package com.shisokar.discord.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.shisokar.discord.bot.util.MsgSender;
import com.shisokar.discord.bot.util.STATIC;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class NowPlaying extends CmdMusic {

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        //super.action(args, e);
        setGuild(e.getGuild());

        nowPlaying(e);
    }

    private void nowPlaying(MessageReceivedEvent e){
        e.getMessage().delete().queue();
        if(isIdle(guild)){
            MsgSender.sendEmbedMsg(e, "No song is playing.");
            return;
        }
        AudioTrack track = getPlayer(guild).getPlayingTrack();
        AudioTrackInfo info = track.getInfo();

        Member author = getManager(guild).curSong.getAuthor();
        String footer = author.getEffectiveName();
        String footerIcon = author.getUser().getAvatarUrl();

        if(currentSongIsStream(guild)){//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< is Stream
            String desc = "";
            String thumbnail = "";
            if(info.identifier.contains("twitch")) {
                String streamer = info.identifier;
                streamer = streamer.replace("https://www.twitch.tv/", "");
                desc += "**Streamer: " + streamer + "**\n"
                     + "**Title:** " + info.title + "\n"
                     + "**Link:** __" + info.identifier + "__";
                thumbnail = null;
            }
            else{
                desc += "**Title:** " + info.title + "\n" +
                        "**Link:** __http://youtu.be/" + info.identifier + "__";
                thumbnail += STATIC.YOUTUBE_THUMBNAIL.replace("<ID>", info.identifier);
            }
            MsgSender.sendEmbedMsgStream(e, "NOW PLAYING - STREAM", desc, thumbnail, footer, footerIcon);
        }
        else {//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< no Stream
            String paused = "";
            if (getPlayer(guild).isPaused()) {
                paused = " :pause_button:";
            }
            String desc = "**Title:** " + info.title + "\n" +
                    "**Duration:** `[" + getTimeStamp(track.getPosition()) + " / " + getTimeStamp(track.getDuration()) + "]`" + paused + "\n" +
                    "**Link:** __http://youtu.be/" + info.identifier + "__";
            MsgSender.sendEmbedMsg(e, "NOW PLAYING", null, desc, info.identifier, footer, footerIcon);
        }
    }

}
