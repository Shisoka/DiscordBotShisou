package com.shisokar.discord.bot.commands;

import com.shisokar.discord.bot.util.STATIC;
import com.shisokar.discord.bot.util.TIME;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CmdVersion implements Command {
    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        e.getMessage().delete().queue();
        e.getTextChannel().sendMessage(STATIC.UPDATE_MSG).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if(success){
            System.out.println(TIME.get()+" [INFO] command print update_msg executed.");
        }
    }

}