package uk.jamieisgeek.bungeediscordsrv;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import uk.jamieisgeek.bungeediscordsrv.Events.ChatListener;
import uk.jamieisgeek.bungeediscordsrv.Events.JoinLeaveListener;
import uk.jamieisgeek.common.DiscordBot;
import uk.jamieisgeek.common.DiscordManager;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class BungeeDiscordSRV extends Plugin {
    @Override
    public void onEnable() {
        ConfigHandler config;

        try {
            config = new ConfigHandler(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] items = config.getBotSetupItems();
        String[] presence = config.getPresence();

        try {
            new DiscordBot(
                    items[0],
                    items[1],
                    getProxy(),
                    config.getStatus(),
                    presence[0],
                    presence[1]
            );
        } catch (LoginException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.getProxy().getPluginManager().registerListener(this, new ChatListener());
        this.getProxy().getPluginManager().registerListener(this, new JoinLeaveListener());

        getLogger().info("#############################################################");
        getLogger().info("# ____                               _____ _______      __  #");
        getLogger().info("# |  _ \\                             / ____|  __ \\ \\    / / #");
        getLogger().info("# | |_) |_   _ _ __   __ _  ___  ___| (___ | |__) \\ \\  / /  #");
        getLogger().info("# |  _ <| | | | '_ \\ / _` |/ _ \\/ _ \\\\___ \\|  _  / \\ \\/ /   #");
        getLogger().info("# | |_) | |_| | | | | (_| |  __/  __/____) | | \\ \\  \\  /    #");
        getLogger().info("# |____/ \\__,_|_| |_|\\__, |\\___|\\___|_____/|_|  \\_\\  \\/     #");
        getLogger().info("#                     __/ |                                 #");
        getLogger().info("#                    |___/                                  #");
        getLogger().info("#                                                           #");
        getLogger().info("#             BungeeDiscordSRV v1.0.0 by JamieIsGeek        #");
        getLogger().info("#                                                           #");
        getLogger().info("#############################################################");
    }

    @Override
    public void onDisable() {
        DiscordManager.shutdown();
        getLogger().info("BungeeDiscordSRV has been disabled.");
    }
}
