package com.shisokar.discord.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.shisokar.discord.bot.util.MsgSender;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Pause extends CmdMusic {

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        //super.action(args, e);
        setGuild(e.getGuild());
        if(!currentSongIsStream(guild))
            pausePlayer(e);
    }

    private void pausePlayer(MessageReceivedEvent e){
        if(isIdle(guild)) return;
        AudioPlayer player = getPlayer(guild);
        if(!player.isPaused())
            player.setPaused(true);

        AudioTrack track = player.getPlayingTrack();
        String desc = ":pause_button: `["+getTimeStamp(track.getPosition())+" / "+getTimeStamp(track.getDuration())+"]` "+
                player.getPlayingTrack().getInfo().title;
        MsgSender.sendEmbedMsg(e, null, desc);
        e.getMessage().delete().queue();
        idleTimer.get(guild).songPaused();
    }

}
