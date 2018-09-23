package me.enscivwy.ticketbot.Commands.Ticketing;

import me.enscivwy.ticketbot.CommandHandler.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

public class CloseTicket implements Command {
    @Override
    public void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member, Guild guild) {
        Role manage = getRole("Management", guild);
        if(channel.getTopic().contains(sender.getId()) || member.getRoles().contains(manage)) {
            if (!channel.getName().contains("ticket-")) {
                channel.sendMessage(new EmbedBuilder()
                        .setTimestamp(OffsetDateTime.now())
                        .setTitle("An Error Occurred")
                        .setColor(Color.decode("#228B22"))
                        .setDescription("This command can only be executed in a ticket")
                        .build()).queue();
                return;
            }
            channel.sendMessage(new EmbedBuilder()
                    .setTitle("Ticket Closing")
                    .setDescription("This ticket will close in 10 seconds")
                    .setTimestamp(OffsetDateTime.now())
                    .setColor(Color.decode("#228B22"))
                    .build()).queue();
            channel.delete().queueAfter(10, TimeUnit.SECONDS);
        }else{
            channel.sendMessage(new EmbedBuilder()
                    .setColor(Color.decode("#ff0000"))
                    .setTimestamp(OffsetDateTime.now())
                    .setTitle("Permission Denied")
                    .setDescription("You don't have permission to execute this command")
                    .build()).queue();
        }
    }

    public Role getRole(String name, Guild g) {
        for(Role r : g.getRoles()) {
            if(r.getName().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public String getCommand() {
        return "close";
    }

    @Override
    public String getDescription() {
        return "This Command will close a ticket";
    }
}
