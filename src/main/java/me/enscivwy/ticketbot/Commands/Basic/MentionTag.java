package me.enscivwy.ticketbot.Commands.Basic;

import me.enscivwy.ticketbot.CommandHandler.Command;
import me.enscivwy.ticketbot.TicketBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.time.OffsetDateTime;

public class MentionTag extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getAuthor().isBot()) return;
        if(e.getMessage().getContentRaw().equalsIgnoreCase("<@470564628443955202>")) {
            StringBuilder sb = new StringBuilder();
            for(Command cmd : TicketBot.getCommands()) {
                sb.append(TicketBot.getPrefix()).append("**").append(cmd.getCommand()).append("**").append(" - ").append(cmd.getDescription()).append("\n");
            }
            e.getChannel().sendMessage(new EmbedBuilder()
                    .setTitle("Help Menu", null)
                    .setDescription("Need help with the bot? Right here! \n\n" + sb.toString().trim() + "\n\n")
                    .setTimestamp(OffsetDateTime.now())
                    .setColor(Color.decode("#ffffff"))
                    .build()).queue();
        }
    }
}
