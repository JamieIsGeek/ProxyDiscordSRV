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
    private Config config;
    private final int CONFIG_VERSION = 1;
    @Override
    public void onEnable() {
        try {
            this.createFile("config.yml", "config.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.config = new Config(new File(getDataFolder(), "config.yml"), "");

        try {
            config.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.updateConfig();

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

        getLogger().info("######################################################");
        getLogger().info("#                                                    #");
        getLogger().info("# _____                      _____ _______      __   #");
        getLogger().info("# |  __ \\                    / ____|  __ \\ \\    / /  #");
        getLogger().info("# | |__) | __ _____  ___   _| (___ | |__) \\ \\  / /   #");
        getLogger().info("# |  ___/ '__/ _ \\ \\/ / | | |\\___ \\|  _  / \\ \\/ /    #");
        getLogger().info("# | |   | | | (_) >  <| |_| |____) | | \\ \\  \\  /     #");
        getLogger().info("# |_|   |_|  \\___/_/\\_\\\\__, |_____/|_|  \\_\\  \\/      #");
        getLogger().info("#                       __/ |                        #");
        getLogger().info("#                      |___/                         #");
        getLogger().info("#                                                    #");
        getLogger().info("#   ProxyDiscordSRV Enabled - Made by JamieIsGeek    #");
        getLogger().info("######################################################");
    }

    @Override
    public void onDisable() {
        DiscordManager.shutdown();
        getLogger().info("BungeeDiscordSRV has been disabled.");
    }

    private void createFile(final String name, final String from) throws IOException {
        final File file = new File(getDataFolder(), name);
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        if (file.exists()) {
            return;
        }

        try (InputStream in = getResourceAsStream(from)) {
            Files.copy(in, file.toPath());
        }
    }

    private void updateConfig() {
        final int version = config.getInt("config-version", 0);

        if (version == CONFIG_VERSION) {
            return;
        }

        this.getLogger().info("Updating config.yml to version " + CONFIG_VERSION);

        try {
            createFile("config-new.yml", "config.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final File file = new File(getDataFolder(), "config-new.yml");
        final Config tempConfig = new Config(file, "");
        try {
            tempConfig.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        config.addMissingFields(tempConfig.getValues(), tempConfig.getComments());
        config.set("config-version", CONFIG_VERSION);

        file.delete();
        tempConfig.clear();

        saveConfig();
        getLogger().info("Config.yml has been updated to version " + CONFIG_VERSION);
    }

    public void saveConfig() {
        try {
            config.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
