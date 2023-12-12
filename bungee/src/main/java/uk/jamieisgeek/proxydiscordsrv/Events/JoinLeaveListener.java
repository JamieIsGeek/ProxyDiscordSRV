package uk.jamieisgeek.proxydiscordsrv.Events;

import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import uk.jamieisgeek.common.DiscordManager;

public class JoinLeaveListener implements Listener {
    @EventHandler
    public void onPlayerJoin(ServerSwitchEvent event) {
        String username = event.getPlayer().getName();
        String serverName = event.getPlayer().getServer().getInfo().getName();
        DiscordManager.getDiscordManager().sendConnectionEmbed(username, serverName, true);
    }

    @EventHandler
    public void onPlayerLeave(ServerSwitchEvent event) {
        String username = event.getPlayer().getName();
        String serverName = event.getPlayer().getServer().getInfo().getName();
        DiscordManager.getDiscordManager().sendConnectionEmbed(username, serverName, false);
    }
}
