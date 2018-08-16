package br.com.mgm.utils.rtp;

import junit.framework.TestCase;

import java.util.UUID;

public class RtpControllerTest extends TestCase {

    public void testRequestSdpB() {
        String callId = UUID.randomUUID().toString();
        try {
            String sdpB = RtpController.INSTANCE.createSessionSdpB(callId, getSdp());
            assertNotNull(sdpB);
            System.out.println(sdpB);
            RtpController.INSTANCE.finalizeSession(callId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testRequestSdpA() {
        String callId = UUID.randomUUID().toString();
        try {
            String sdpB = RtpController.INSTANCE.createSessionSdpB(callId, getSdp());
            assertNotNull(sdpB);
            System.out.println(sdpB);
            String sdpA = RtpController.INSTANCE.updateSessionSdpA(callId, getSdp());
            System.out.println(sdpA);

            sdpB = RtpController.INSTANCE.requestSdpB(callId, getSdp());
            assertNotNull(sdpB);

            sdpA = RtpController.INSTANCE.requestSdpA(callId, getSdp());
            assertNotNull(sdpA);

            RtpController.INSTANCE.finalizeSession(callId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*private String getSdp() {
        String rtcSdp = "v=0\n" +
                "o=- 3212632920 2 IN IP4 127.0.0.1\n" +
                "s=plivo\n" +
                "t=0 0\n" +
                "m=audio 49665 RTP/SAVPF 103 104 0 8 106 105 13 126\n" +
                "c=IN IP4 192.168.3.1\n" +
                "a=sendrecv\n" +
                "a=mid:audio\n" +
                "a=rtcp-mux\n" +
                "a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:i3ZhWGTXvX8vxKxtht+lCLsT/nhuyM2rgDQFInTx\n" +
                "a=rtpmap:103 ISAC/16000\n" +
                "a=rtpmap:104 ISAC/32000\n" +
                "a=rtpmap:0 PCMU/8000\n" +
                "a=rtpmap:8 PCMA/8000\n" +
                "a=rtpmap:106 CN/32000\n" +
                "a=rtpmap:105 CN/16000\n" +
                "a=rtpmap:13 CN/8000\n" +
                "a=rtpmap:126 telephone-event/8000\n" +
                "a=ssrc:2399224977 cname:Wq4cj1yaLwKIQPRA\n" +
                "a=ssrc:2399224977 mslabel:SeAfUDCzSeGWhdcVyHTVIt9HBI2acjoawxkI\n" +
                "a=ssrc:2399224977 label:SeAfUDCzSeGWhdcVyHTVIt9HBI2acjoawxkI00\n";
        return rtcSdp;
    }*/

    private String getSdp() {
        String sdp = "v=0\n" +
                "o=twinkle 1815253411 511287015 IN IP4 192.168.1.233\n" +
                "s=-\n" +
                "c=IN IP4 192.168.1.233\n" +
                "t=0 0\n" +
                "m=audio 8000 RTP/AVP 98 97 8 0 3 101\n" +
                "a=rtpmap:98 speex/16000\n" +
                "a=rtpmap:97 speex/8000\n" +
                "a=rtpmap:8 PCMA/8000\n" +
                "a=rtpmap:0 PCMU/8000\n" +
                "a=rtpmap:3 GSM/8000\n" +
                "a=rtpmap:101 telephone-event/8000\n" +
                "a=fmtp:101 0-15\n" +
                "a=ptime:20\n";
        return sdp;

    }
}