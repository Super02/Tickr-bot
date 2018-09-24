package me.enscivwy.ticketbot.Commands.Basic;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class AletsRole extends ListenerAdapter {
    String reactMessageId = "";
    @Override
    public void onReady(ReadyEvent event) {
        event.getJDA().getTextChannelById("476762900900610049").sendMessage(new EmbedBuilder()
                .setColor(Color.decode("#ffffff"))
                .setTitle("React to confirm")
                .setDescription("Hello, if you wish to receive alerts from things like: New plugin updates, changes in our bots, new bots, new features and so on. Then please react with :white_check_mark: You can always un-react and disable notifications. If you wish to only receive certain notification please make a ticket using `-new` and our Support Team will set that up for you :smiley:")
                .build()).queue((msg) -> {
            msg.addReaction("\u2705").queue();
            reactMessageId = msg.getId();
        });
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (!event.getChannel().getId().equals("476762900900610049")) return;
        if (!event.getMessageId().equals(reactMessageId)) return;
        if (event.getReactionEmote().getName().equals("\u2705")) {
            event.getGuild().getController().addRolesToMember(event.getMember(), event.getJDA().getRoleById("472132605245128709")).queue();
        }
    }

    @Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        if (!event.getChannel().getId().equals("476762900900610049")) return;
        if (!event.getMessageId().equals(reactMessageId)) return;
        if (event.getReactionEmote().getName().equals("\u2705")) {
            event.getGuild().getController().removeRolesFromMember(event.getMember(), event.getJDA().getRoleById("472132605245128709")).queue();
        }
    }
}
