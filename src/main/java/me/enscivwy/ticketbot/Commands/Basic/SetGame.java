package me.enscivwy.ticketbot.Commands.Basic;

import me.enscivwy.ticketbot.CommandHandler.Command;
import me.enscivwy.ticketbot.TicketBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;

public class SetGame implements Command {
    @Override
    public void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member,
                          Guild guild) {
        ArrayList<String> access = new ArrayList<>();
        access.add("305246941992976386");
        access.add("234255082345070592");
        if(access.contains(sender.getId())) {
            if(args.length >= 2) {
                StringBuilder x = new StringBuilder();
                for(int i = 1; i < args.length; i++) {
                    x.append(args[i] + " ");
                }
                TicketBot.getInstance().getPresence().setGame(Game.of(Game.GameType.valueOf(args[0].toUpperCase()), x.toString()));
                channel.sendMessage(new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTimestamp(OffsetDateTime.now())
                        .setTitle("Success")
                        .setDescription("The bot's status has been changed to " + x.toString())
                        .build()).queue();
            }else {
                channel.sendMessage(new EmbedBuilder()
                        .setColor(Color.decode("#ff0000"))
                        .setTimestamp(OffsetDateTime.now())
                        .setTitle("An Error Occured")
                        .setDescription("Incorrect Usage: ``-setgame (GAME STATUS) (GAME PLAYING)``")
                        .build()).queue();
            }
        }
    }

    @Override
    public String getCommand() { return "setgame"; }

    @Override
    public String getDescription() { return "Change the bot's game text."; }
}
