package me.enscivwy.ticketbot.Commands.Basic;

import me.enscivwy.ticketbot.CommandHandler.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class AlertCommand implements Command {
    @Override
    public void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member, Guild guild) {
        String announcement = "";
        for(int i = 0; i < args.length; i++){
            announcement = announcement.concat(args[i]).concat(" ");
        }
        final String an = announcement;
        Role alertsRole = guild.getRoleById("472132605245128709");
        channel.sendMessage(alertsRole.getAsMention()).queue((msg) -> {
            msg.delete().queueAfter(1, TimeUnit.SECONDS);
        });
        alertsRole.getManager().setMentionable(true).queue(s ->
            guild.getTextChannelById("470580424499003402").sendMessage(new EmbedBuilder()
                    .setColor(Color.decode("#ffffff"))
                    .setDescription(an)
                    .setTitle("New broadcast")
                    .build()).queue(msg ->
                alertsRole.getManager().setMentionable(false).queue()));

    }

    @Override
    public String getCommand() {
        return "broadcast";
    }

    @Override
    public String getDescription() {
        return "Will broadcast a message";
    }
}
