package me.enscivwy.ticketbot.Commands.Basic;

import me.enscivwy.ticketbot.CommandHandler.Command;
import me.enscivwy.ticketbot.TicketBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.time.OffsetDateTime;

public class HelpCommand implements Command {
    @Override
    public void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member, Guild guild) {
        StringBuilder sb = new StringBuilder();
        for(Command cmd : TicketBot.getCommands()) {
            sb.append(TicketBot.getPrefix()).append("**").append(cmd.getCommand()).append("**").append(" - ").append(cmd.getDescription()).append("\n");
        }
        channel.sendMessage(new EmbedBuilder()
                .setTitle("Help Menu", null)
                .setDescription("Need help with the bot? Right here! \n\n" + sb.toString().trim() + "\n\n")
                .setTimestamp(OffsetDateTime.now())
                .setColor(Color.decode("#ffffff"))
                .build()).queue();
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Will give you a list of commands you can use";
    }
}
