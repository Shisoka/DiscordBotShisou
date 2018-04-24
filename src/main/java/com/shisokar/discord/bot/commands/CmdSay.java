package com.shisokar.discord.bot.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import com.shisokar.discord.bot.util.STATIC;
import com.shisokar.discord.bot.util.TIME;

import java.util.Arrays;
import java.util.List;

public class CmdSay implements Command {

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        e.getMessage().delete().queue();
        List argsList = Arrays.asList(args);
        StringBuilder sb = new StringBuilder();
        argsList.stream().forEach(s -> sb.append(s + " "));
        //Arrays.stream(args).skip(1).map(s -> " " + s).collect(Collectors.joining()).substring(1);
        e.getTextChannel().sendMessage(sb.toString()).queue();
        System.out.println(TIME.get()+" "+e.getJDA().getSelfUser().getName()+": "+sb.toString());
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if(success){
            System.out.println(TIME.getINFO()+"command "+ STATIC.PREFIX+"say executed.");
        }
    }

}
