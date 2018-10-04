package com.shisokar.discord.bot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.shisokar.discord.bot.util.TIME;
import net.dv8tion.jda.core.audio.AudioSendHandler;

import static com.shisokar.discord.bot.util.STATIC.get_AUDIO_VOLUME;

public class PlayerSendHandler implements AudioSendHandler {

    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

    public PlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        int volume = get_AUDIO_VOLUME();
        System.out.println(TIME.getINFO()+"Audio Volume = "+volume+"%");
        this.audioPlayer.setVolume(volume);
    }

    @Override
    public boolean canProvide() {
        if(lastFrame == null){
            lastFrame = audioPlayer.provide();
        }

        return lastFrame != null;
    }

    @Override
    public byte[] provide20MsAudio() {
        if(lastFrame == null){
            lastFrame = audioPlayer.provide();
        }

        byte[] data = lastFrame != null ? lastFrame.data : null;
        lastFrame = null;

        return data;
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
