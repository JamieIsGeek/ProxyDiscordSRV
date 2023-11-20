package uk.jamieisgeek.common;

public class Factory {
    public Factory factory;

    public Factory(String proxyType) {
        this.factory = this;
    }

    public void sendChannelMessage(String message, String username, String serverName) {
        if(proxy.getServers().containsKey(serverName)) {
            proxy.getServers().get(serverName).getPlayers().forEach(player -> player.sendMessage(new MessageBuilder(username, message).DiscordToMC()));
        }
    }
}
