package uk.jamieisgeek.common;

import net.md_5.bungee.api.ProxyServer;

public class Cache {
    public static Cache cache;
    private String proxyType;
    private ProxyServer bungee;
    public Cache() {
        cache = this;
    }
    public void setProxyType(String proxyType) {
        this.proxyType = proxyType;
    }
    public String getProxyType() {
        return proxyType;
    }

    public static Cache getCache() {
        return cache;
    }
}
