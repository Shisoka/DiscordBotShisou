package com.shisokar.discord.bot.commands;

import com.shisokar.discord.bot.util.MsgSender;
import com.shisokar.discord.bot.util.STATIC;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CmdHelp implements Command {
    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        //if(help(e) != null)
        //    MsgSender.sendMsg(e, help(e));
        MsgSender.sendEmbedMsg(e, STATIC.MUSIC_HELP);
     }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {

    }
}
