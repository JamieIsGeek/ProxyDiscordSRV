package uk.jamieisgeek.common;

import net.md_5.bungee.api.ProxyServer;

public class Factory {
    public static Factory factory;
    private final ProxyServer proxy;

    public Factory(ProxyServer proxy) {
        factory = this;
        this.proxy = proxy;
    }

    public void sendChannelMessage(String message, String username, String serverName) {
        if(proxy.getServers().containsKey(serverName)) {
            proxy.getServers().get(serverName).getPlayers().forEach(player -> player.sendMessage(new MessageBuilder(username, message).DiscordToMC()));
        }
    }

    public static Factory getFactory() {
        return factory;
    }
}
