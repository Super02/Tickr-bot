package me.enscivwy.ticketbot.Commands.Ticketing;

import me.enscivwy.ticketbot.CommandHandler.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Properties;

public class NewTicket implements Command {
    @Override
    public void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member, Guild guild) {
        File config = new File("config.cfg");
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(config));
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(member.hasPermission(Permission.MESSAGE_WRITE)) {
            TextChannel ticketChannel = guild.getTextChannelById(guild.getController().createTextChannel("ticket-" + sender.getName().substring(0, Math.min(sender.getName().length(), 5)) + "-" + getTicketID(prop)).setTopic(sender.getId()).complete().getId());
            channel.sendMessage(new EmbedBuilder()
                    .setColor(Color.decode("#228B22"))
                    .setTimestamp(OffsetDateTime.now())
                    .setTitle("Ticket Created")
                    .setDescription("Your Ticket has been created at " + ticketChannel.getAsMention())
                    .build()).queue();
            String subject = "No Subject Given";
            if(args.length > 0) {
                subject = "";
                for (int i = 0; i < args.length; i++) {
                    if(!args[i].equalsIgnoreCase("-new")) {
                        subject = subject.concat(args[i]).concat(" ");
                    }else {
                        subject = "No Subject Given";
                    }
                }
            }
            Category category = null;
            for (Category cat : guild.getCategories()) {
                if (cat.getName().equalsIgnoreCase("-=- Tickets -=-")) {
                    category = cat;
                }
            }
            if (category != null) {
                ticketChannel.getManager().setParent(category).queue();
            }
            else {
                System.out.println("Category Not Found - New Order Ticket, Creating Category");
                guild.getController().createCategory("-=- Tickets -=-").queue(categoryAdd  -> {
                    ticketChannel.getManager().setParent((Category)categoryAdd).queue();
                });
            }
            for (Role r : guild.getRoles()) {
                if (r.equals(guild.getPublicRole())) {
                    ticketChannel.putPermissionOverride(r).setDeny(
                            Permission.CREATE_INSTANT_INVITE,
                            Permission.MANAGE_PERMISSIONS,
                            Permission.MANAGE_CHANNEL,
                            Permission.MANAGE_WEBHOOKS,
                            Permission.MESSAGE_TTS,
                            Permission.MESSAGE_MANAGE,
                            Permission.MESSAGE_EMBED_LINKS,
                            Permission.MESSAGE_ATTACH_FILES,
                            Permission.MESSAGE_MENTION_EVERYONE,
                            Permission.VIEW_CHANNEL).queue();
                }
            }
            for (Role r : guild.getRoles()){
                if(r.getName().equalsIgnoreCase("Management")){
                    ticketChannel.putPermissionOverride(r).setAllow(Permission.VIEW_CHANNEL).queue();
                }
            }
            ticketChannel.putPermissionOverride(member).setAllow(Permission.VIEW_CHANNEL).queue();
            ticketChannel.sendMessage(new EmbedBuilder()
                    .setColor(Color.decode("#228B22"))
                    .setTimestamp(OffsetDateTime.now())
                    .setTitle("Ticket Opened")
                    .setDescription("Dear " + sender.getAsMention() + ",\n\nThank you for reaching out to our support team!\nWe Will get back to you as soon as possible!\n\nSubject:\n```" + subject + "```")
                    .build()).queue();
            increaseTicketID(prop);

        }
    }

    public String getTicketID(Properties prop) {
        int num = Integer.parseInt(prop.getProperty("ticketID"));
        if(num <= 9) {
            return "000" + num;
        }else if(num <= 99) {
            return "00" + num;
        }else if(num <= 999) {
            return "0" + num;
        }else {
            return "" + num;
        }
    }

    public void increaseTicketID(Properties prop) {
        File config = new File("config.cfg");
        int num = Integer.parseInt(prop.getProperty("ticketID"));
        prop.setProperty("ticketID", ""+(num + 1));
        try {
            prop.store(new FileOutputStream(config), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommand() { return "new"; }

    @Override
    public String getDescription() { return "This command will create a new ticket for you"; }
}
