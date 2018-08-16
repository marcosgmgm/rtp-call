package br.com.mgm.utils.rtp;

import java.net.InetSocketAddress;

public class MediaOrigin {

    private InetSocketAddress address;

    public MediaOrigin(String host, int port) {
        this.address = new InetSocketAddress(host, port);
    }

    public InetSocketAddress getAddress() {
        return address;
    }
}
