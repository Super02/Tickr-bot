package me.enscivwy.ticketbot.Commands.Basic;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class ReactionListeners extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        event.getJDA().getTextChannelById("493057488862838794").sendMessage(new EmbedBuilder()
                .setColor(Color.decode("#ffffff"))
                .setTitle("React to confirm")
                .setDescription("Hello, and welcome to the Discord server.\n" + " \n" + "__ **To report a bug/get help type -new in chat to create a ticket. We will answer as fast as possible.\n" + "Agree to the rules to gain access to all our other channels**__\n" + " \n" + "**__Rules:__**\n" + " \n" + " - __Do not spam open tickets.__ - Warn/Kick/Ban/Mute\n" + " \n" + " - __Do not spam chat or other members.__ Mute/Warn/Kick/Ban\n" + " \n" + " - __Respect staff. __ Mute/Warn\n" + " \n" + " - __Don't bully others.__ PERM BAN\n" + " \n" + " - __Don't swear to much.__ Warn/Mute\n" + " \n" + " - __Don't be racist, sexist etc.__ Mute/Warn/Kick/Perm Ban\n" + " \n" + " - __Do not flood chat.__ Mute/Warn\n" + " \n" + " - __Be nice. Don't talk bad to other people.__ Mute/Warn/Kick/Ban\n" + " \n" + " - __Don't advertise.__Warn/Mute/Ban/Perm Ban\n" + " \n" + " - __Playing loud noises/music in Voice channels.__ Mute/Warn/Kick/Ban/Perm Ban\n" + " \n" + " - __Saying  problematic jokes.__ Mute/Warn/Kick/Ban/Perm Ban\n" + " \n" + " - __Giving private information.__ / Asking for it Mute/Warn/Ban/Perm Ban\n" + " \n" + " - __Asking people for their money.__ Mute/Warn/Ban/Perm Ban\n" + " \n" + " - __Threatening people.__ Perm Ban\n" + " \n" + " - __Impersonating staff.__ Mute/Warn/Ban/Perm Ban\n" + " \n" + " - __Being toxic against others .__ Warn/Mute/Kick/Ban/Perm Ban\n" + " \n" + " - __Use #bot-commands for the bot command. (Just so it does not get spammy)__ Warn/Mute\n" + " \n" + " - __Follow Discord's ToS and local law.__ Warn/Ban/Perm Ban/Report to Discord\n" + " \n" + " - __Use the right channels for the right things don't chat in support channels etc.__ Warn/Mute/Kick\n" + " \n" + " - __Do not exploit (Report bugs by making a ticket).__ Warn/Kick/Ban/Perm Ban\n" + " \n" + " - __Do no tag staff.__ Warn/Mute/Kick/Ban\n" + " \n" + " - __Do not spell anything bad with reactions.__ Removal of reactions/Warn/Kick/Removal of reaction permission\n" + " \n" + "         **__Use common sense. Then, you won't have any problems with these rules :smiley:__\n" + "              __Press on the :white_check_mark:  emoji to confirm agreement with the rules above__\n" + "                                                    __failure to do so will result in a kick__**")
                .build()).queue((msg) -> {
                    msg.addReaction("\u2705").queue();
                    msg.addReaction("\u274C").queue();
        });
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (!event.getChannel().getId().equals("493057488862838794")) return;
        if (event.getReactionEmote().getName().equals("\u2705")) {
            event.getGuild().getController().addRolesToMember(event.getMember(), event.getJDA().getRoleById("447743844323426314")).queue();
        }else if(event.getReactionEmote().getName().equals("\u274C")){
            event.getGuild().getController().kick(event.getMember()).queue();
        }
    }

    @Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        if (!event.getChannel().getId().equals("493057488862838794")) return;
        if (event.getReactionEmote().getName().equals("\u2705")) {
            event.getGuild().getController().removeRolesFromMember(event.getMember(), event.getJDA().getRoleById("447743844323426314")).queue();
        }
    }
}
