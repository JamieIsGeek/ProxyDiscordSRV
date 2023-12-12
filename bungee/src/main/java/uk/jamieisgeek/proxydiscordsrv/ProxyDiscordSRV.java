package uk.jamieisgeek.proxydiscordsrv;

import net.md_5.bungee.api.plugin.Plugin;
import uk.jamieisgeek.proxydiscordsrv.Events.ChatListener;
import uk.jamieisgeek.proxydiscordsrv.Events.JoinLeaveListener;
import uk.jamieisgeek.common.Config.Config;
import uk.jamieisgeek.common.DiscordBot;
import uk.jamieisgeek.common.DiscordManager;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ProxyDiscordSRV extends Plugin {
    @Override
    public void onEnable() {
        this.createFile("config.yml", "config.yml");
        Config config = new Config(new File(getDataFolder(), "config.yml"), "");

        try {
            config.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final String token = config.getOrSet("discord.token", "BOT_TOKEN_HERE");
        final String catID = config.getOrSet("discord.category", "CATEGORY_ID_HERE");
        final String status = config.getOrSet("discord.presence.status", "ONLINE");
        final String activity = config.getOrSet("discord.presence.activity", "PLAYING");
        final String activityName = config.getOrSet("discord.presence.text", "Minecraft");

        try {
            new DiscordBot(
                    token,
                    catID,
                    getProxy(),
                    status,
                    activity,
                    activityName
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

    private void createFile(final String name, final String from) {
        final File file = new File(getDataFolder(), name);
        if (!file.exists()) {
            try (final InputStream in = this.getClass().getClassLoader().getResourceAsStream(from)) {
                Files.copy(in, file.toPath());
            } catch (final IOException e) {
                throw new RuntimeException("Unable to create " + name + " file for BungeeDiscordSRV!", e);
            }
        }
    }
}
