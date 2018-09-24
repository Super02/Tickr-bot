package me.enscivwy.ticketbot;

import me.enscivwy.ticketbot.CommandHandler.Command;
import me.enscivwy.ticketbot.CommandHandler.CommandHandler;
import me.enscivwy.ticketbot.Commands.Basic.*;
import me.enscivwy.ticketbot.Commands.Ticketing.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class TicketBot {
    private static String token;
    private static String prefix;
    private static JDA instance;
    private static HashSet<Command> commandSet = new HashSet<>();

    public static void main(String[] args) {
        File config = new File("config.cfg");
        Properties prop = new Properties();
        try {
            if(!config.exists()) {
                config.createNewFile();
                // Setting variables in the "prop" object. This is what we're saving to the file "config".
                prop.setProperty("token", "token here");
                prop.setProperty("prefix", "-");
                prop.setProperty("ticketID", "1");
                // Saves "prop" to "config".
                prop.store(new FileOutputStream(config), null);
                System.out.println("Config created as 'config.cfg'. Please set your token and prefix there before re-running the program.");
                System.exit(0);
            } else {
                // Loading the properties, since config exists.
                prop.load(new FileInputStream(config));
                token = prop.getProperty("token");
                prefix = prop.getProperty("prefix");
            }
            instance = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .setAudioEnabled(false)
                    .addEventListener(new CommandHandler())
                    .addEventListener(new MentionTag())
                    .addEventListener(new ReactionListeners())
                    .addEventListener(new AletsRole())
                    .build();
            run();
        } catch (LoginException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void run() {
        //Basic
        registerCommand(new PingCommand());
        registerCommand(new HelpCommand());
        registerCommand(new SetGame());
        registerCommand(new AlertCommand());
        //Ticketing
        registerCommand(new NewTicket());
        registerCommand(new CloseTicket());
        registerCommand(new AddMember());
        registerCommand(new RemoveMember());
        registerCommand(new TicketPerm());
        registerCommand(new RemoveTicketPerm());
        getInstance().getPresence().setGame(Game.of(Game.GameType.WATCHING, "and helping -help"));
        getInstance().getPresence().setStatus(OnlineStatus.ONLINE);
    }
    private static void registerCommand(Command cmd) {
        commandSet.add(cmd);
        System.out.println("Command " + cmd.getCommand() + " has been registered.");
    }

    public static JDA getInstance() { return instance; }
    public static String getPrefix() { return prefix; }
    public static Set<Command> getCommands() { return commandSet; }
    public static Boolean closeVerify;
}
