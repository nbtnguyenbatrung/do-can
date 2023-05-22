package com.dogoo.SystemWeighingSas.unitity.http;

import lombok.Data;

@Data
public class HostAndPort {
    private String host;
    private Integer port;

    public static HostAndPort fromString(String hostAndPort) {
        String[] hpSplit = hostAndPort.split(":");
        HostAndPort hp = new HostAndPort();
        hp.setHost(hpSplit[0]);
        hp.setPort(Integer.valueOf(hpSplit[1]));
        return hp;
    }
}
