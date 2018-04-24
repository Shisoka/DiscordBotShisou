package com.shisokar.discord.bot.commands.music;

import com.shisokar.discord.bot.util.MsgSender;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Stop extends CmdMusic {

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        //super.action(args, e);
        setGuild(e.getGuild());

        stopPlayer(args, e);
    }

    private void stopPlayer(String[] args, MessageReceivedEvent e){
        if(isIdle(guild)) return;
        getManager(guild).purgeQueue();
        skip(guild);
        getIdleTimer(guild).setIdle();
        //guild.getAudioManager().closeAudioConnection();
        //System.out.println(TIME.getINFO()+" left voice channel: ("+guild.getName()+") "+ voiceChannel);
        MsgSender.sendEmbedMsg(e, null, ":stop_button: player stopped.");
        e.getMessage().delete().queue();
    }

}
