package com.shisokar.discord.bot.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Member;

public class AudioInfo {

    private final AudioTrack TRACK;
    private final Member AUTHOR;

    public AudioInfo(AudioTrack track, Member autor){
        this.TRACK = track;
        this.AUTHOR = autor;
    }

    public AudioTrack getTrack(){
        return TRACK;
    }

    public Member getAuthor(){
        return AUTHOR;
    }

}
