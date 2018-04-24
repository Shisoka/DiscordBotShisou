package com.shisokar.discord.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.shisokar.discord.bot.util.MsgSender;
import com.shisokar.discord.bot.util.STATIC;
import com.shisokar.discord.bot.util.YoutubeSearch;
import com.shisokar.discord.bot.web.YoutubeSearcher;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.shisokar.discord.bot.util.STATIC.ID_BECOME_AS_GODS;

public class Play extends CmdMusic {

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        //super.action(args, e);
        setGuild(e.getGuild());
        switch(args[0].toLowerCase()) {
            case "fp":
            case "fplay":
                play(args, e, true);
                break;

            case "play":
            case "p":
                play(args, e, false);
                break;
        }
    }

    private void play(String[] args, MessageReceivedEvent e, boolean now){
        e.getMessage().delete().queue();
        if(args.length < 2) {
            MsgSender.sendErrorMsg(e, "Please enter valid source!");
            return;
        }
        String input = Arrays.stream(args).skip(1).map(s -> " " + s).collect(Collectors.joining()).substring(1);

        if(! (input.startsWith("http://") || input.startsWith("https://"))){
            if(activeYTsearches.containsKey(e.getAuthor().getId())){
                return;
            }
            YoutubeSearch youtube = new YoutubeSearch(new YoutubeSearcher(STATIC.get_YOUTUBE_API_KEY()));
            youtube.search(e, input, now);
            return;
        }
        setMyEvent(e);
        loadTrack(e, input, e.getMember(), e.getMessage(), now);
    }

    private void loadTrack(MessageReceivedEvent event, String identifier, Member author, Message msg, final boolean now) {
        Guild guild = author.getGuild();
        getPlayer(guild); //creates player
        getIdleTimer(guild).stop();
        boolean playNow = !isIdle(guild) && now;
        String footer = author.getEffectiveName();
        String footerIcon = author.getUser().getAvatarUrl();

        MANAGER.setFrameBufferDuration(BUFFER_DURATION); //track buffer
        MANAGER.loadItemOrdered(guild, identifier, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                long startPosition = getMillisfromLink(msg.getContent());
                if(startPosition != 0){
                    track.setPosition(startPosition);
                }
                String desc = "";
                String name;
                String icon;
                if(playNow){
                    getManager(guild).playNow(track, author);
                    name = "Playing Now";
                    icon = STATIC.ARROR_FORWARD;
                } else {
                    getManager(guild).addToQueue(track, author);
                    name = "Queued";
                    icon = STATIC.MUSICAL_NOTE;
                }
                desc += "**Title:** "+track.getInfo().title+"\n";
                if(startPosition>0){
                    desc += "**Starting at:** `[" + getTimeStamp(startPosition) + " / " + getTimeStamp(track.getDuration()) + "]`\n";
                }
                if(track.getInfo().isStream)
                    desc += "**Link:**  __"+track.getIdentifier()+"__";
                else
                    desc += "**Link:**  __"+"https://youtu.be/"+track.getIdentifier()+"__";


                if(track.getIdentifier().equals(ID_BECOME_AS_GODS)) //easter egg for default help from command interface
                    return;
                MsgSender.sendEmbedMsg(event, name, icon, desc, track.getIdentifier(), footer, footerIcon);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                int count = playlist.getTracks().size() > PLAYLIST_LIMIT ? PLAYLIST_LIMIT : playlist.getTracks().size();
                if(count<1) return;
                String name = "Playlist Queued  ("+count+") songs";
                for(int i=0; i < count; i++) {
                    getManager(guild).addToQueue(playlist.getTracks().get(i), author);
                }
                System.out.println("identifier: "+identifier);
                String desc = "**Playlist:** "+playlist.getName()+"\n"
                        + "**Link:** __"+identifier+"__";
                String thumbnailId = playlist.getTracks().get(0).getIdentifier();
                MsgSender.sendEmbedMsg(event, name, STATIC.MUSICAL_NOTES, desc, thumbnailId, footer, footerIcon);
            }

            @Override
            public void noMatches() {
                MsgSender.sendEmbedMsg(event, null, ":musical_note: no matches.");
            }

            @Override
            public void loadFailed(FriendlyException e) {
                MsgSender.sendEmbedMsg(event, null, ":confused: whoops, something went wrong.");
            }
        });
    }

    private long getMillisfromLink (String link){
        String[] split = link.split("[?]");
        if(split.length < 2){
            return 0;
        }
        if(!split[1].startsWith("t=")){
            return 0;
        }
        String[] splitTime = split[1].split("[=hms]");
        String time = Arrays.stream(splitTime).skip(1).map(s -> ":" + s).collect(Collectors.joining()).substring(1);
        //System.out.println("time: "+time);
        return getMS(time);
    }

}
