package com.shisokar.discord.bot.core;

import com.shisokar.discord.bot.commands.Command;

import java.util.HashMap;

public class CommandHandler {

    public static final CommandParser parse = new CommandParser();
    public static HashMap<String, Command> commands = new HashMap<>();
    public static HashMap<String, Command> invCommands = new HashMap<>();

    public static void handleCommand(CommandParser.CommandContainer cmd) {

        if(invCommands.containsKey(cmd.invoke)){
            boolean safe = invCommands.get(cmd.invoke).called(cmd.args, cmd.event);
            if(safe){
                invCommands.get(cmd.invoke).invAction(cmd.invoke, cmd.args, cmd.event);
                invCommands.get(cmd.invoke).executed(safe, cmd.event);
            } else {
                invCommands.get(cmd.invoke).executed(safe, cmd.event);
            }

        } else if(commands.containsKey(cmd.invoke)){
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
            if(safe){
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            } else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }


        } else {
            //invalid command
        }

    }

}
