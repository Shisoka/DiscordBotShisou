package com.shisokar.discord.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.shisokar.discord.bot.util.MsgSender;
import com.shisokar.discord.bot.util.TIME;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SeekRewindForward extends CmdMusic {

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        //super.action(args, e);
        setGuild(e.getGuild());

        if(currentSongIsStream(guild)){
            return;
        }

        switch(args[0].toLowerCase()) {
            case "seek":
                seek(args, e);
                break;

            case "rewind":
                rewind(args, e);
                break;

            case "forward":
                forward(args, e);
        }

    }

    private void seek(String[] args, MessageReceivedEvent e){
        e.getMessage().delete().queue();
        if(args.length < 2) {
            MsgSender.sendErrorMsg(e, "Please enter a valid time!");
            System.out.println(TIME.getINFO()+"no valid time");
            return;
        }
        long position = getMS(args[1]);
        if(position == 0){
            MsgSender.sendErrorMsg(e, "\""+args[1]+"\" is no valid time");
            System.out.println(TIME.getINFO()+"no valid time");
        }
        AudioTrack song = getPlayer(guild).getPlayingTrack();
        String msg = "**Title:** "+song.getInfo().title+"\n"
                +"**Seek:** `["+getTimeStamp(song.getPosition()) + "] ➜ "
                + (song.getDuration() < position ?
                "[next song]`" : "["+getTimeStamp(position)+"]`");
        MsgSender.sendEmbedMsg(e, null, msg);
        forPlayingTrack(track -> {
            track.setPosition(position);
        });
    }

    private void rewind(String[] args, MessageReceivedEvent e){
        e.getMessage().delete().queue();
        if(args.length < 2) {
            MsgSender.sendErrorMsg(e, "Please enter a valid duration!");
            System.out.println(TIME.getINFO()+"no valid duration");
            return;
        }
        long duration = getMS(args[1]);
        if(duration == 0){
            MsgSender.sendErrorMsg(e, "\""+args[1]+"\" is no valid duration");
            System.out.println(TIME.getINFO()+"no valid duration");
        }
        AudioTrack song = getPlayer(guild).getPlayingTrack();
        String msg = "**Title:** "+song.getInfo().title+"\n"
                +"**Rewind:** `["+getTimeStamp(song.getPosition()) + "] ⏪ "
                + (0 > song.getPosition()-duration ?
                "[0:00]`" : "["+getTimeStamp(song.getPosition()-duration)+"]`");

        MsgSender.sendEmbedMsg(e, null, msg);
        forPlayingTrack(track -> {
            track.setPosition(Math.max(0, track.getPosition()-duration));
        });
    }

    private void forward(String[] args, MessageReceivedEvent e){
        e.getMessage().delete().queue();
        if(args.length < 2) {
            MsgSender.sendErrorMsg(e, "Please enter a valid duration!");
            System.out.println(TIME.getINFO()+"no valid duration");
            return;
        }
        long duration = getMS(args[1]);
        if(duration == 0){
            MsgSender.sendErrorMsg(e, "\""+args[1]+"\" is no valid duration");
            System.out.println(TIME.getINFO()+"no valid duration");
        }
        AudioTrack song = getPlayer(guild).getPlayingTrack();
        String msg = "**Title:** "+song.getInfo().title+"\n"
                +"**Fast Forward:** `["+getTimeStamp(song.getPosition()) + "] ⏩ "
                + (song.getDuration() < song.getPosition()+duration ?
                "next song`" : "["+getTimeStamp(song.getPosition()+duration)+"]`");
        MsgSender.sendEmbedMsg(e, null, msg);
        forPlayingTrack(track -> {
            track.setPosition(track.getPosition()+duration);
        });
    }

    private interface TrackOperation {
        void execute(AudioTrack track);
    }

    private void forPlayingTrack(TrackOperation operation) {
        AudioTrack track = getPlayer(guild).getPlayingTrack();

        if (track != null) {
            operation.execute(track);
        }
    }

}
