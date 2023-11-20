package uk.jamieisgeek.common;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageBuilder {
    private static String message;
    private static String username;
    public MessageBuilder(String username, String message) {
        MessageBuilder.username = username;
        MessageBuilder.message = message;
    }

    public String MCToDiscord() {
        return "**" + username + "**: " + message;
    }
    public TextComponent DiscordToMC() {
        return new TextComponent("[" + ChatColor.AQUA + "Discord" + ChatColor.RESET + "] " + username + ": " + ChatColor.WHITE + message);
    }
}
