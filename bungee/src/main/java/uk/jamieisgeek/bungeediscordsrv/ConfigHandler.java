package uk.jamieisgeek.bungeediscordsrv;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConfigHandler {
    private Configuration configuration = null;
    private final BungeeDiscordSRV plugin;

    public ConfigHandler(BungeeDiscordSRV plugin) throws IOException {
        this.plugin = plugin;

        this.setup();
    }

    public void setup() throws IOException {
        if (!plugin.getDataFolder().exists()) {
            plugin.getLogger().info("Created config folder: " + plugin.getDataFolder().mkdir());
        }

        File configFile = new File(plugin.getDataFolder(), "config.yml");

        // Copy default config if it doesn't exist
        if (!configFile.exists()) {
            FileOutputStream outputStream = new FileOutputStream(configFile); // Throws IOException
            InputStream in = plugin.getResourceAsStream("config.yml"); // This file must exist in the jar resources folder
            in.transferTo(outputStream); // Throws IOException
        }

        if (!plugin.getDataFolder().exists()) {
            plugin.getLogger().info("Created config folder: " + plugin.getDataFolder().mkdir());
        }

        configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
    }

    public String[] getBotSetupItems() {
        return new String[]{
                configuration.getString("discord.token"),
                configuration.getString("discord.category")
        };
    }

    public String[] getPresence() {
        return new String[]{
                configuration.getString("discord.presence.type"),
                configuration.getString("discord.presence.text")
        };
    }

    public String getStatus() {
        return configuration.getString("discord.presence.status");
    }

    public void checkAndSet(String path, String value) {
        if(!configuration.contains(path)) {
            configuration.set(path, value);
        }
    }
}
