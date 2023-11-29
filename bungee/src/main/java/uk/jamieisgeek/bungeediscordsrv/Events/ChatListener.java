package uk.jamieisgeek.bungeediscordsrv.Events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import uk.jamieisgeek.common.DiscordManager;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(ChatEvent event) {
        if (event.isCommand()) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }

        String sender = ((ProxiedPlayer) event.getSender()).getName();
        String serverName = ((ProxiedPlayer) event.getSender()).getServer().getInfo().getName();

        DiscordManager.getDiscordManager().sendChannelMessage(event.getMessage(), sender, serverName);
    }
}
