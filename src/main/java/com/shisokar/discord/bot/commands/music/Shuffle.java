package com.shisokar.discord.bot.commands.music;

import com.shisokar.discord.bot.util.MsgSender;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Shuffle extends CmdMusic {

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        //super.action(args, e);
        setGuild(e.getGuild());

        shuffle(e);
    }

    private void shuffle(MessageReceivedEvent e){
        if(isIdle(guild)) return;
        getManager(guild).shuffleQueue();
        MsgSender.sendEmbedMsg(e, null, ":twisted_rightwards_arrows: shuffeled queue.");
        e.getMessage().delete().queue();
    }

}
