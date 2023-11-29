package uk.jamieisgeek.common;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.md_5.bungee.api.ProxyServer;

import java.util.ArrayList;

public class DiscordManager {
    private final ArrayList<TextChannel> channels;
    private static DiscordManager instance;
    private final ProxyServer proxy;
    private final Factory factory;
    public DiscordManager(ProxyServer proxy) {
        this.channels = DiscordBot.getChannels();
        this.proxy = proxy;
        this.factory = new Factory(this.proxy);
        instance = this;
    }

    public static DiscordManager getDiscordManager() {
        return instance;
    }

    public void sendChannelMessage(String message, String username, String serverName) {
        if(getServerChannel(serverName) != null) {
            getServerChannel(serverName).sendMessage(new MessageBuilder(username, message).MCToDiscord()).queue();
        }
    }

    public void sendStatusMessage(boolean online) {
        channels.forEach(channel -> channel.sendMessage("Network is " + (online ? "online" : "offline")).queue());
    }

    public void sendConnectionEmbed(String username, String server, boolean isJoin) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(username + (isJoin ? " joined" : " left"));
        if(isJoin) {
            builder.setColor(0x00ff00);
        } else {
            builder.setColor(0xff0000);
        }

        if(getServerChannel(server) != null) {
            getServerChannel(server).sendMessageEmbeds(builder.build()).queue();
        }
    }

    public TextChannel getServerChannel(String serverName) {
        for (TextChannel channel : channels) {
            if (channel.getName().equalsIgnoreCase(serverName)) {
                return channel;
            }
        }
        return null;
    }
}
