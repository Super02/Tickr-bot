package me.enscivwy.ticketbot.Commands.Basic;

import me.enscivwy.ticketbot.CommandHandler.Command;
import net.dv8tion.jda.core.entities.*;

public class PingCommand implements Command {
    @Override
    public void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member, Guild guild) {
        long first = System.currentTimeMillis();
        channel.sendMessage("Pong! `Testing...`").queue(msg -> msg.editMessage("Pong! `" + (System.currentTimeMillis() - first) + "`").queue());
        // why not huh
    }

    @Override
    public String getCommand() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Pong!";
    }
}
