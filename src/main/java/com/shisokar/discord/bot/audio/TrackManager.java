package com.shisokar.discord.bot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.shisokar.discord.bot.commands.music.CmdMusic;
import com.shisokar.discord.bot.util.MsgSender;
import com.shisokar.discord.bot.util.TIME;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class TrackManager extends AudioEventAdapter {

    private final AudioPlayer PLAYER;
    //private final Queue<AudioInfo> QUEUE;
    private final BlockingDeque<AudioInfo> QUEUE;
    public AudioInfo lastSong;
    public AudioInfo curSong;

    public TrackManager(AudioPlayer PLAYER) {
        this.PLAYER = PLAYER;
        this.QUEUE = new LinkedBlockingDeque<>();
        this.lastSong = null;
        this.curSong  = null;
    }

    public void addToQueue(AudioTrack track, Member author) {
        AudioInfo info = new AudioInfo(track, author);
        QUEUE.add(info);
        if(PLAYER.getPlayingTrack() == null){
            PLAYER.playTrack(track);
        }
        //startNextTrack(true);
    }

    public void playNow(AudioTrack track, Member author){

        AudioInfo info = new AudioInfo(track, author);
        List<AudioInfo> cQueue = new ArrayList<>(getQueue());
        AudioInfo cur = cQueue.get(0);
        AudioInfo old = new AudioInfo(cur.getTrack().makeClone(), cur.getAuthor());
        old.getTrack().setPosition(cur.getTrack().getPosition());
        cQueue.remove(0);
        cQueue.add(0, cur); //gets skipped
        cQueue.add(1, info);
        cQueue.add(2, old);
        purgeQueue();
        QUEUE.addAll(cQueue);
        PLAYER.stopTrack();
    }

    private void startNextTrack(boolean noInterrupt){
        AudioInfo next = QUEUE.pollFirst();
        if(next != null){
            try {
                if (!PLAYER.startTrack(next.getTrack(), noInterrupt)) {
                    QUEUE.addFirst(next);
                }
            } catch (Exception e){
                System.out.println("exception.");
            }
        } else {
            PLAYER.stopTrack();
            System.out.println(TIME.getINFO()+"Queue finished.");
        }
    }

    public Set<AudioInfo> getQueue() {
        return new LinkedHashSet<>(QUEUE);
    }

    public AudioInfo getInfo(AudioTrack track) {
        return QUEUE.stream()
                .filter(info -> info.getTrack().equals(track))
                .findFirst().orElse(null);
    }

    public void purgeQueue() {
        QUEUE.clear();
    }

    public void shuffleQueue() {
        List<AudioInfo> cQueue = new ArrayList<>(getQueue());
        AudioInfo cur = cQueue.get(0);
        cQueue.remove(0);
        Collections.shuffle(cQueue);
        cQueue.add(0, cur);
        purgeQueue();
        QUEUE.addAll(cQueue);
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        AudioInfo info = QUEUE.element();
        VoiceChannel vChannel = info.getAuthor().getVoiceState().getChannel();
        curSong  = info;
        System.out.println(TIME.get()+" now playing: "+track.getInfo().title+"\n"+
                            "\t   requested by: "+info.getAuthor().getEffectiveName());
        //////
        //System.out.println("lastsong: "+((lastSong==null)?"null":lastSong.title));
        //System.out.println("cursong:  "+((curSong ==null)?"null": curSong.title));
        //////
        if(vChannel == null){
            player.stopTrack();
        } else {
            info.getAuthor().getGuild().getAudioManager().openAudioConnection(vChannel);
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        Guild g = QUEUE.poll().getAuthor().getGuild();
        lastSong = curSong;
        curSong = null;
        if(QUEUE.isEmpty()){
            //g.getAudioManager().closeAudioConnection();
            //System.out.println(TIME.getINFO()+" left voice channel of " + g.getName());
            System.out.println(TIME.getINFO()+"Queue finished.");
            CmdMusic m = new CmdMusic();
            m.getIdleTimer(g).setIdle();
        } else {
            player.playTrack(QUEUE.element().getTrack());
        }
    }

}
