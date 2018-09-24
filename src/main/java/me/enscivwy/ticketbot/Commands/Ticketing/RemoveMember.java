package me.enscivwy.ticketbot.Commands.Ticketing;

import me.enscivwy.ticketbot.CommandHandler.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.time.OffsetDateTime;

public class RemoveMember implements Command {
    @Override
    public void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member, Guild guild) {
        Role manage = getRole("Management", guild);
        if(channel.getTopic().contains(sender.getId()) || member.getRoles().contains(manage)) {
            String id = args[0].replaceAll("[^0-9]", "");
            for (Member m : guild.getMembers()) {
                if (m.getUser().getId().equalsIgnoreCase(id)) {
                    channel.putPermissionOverride(m).setDeny(Permission.VIEW_CHANNEL).queue();
                    channel.sendMessage(new EmbedBuilder()
                            .setColor(Color.decode("#008000"))
                            .setTimestamp(OffsetDateTime.now())
                            .setTitle("Remove Member", null)
                            .setDescription(sender.getAsMention() + " The Member has been removed from the ticket")
                            .build()).queue();
                }
            }
        } else {
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
    public String getCommand() { return "remove"; }

    @Override
    public String getDescription() { return "This command enables you to remove a member from a ticket"; }
}
