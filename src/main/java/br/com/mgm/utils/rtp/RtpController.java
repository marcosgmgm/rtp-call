package br.com.mgm.utils.rtp;

import org.vtlabs.rtpproxy.client.RTPProxySession;
import org.vtlabs.rtpproxy.config.RTPProxyClientConfig;
import org.vtlabs.rtpproxy.exception.RTPProxyClientException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum  RtpController {

    INSTANCE;
    private Map<String, ClientListener> listeners;

    {
        listeners = Collections.synchronizedMap(new HashMap<String, ClientListener>());
    }

    public String createSessionSdpB(String callId, String sdpA) throws Exception {
        ClientListener listener = listeners.get(callId);
        if (listener == null) {
            listener = new ClientListener();
            listeners.put(callId, listener);
        }
        MediaOrigin origin = SDPUtil.getMediaOrigem(sdpA);
        synchronized (listener) {
            listener.getClient().createSession(callId, origin.getAddress(), null, listener);
            // We'll be notified when some callback method was called.
            listener.wait();
        }
        RTPProxySession session = listener.getSession();
        if (session != null) {
            String sdpB = SDPUtil.changeMediaSdp(sdpA, session.getCalleeMediaAddress());
            return sdpB;
        } else {
            throw new RuntimeException("Falha na criacao de SDP para ponta B");
        }
    }

    public String updateSessionSdpA(String callId, String sdpB) throws Exception {
        ClientListener listener = listeners.get(callId);
        MediaOrigin origin = SDPUtil.getMediaOrigem(sdpB);
        RTPProxySession session = listener.getSession();
        synchronized (listener) {
            listener.getClient().updateSession(session, origin.getAddress(), null, listener);
            // We'll be notified when some callback method was called.
            listener.wait();
        }
        return SDPUtil.changeMediaSdp(sdpB, session.getCallerMediaAddress());
    }

    public String requestSdpB(String callId, String sdpA) throws Exception {
        ClientListener listener = listeners.get(callId);
        return SDPUtil.changeMediaSdp(sdpA, listener.getSession().getCalleeMediaAddress());
    }

    public String requestSdpA(String callId, String sdpB) throws Exception {
        ClientListener listener = listeners.get(callId);
        return SDPUtil.changeMediaSdp(sdpB, listener.getSession().getCallerMediaAddress());
    }

    public void finalizeSession(String callId) throws Exception {
        ClientListener listener = listeners.remove(callId);
        if (listener != null) {
            synchronized (listener) {
                RTPProxySession session = listener.getSession();
                listener.getClient().destroySession(session, null, listener);
                listener.wait();
            }
        }

    }

}
