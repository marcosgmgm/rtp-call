package br.com.mgm.utils.rtp;

import org.vtlabs.rtpproxy.client.RTPProxyClient;
import org.vtlabs.rtpproxy.client.RTPProxyClientListener;
import org.vtlabs.rtpproxy.client.RTPProxySession;
import org.vtlabs.rtpproxy.config.RTPProxyClientConfig;
import org.vtlabs.rtpproxy.config.RTPProxyClientConfigurator;
import org.vtlabs.rtpproxy.exception.RTPProxyClientException;

public class ClientListener implements RTPProxyClientListener {


    private RTPProxySession session;
    private String lastSdpA;
    private String lastSdpB;
    private RTPProxyClient client;
    private boolean wasCreateTimeout;
    private boolean wasUpdateTimeout;
    private boolean wasCreateFailed;
    private boolean wasUpdateFailed;
    private boolean wasDestroyFailed;
    private boolean wasDestroyed;
    private boolean wasSessionRecordableTimeout;
    private boolean wasSessionRecordableCreate;
    private boolean wasSessionRecordableFailed;
    private boolean wasDestroyTimeout;

    public ClientListener() throws RTPProxyClientException {
        RTPProxyClientConfig config = RTPProxyClientConfigurator.load(System.getProperty("list.rtp.server", "127.0.0.1:7722"));
        client = new RTPProxyClient(config);
    }

    public RTPProxyClient getClient() {
        return this.client;
    }

    public RTPProxySession getSession() {
        return session;
    }


    //
    // Callback methods implemented from RTPProxyClientListener
    //
    public void sessionCreated(RTPProxySession session, Object appData) {
        this.session = session;
        wakeup();
    }

    public void sessionUpdated(RTPProxySession session, Object appData) {
        wakeup();
    }

    public void createSessionTimeout(String sessionID, Object appData) {
        wasCreateTimeout = true;
        wakeup();
    }

    public void updateSessionTimeout(RTPProxySession session, Object appData) {
        wasUpdateTimeout = true;
        wakeup();
    }

    public void createSessionFailed(String sessionID, Object appData,
                                    Throwable t) {
        wasCreateFailed = true;
        wakeup();
    }

    public void updateSessionFailed(RTPProxySession session, Object appData,
                                    Throwable t) {
        wasUpdateFailed = true;
        wakeup();
    }

    private void wakeup() {
        synchronized (this) {
            notify();
        }
    }

    public void sessionDestroyed(RTPProxySession session, Object appData) {
        wasDestroyed = true;
        wakeup();
    }

    public void destroySessionTimeout(RTPProxySession session, Object appData) {
        wasDestroyTimeout = true;
        wakeup();
    }

    public void destroySessionFailed(RTPProxySession session, Object appData,
                                     Throwable t) {
        wasDestroyFailed = true;
        wakeup();
    }

    public void recordableSessionCreated(String sessionID, Object appData) {
        wasSessionRecordableCreate = true;
        wakeup();

    }

    public void createRecordableSessionFailed(String sessionID, Object appData) {
        wasSessionRecordableFailed = true;
        wakeup();
    }

    public void createRecordableSessionTimeout(String sessionID, Object appData) {
        wasSessionRecordableTimeout = true;
        wakeup();
    }


}
