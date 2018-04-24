package com.shisokar.discord.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.shisokar.discord.bot.audio.AudioInfo;
import com.shisokar.discord.bot.util.MsgSender;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Queue extends CmdMusic {

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        //super.action(args, e);
        setGuild(e.getGuild());

        switch(args[0].toLowerCase()) {
            case "queue":
            case "q":
                printQueue(args, e);
                break;

            case "qlist":
                printQueueList(args, e);
                break;
        }
    }

    private void printQueue(String[] args, MessageReceivedEvent e){
        if(isIdle(guild) || getManager(guild).getQueue().isEmpty()){
            MsgSender.sendEmbedMsg(e, null, "Queue is empty.");
            e.getMessage().delete().queue();
            return;
        }

        List<String> tracks = new ArrayList<>();

        getManager(guild).getQueue().forEach(audioInfo -> tracks.add(buildQueueMessage(audioInfo)));

        MsgSender.sendEmbedMsg(e, null, "**CURRENT QUEUE**  has `"+tracks.size()+"` songs remaining.\n" +
                                "Total Duration: "+getTimeStamp(totalQueueDuration()));
        e.getMessage().delete().queue();
    }

    private long totalQueueDuration(){
        long totalQueueDuration = 0;
        for(AudioInfo i : getManager(guild).getQueue()){
            totalQueueDuration += i.getTrack().getDuration()-i.getTrack().getPosition();
        }
        return totalQueueDuration;
    }

    private void printQueueList(String[] args, MessageReceivedEvent e){
        if(!hasPlayer(guild))
            return;
        if(getManager(guild).getQueue().isEmpty()){
            MsgSender.sendEmbedMsg(e, null, "Queue is empty.");
            e.getMessage().delete().queue();
            return;
        }

        int sideNumb = args.length > 1 ? Integer.parseInt(args[1]) : 1;
        List<String> tracks = new ArrayList<>();
        List<String> trackSublist;

        getManager(guild).getQueue().forEach(audioInfo -> tracks.add(buildQueueMessage(audioInfo)));

        if(tracks.size() > QUEUE_PAGE_SIZE) {
            trackSublist = tracks.subList((sideNumb - 1) * QUEUE_PAGE_SIZE, (sideNumb - 1) * QUEUE_PAGE_SIZE + QUEUE_PAGE_SIZE);
        } else {
            trackSublist = tracks;
        }

        String out = trackSublist.stream().collect(Collectors.joining("\n"));
        int sideNumbAll = tracks.size() >= QUEUE_PAGE_SIZE ? tracks.size() / QUEUE_PAGE_SIZE : 1;

        String desc = "**CURRENT QUEUE**  "+
                "["+getManager(guild).getQueue().size() + " Tracks | Page " + sideNumb + " - " + sideNumbAll +"]" +
                "  Total Duration: "+getTimeStamp(totalQueueDuration())+"\n\n"+
                out;
        MsgSender.sendEmbedMsg(e, null, desc);
        e.getMessage().delete().queue();
    }

    private String buildQueueMessage(AudioInfo info) {
        AudioTrackInfo trackInfo = info.getTrack().getInfo();
        String title = trackInfo.title;
        long length = trackInfo.length;
        return "`["+getTimeStamp(length)+"]` "+title + '\n';
    }

}
