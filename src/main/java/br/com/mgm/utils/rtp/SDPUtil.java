package br.com.mgm.utils.rtp;

import gov.nist.javax.sdp.SessionDescriptionImpl;
import gov.nist.javax.sdp.parser.SDPAnnounceParser;

import javax.sdp.MediaDescription;
import javax.sdp.SdpException;
import java.net.InetSocketAddress;
import java.text.ParseException;

public class SDPUtil {

    public static MediaOrigin getMediaOrigem(String sdp) throws ParseException, SdpException {
        SDPAnnounceParser parser = new SDPAnnounceParser(sdp);
        SessionDescriptionImpl sessiondescription = parser.parse();
        MediaDescription md = (MediaDescription) sessiondescription.getMediaDescriptions(
                false).get(0);

        int port = md.getMedia().getMediaPort();
        String origin = sessiondescription.getOrigin().getAddress();

        return new MediaOrigin(origin, port);
    }

    public static String changeMediaSdp(String sdp, InetSocketAddress address) throws ParseException, SdpException {
        SDPAnnounceParser parser = new SDPAnnounceParser(sdp);
        SessionDescriptionImpl sessiondescription = parser.parse();
        MediaDescription md = (MediaDescription) sessiondescription.getMediaDescriptions(
                false).get(0);
        md.getMedia().setMediaPort(address.getPort());
        sessiondescription.getOrigin().setAddress(address.getHostName());
        sessiondescription.getConnection().setAddress(address.getHostName());
        return sessiondescription.toString();
    }
}
