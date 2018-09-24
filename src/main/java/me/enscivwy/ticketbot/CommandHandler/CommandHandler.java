package me.enscivwy.ticketbot.CommandHandler;

import me.enscivwy.ticketbot.TicketBot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
    @Override

    public void onMessageReceived(MessageReceivedEvent e) {
        if(!e.getMessage().getContentRaw().startsWith(TicketBot.getPrefix())) return;
        if(e.getAuthor().isBot()) return;
        String commandName = e.getMessage().getContentRaw().split(" ")[0].replaceAll(TicketBot.getPrefix(), "");
        String message = e.getMessage().getContentRaw();
        for(Command cmd : TicketBot.getCommands()) {
            if(commandName.equalsIgnoreCase(cmd.getCommand())) {
                System.out.println(commandName + " command was executed in channel " + e.getTextChannel().getName() + " by " + e.getAuthor().getName());
                String[] args = message.substring(message.indexOf(" ") + 1).split(" ");
                cmd.onCommand(e.getAuthor(), e.getTextChannel(), e.getMessage(), args, e.getMember(), e.getGuild());
                return;
            }
        }
    }
}
