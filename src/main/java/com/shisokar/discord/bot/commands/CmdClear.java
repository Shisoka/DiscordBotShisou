package com.shisokar.discord.bot.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import com.shisokar.discord.bot.util.TIME;

import java.util.List;

public class CmdClear implements Command{
    @Override
    public boolean called(String[] args, MessageReceivedEvent e){
        Command.super.called(args, e);
        if(args.length < 1){
            //e.getTextChannel().sendMessage("not enough arguments.").queue();
            //System.out.println(TIME.getINFO()+" not enough arguments.");
            //return false;
        } else {
            int arg = getInt(args[0]);
            if (arg < 1 || arg > 100) {
                e.getTextChannel().sendMessage("please enter a number between 1 and 100.").queue();
                if (arg != -1) {
                    System.out.println(TIME.getINFO() + " value out of bounds.");
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        int range = (args.length < 1) ? 1 : getInt(args[0]);
        MessageHistory history = new MessageHistory(e.getTextChannel());
        List<Message> msgs;

        e.getMessage().delete().queue();
        try {Thread.sleep(50);} catch (InterruptedException e1) { /**/ }

        msgs = history.retrievePast(range).complete();

        if(range == 1){
            msgs.get(0).delete().queue();
        } else {
            e.getTextChannel().deleteMessages(msgs).queue();
        }
        System.out.println(TIME.getINFO()+range+" message(s) deleted.");
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        //if(success){
        //    System.out.println(TIME.getINFO()+"command "+ STATIC.PREFIX+"clear executed.");
        //}
    }


    private int getInt(String str) {
        try{
            return Integer.parseInt(str);
        } catch (Exception e){
            System.out.println(TIME.getINFO()+" no integer");
            //e.printStackTrace();
            return -1;
        }
    }
}
