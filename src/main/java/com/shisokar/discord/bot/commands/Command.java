package com.shisokar.discord.bot.commands;

import com.shisokar.discord.bot.commands.music.Play;
import com.shisokar.discord.bot.util.MsgSender;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import com.shisokar.discord.bot.util.TIME;

import java.awt.*;

import static com.shisokar.discord.bot.util.STATIC.GIF_BECOME_AS_GODS;
import static com.shisokar.discord.bot.util.STATIC.HELP_BECOME_AS_GODS;
import static com.shisokar.discord.bot.util.STATIC.YT_BECOME_AS_GODS;

public interface Command {

    default boolean called(String[] args, MessageReceivedEvent e){
        System.out.println(TIME.get()+" >>"+e.getMember().getEffectiveName()+": "+e.getMessage().getContent());
        return true;
    }
    default void invAction(String inv, String[] args, MessageReceivedEvent e){
        String[] newArgs = new String[args.length+1];
        newArgs[0] = inv;
        //System.out.println("newArgs[0] = "+newArgs[0]);
        for(int i = 0; i<args.length; ++i) {
            newArgs[i + 1] = args[i];
            //System.out.println("newArgs["+(i+1)+"] = "+newArgs[i+1]);
        }
        action(newArgs, e);
    }
    void    action(String[] args, MessageReceivedEvent e);
    void    executed(boolean success, MessageReceivedEvent e);
    default String help(MessageReceivedEvent e){
        String[] args = {"fplay", YT_BECOME_AS_GODS};
        new Play().action(args, e);
        EmbedBuilder embed = new EmbedBuilder();
        embed.setImage(GIF_BECOME_AS_GODS);
        embed.setDescription(HELP_BECOME_AS_GODS);
        embed.setColor(Color.RED);
        e.getTextChannel().sendMessage(embed.build()).queue();
        return null;
    }

}
