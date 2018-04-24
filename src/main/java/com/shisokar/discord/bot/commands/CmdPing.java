package com.shisokar.discord.bot.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import com.shisokar.discord.bot.util.STATIC;
import com.shisokar.discord.bot.util.TIME;

public class CmdPing implements Command {
    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        e.getTextChannel().sendMessage("pong!").queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if(success){
            System.out.println(TIME.get()+" [INFO] command "+ STATIC.PREFIX+"ping executed.");
        }
    }

}
