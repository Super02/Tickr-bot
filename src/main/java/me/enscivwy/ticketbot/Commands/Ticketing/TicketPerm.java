package me.enscivwy.ticketbot.Commands.Ticketing;

import me.enscivwy.ticketbot.CommandHandler.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.time.OffsetDateTime;

public class TicketPerm implements Command {
    @Override
    public void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member,
                          Guild guild) {
        Role manage = getRole("Management", guild);
        if(member.getRoles().contains(manage)) {
            if(channel.getName().startsWith("ticket-") ) {
                if(args.length == 1) {
                    Member m = getMentionedMember(args[0], guild);
                    if (m != null) {
                        channel.getManager().setTopic(channel.getTopic() + " " + m.getUser().getId()).queue();
                        channel.sendMessage(new EmbedBuilder()
                                .setColor(Color.GREEN)
                                .setTimestamp(OffsetDateTime.now())
                                .setTitle("Success")
                                .setDescription(m.getAsMention() + " has been granted full access to this ticket.")
                                .build()).queue();
                    }else {
                        channel.sendMessage(new EmbedBuilder()
                                .setColor(Color.decode("#ff0000"))
                                .setTimestamp(OffsetDateTime.now())
                                .setTitle("An Error Occured")
                                .setDescription("Cannot find Member " + args[0])
                                .build()).queue();
                    }
                }else {
                    channel.sendMessage(new EmbedBuilder()
                            .setColor(Color.decode("#ff0000"))
                            .setTimestamp(OffsetDateTime.now())
                            .setTitle("An Error Occured")
                            .setDescription("Incorrect Usage: ``-ticketperm (@Member)``")
                            .build()).queue();
                }
            }else {
                channel.sendMessage(new EmbedBuilder()
                        .setTimestamp(OffsetDateTime.now())
                        .setTitle("An Error Occurred")
                        .setColor(Color.decode("#228B22"))
                        .setDescription("This command can only be executed in a ticket")
                        .build()).queue();
            }
        }else {
            channel.sendMessage(new EmbedBuilder()
                    .setColor(Color.decode("#ffffff"))
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

    public Member getMentionedMember(String s, Guild g) {
        for(Member mem : g.getMembers()) {
            if (mem.getAsMention().equalsIgnoreCase(s)) {
                return mem;
            }
        }
        return null;
    }

    @Override
    public String getCommand() { return "ticketperm"; }

    @Override
    public String getDescription() { return "Give a user the same permissions as the player who made the ticket."; }
}
