package com.shisokar.discord.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.shisokar.discord.bot.util.MsgSender;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Resume extends CmdMusic {

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        //super.action(args, e);
        setGuild(e.getGuild());

        resumePlayer(e);
    }

    private void resumePlayer(MessageReceivedEvent e){
        if(!hasPlayer(guild)) return;
        AudioPlayer player = getPlayer(guild);
        if(player.isPaused()){
            player.setPaused(false);
        }
        AudioTrack track = player.getPlayingTrack();
        String desc = ":arrow_forward: `["+getTimeStamp(track.getPosition())+" / "+getTimeStamp(track.getDuration())+"]` "+
                player.getPlayingTrack().getInfo().title;
        MsgSender.sendEmbedMsg(e, null, desc);
        e.getMessage().delete().queue();
        idleTimer.get(guild).songResumed();
    }
}
