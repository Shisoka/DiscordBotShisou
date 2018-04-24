package com.shisokar.discord.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.shisokar.discord.bot.audio.AudioInfo;
import com.shisokar.discord.bot.util.MsgSender;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Skip extends CmdMusic {

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        //super.action(args, e);
        setGuild(e.getGuild());

        skipTrack(args, e);
    }

    private void skipTrack(String[] args, MessageReceivedEvent e){
        if(isIdle(guild)) return;
        int count = args.length > 1 ? Integer.parseInt(args[1]):1;

        AudioInfo curSong = getManager(guild).curSong;
        AudioTrack track = getPlayer(guild).getPlayingTrack();
        String desc = ":track_next: skipped";
        if(count == 1 && track != null){
            desc += ": " + track.getInfo().title;
        } else {
            desc += " `"+count+"` songs.";
        }
        for(int i=count; i>=1; i--){
            skip(guild);
        }
        MsgSender.sendEmbedMsg(e, null, desc);
        e.getMessage().delete().queue();
        //nowPlaying(args, e);
        getManager(guild).lastSong = curSong;
    }

}
