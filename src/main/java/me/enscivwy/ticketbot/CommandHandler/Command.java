package me.enscivwy.ticketbot.CommandHandler;

import net.dv8tion.jda.core.entities.*;

public interface Command {
    void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member, Guild guild);
    String getCommand();
    String getDescription();
}
