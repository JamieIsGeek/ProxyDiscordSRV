package uk.jamieisgeek.bungeediscordsrv;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import uk.jamieisgeek.bungeediscordsrv.Events.ChatListener;
import uk.jamieisgeek.bungeediscordsrv.Events.JoinLeaveListener;
import uk.jamieisgeek.common.DiscordBot;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BungeeDiscordSRV extends Plugin {
    private static BungeeDiscordSRV instance;
    private Configuration configuration;

    @Override
    public void onEnable() {
        instance = this;
        String[] items;

        try {
            items = this.getConfigItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            new DiscordBot(items[0], items[1], getProxy());
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

    }

    public void setupConfig() throws IOException {
        if (!getDataFolder().exists()) {
            getLogger().info("Created config folder: " + getDataFolder().mkdir());
        }

        File configFile = new File(getDataFolder(), "config.yml");

        // Copy default config if it doesn't exist
        if (!configFile.exists()) {
            FileOutputStream outputStream = new FileOutputStream(configFile); // Throws IOException
            InputStream in = getResourceAsStream("config.yml"); // This file must exist in the jar resources folder
            in.transferTo(outputStream); // Throws IOException
        }

        if (!getDataFolder().exists()) {
            getLogger().info("Created config folder: " + getDataFolder().mkdir());
        }

        configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
    }

    public String[] getConfigItems() throws IOException {
        if (this.configuration == null) {
            this.setupConfig();
        }

        return new String[] {
            this.configuration.getString("discord.token"),
            this.configuration.getString("discord.category")
        };
    }

    public static BungeeDiscordSRV getInstance() {
        return instance;
    }

}
