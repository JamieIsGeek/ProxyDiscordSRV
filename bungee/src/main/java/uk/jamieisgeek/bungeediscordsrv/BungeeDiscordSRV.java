package uk.jamieisgeek.bungeediscordsrv;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public class BungeeDiscordSRV extends Plugin {
    private static BungeeDiscordSRV instance;
    private Configuration configuration;

    @Override
    public void onEnable() {
        instance = this;
        String[] items;
        /*
        try {
            items = this.getConfigItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new ProxyManager(this.getProxy());

        try {
            new DiscordBot(items[0], items[1], getProxy());
        } catch (LoginException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.getProxy().getPluginManager().registerListener(this, new ChatListener());
        this.getProxy().getPluginManager().registerListener(this, new JoinLeaveListener());
        this.getProxy().getPluginManager().registerCommand(this, new ReloadCommand());
        */
    }
}
