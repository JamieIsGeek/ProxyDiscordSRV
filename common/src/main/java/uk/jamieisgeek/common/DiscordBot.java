package uk.jamieisgeek.common;

import com.typesafe.config.Optional;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.md_5.bungee.api.ProxyServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class DiscordBot implements EventListener {
    private static DiscordBot instance;
    private static JDA BOT = null;
    private final String categoryID;
    private final ProxyServer proxy;
    private final ArrayList<TextChannel> channels = new ArrayList<>();

    public DiscordBot(String token, String categoryID, ProxyServer proxy, String status, String activity, String activityText) throws LoginException, InterruptedException {
        instance = this;
        this.categoryID = categoryID;

        this.proxy = proxy;

        OnlineStatus onlineStatus;

        switch (status.toLowerCase()) {
            case "idle" -> onlineStatus = OnlineStatus.IDLE;
            case "dnd" -> onlineStatus = OnlineStatus.DO_NOT_DISTURB;
            case "invisible" -> onlineStatus = OnlineStatus.INVISIBLE;
            default -> onlineStatus = OnlineStatus.ONLINE;
        }

        if(activity.equalsIgnoreCase("null")) {
            BOT = JDABuilder.createLight(token)
                    .enableIntents(
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_MEMBERS
                    )
                    .disableIntents(
                            GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_BANS,
                            GatewayIntent.DIRECT_MESSAGES,
                            GatewayIntent.DIRECT_MESSAGE_TYPING
                    )
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setBulkDeleteSplittingEnabled(false)
                    .addEventListeners(this)
                    .setStatus(onlineStatus)
                    .build();
        } else {
            Activity activityType;

            switch (activity.toLowerCase()) {
                case "listening" -> activityType = Activity.listening(activityText);
                case "watching" -> activityType = Activity.watching(activityText);
                default -> activityType = Activity.playing(activityText);
            }

            BOT = JDABuilder.createLight(token)
                    .enableIntents(
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_MEMBERS
                    )
                    .disableIntents(
                            GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_BANS,
                            GatewayIntent.DIRECT_MESSAGES,
                            GatewayIntent.DIRECT_MESSAGE_TYPING
                    )
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setBulkDeleteSplittingEnabled(false)
                    .addEventListeners(this)
                    .setStatus(onlineStatus)
                    .setActivity(activityType)
                    .build();
        }

        new DiscordManager(proxy);
    }

    public void onEvent(@NotNull GenericEvent event) {
        if (event.getClass().getSimpleName().equals("ReadyEvent")) {
            proxy.getLogger().info("Discord Bot is ready!");

            this.setupChannels();
        } else if (event instanceof GuildMessageReceivedEvent guildMessageReceivedEvent) {
            if (guildMessageReceivedEvent.getAuthor().isBot()) return;
            if (guildMessageReceivedEvent.getChannel().getParent().getId().equals(categoryID)) {
                Factory.getFactory().sendChannelMessage(guildMessageReceivedEvent.getMessage().getContentRaw(), guildMessageReceivedEvent.getAuthor().getName(), guildMessageReceivedEvent.getChannel().getName());
            }
        }
    }

    public void setupChannels() {
        proxy.getLogger().info("Setting up channels...");
        List<TextChannel> guildChannels = BOT.getTextChannels();

        proxy.getServers().forEach((s, serverinfo) -> {
            if(guildChannels.stream().noneMatch(channel -> channel.getName().equalsIgnoreCase(serverinfo.getName()))) {
                Category category = BOT.getCategoryById(this.categoryID);
                if(category == null) {
                    proxy.getLogger().warning("Category ID is invalid, please check your config.yml");
                    return;
                }

                category.createTextChannel(serverinfo.getName()).queue(textChannel -> {
                    channels.add(textChannel);
                    proxy.getLogger().info("Created channel " + textChannel.getName());
                });

            } else {
                guildChannels.stream().filter(channel -> channel.getName().equalsIgnoreCase(serverinfo.getName())).forEach(channels::add);
            }
            /*if(guildChannels.stream().noneMatch(channel -> channel.getName().equalsIgnoreCase(serverinfo.getName()))) {
                Category category = BOT.getCategoryById(categoryID);
                if(category == null) {
                    proxy.getLogger().warning("Category ID is invalid, please check your config.yml");
                    return;
                }

                category.createTextChannel(serverinfo.getName()).queue(textChannel -> {
                    channels.add(textChannel);
                    proxy.getLogger().info("Created channel " + textChannel.getName());
                });

            } else {
                guildChannels.stream().filter(channel -> channel.getName().equalsIgnoreCase(serverinfo.getName())).forEach(channels::add);
            }*/
        });

        Category category = BOT.getCategoryById(categoryID);
        assert category != null;

        category.getTextChannels().forEach(channel -> {
            if (!channels.contains(channel)) {
                channels.add(channel);
            }
        });

        DiscordManager.getDiscordManager().sendStatusMessage(true);
    }

    public static void shutdown() {
        if(BOT == null) return;
        BOT.shutdown();
    }

    public static void setStatus(OnlineStatus status) {
        BOT.getPresence().setStatus(status);
    }

    public static void setPresence(Activity status, String text, @Optional String url) {
        BOT.getPresence().setActivity(status);
    }

    public static ArrayList<TextChannel> getChannels() {
        return instance.channels;
    }
}
