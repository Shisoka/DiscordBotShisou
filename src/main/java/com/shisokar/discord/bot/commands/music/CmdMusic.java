package com.shisokar.discord.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.shisokar.discord.bot.audio.PlayerSendHandler;
import com.shisokar.discord.bot.audio.TrackManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.shisokar.discord.bot.commands.Command;
import com.shisokar.discord.bot.util.IdleTimer;
import com.shisokar.discord.bot.util.MsgSender;
import com.shisokar.discord.bot.util.YoutubeSearch;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import com.shisokar.discord.bot.util.TIME;

import java.util.*;



public class CmdMusic implements Command {

    static final int PLAYLIST_LIMIT = 200;
    static final int BUFFER_DURATION = 3500; //track buffer in ms
    static final int QUEUE_PAGE_SIZE = 15; //max entries per page in queue
    static Guild guild;
    static MessageReceivedEvent myEvent = null;
    static final AudioPlayerManager MANAGER = new DefaultAudioPlayerManager();
    static final Map<Guild, Map.Entry<AudioPlayer, TrackManager>> PLAYERS = new HashMap<>();
    public static HashMap<String, YoutubeSearch> activeYTsearches = new HashMap<>();
    public static HashMap<Guild, IdleTimer> idleTimer = new HashMap<>();
    String voiceChannel = "";

    public CmdMusic() {
        MANAGER.registerSourceManager(new YoutubeAudioSourceManager());
        MANAGER.registerSourceManager(new TwitchStreamAudioSourceManager());
        AudioSourceManagers.registerRemoteSources(MANAGER);
    }

    private AudioPlayer createPlayer(Guild g) {
        AudioPlayer p = MANAGER.createPlayer();
        TrackManager m = new TrackManager(p);
        p.addListener(m);

        guild.getAudioManager().setSendingHandler(new PlayerSendHandler(p));

        PLAYERS.put(g, new AbstractMap.SimpleEntry<>(p, m));

        IdleTimer timer = new IdleTimer(g);
        idleTimer.put(g, timer);
        timer.run();
        return p;
    }

    boolean hasPlayer(Guild g) {
        return PLAYERS.containsKey(g);
    }

    public AudioPlayer getPlayer(Guild g) {
        if(hasPlayer(g)){
            return PLAYERS.get(g).getKey();
        } else {
            voiceChannel = myEvent.getMember().getVoiceState().getChannel().getName();
            System.out.println(TIME.getINFO()+"connected voice channel ("+guild.getName()+") "+ voiceChannel);
            return createPlayer(g);
        }
    }

    public IdleTimer getIdleTimer(Guild g){
        return idleTimer.get(g);
    }

    TrackManager getManager(Guild g) {
        return PLAYERS.get(g).getValue();
    }

    boolean isIdle(Guild g) {
        return !hasPlayer(g) || getPlayer(g).getPlayingTrack() == null;
    }

    void skip(Guild g) {
        getPlayer(g).stopTrack();
    }

    String getTimeStamp(long ms){
         long sec = ms / 1000;
         long h   = Math.floorDiv(sec, 3600);
         sec = sec - (h * 3600);
         long min = Math.floorDiv(sec, 60);
         sec = sec - (min * 60);
         return (h == 0 ? "" : h + ":") + String.format("%02d",min) + ":" + String.format("%02d", sec);
    }

    long getMS (String timestamp){
        if (timestamp == null){
            return -1;
        }
        String[] strTimes = timestamp.split(":");
        if(strTimes.length < 1){
            return -1;
        }
        long[] times = new long[3]; //hours:minutes:seconds
        times[0] = 0;
        times[1] = 0;
        times[2] = 0;

        for(int i=0; i<strTimes.length; ++i){
            long tmp = getLong(strTimes[strTimes.length-1-i]);
            if(tmp == -1){
                return -1;
            } else {
                times[times.length-1-i] = tmp;
            }
        }
        //System.out.println("0="+times[0]+", [1]="+times[1]+", [2]="+times[2]);
        return (/*hours*/times[0]*3_600_000) + (/*minutes*/times[1]*60000) + (/*seconds*/times[2]*1000);
    }

    private long getLong(String str) {
        try{
            return Long.parseLong(str);
        } catch (Exception e){
            System.out.println(TIME.getINFO()+str+" is no long.");
            //e.printStackTrace();
            return -1;
        }
    }

    boolean currentSongIsStream(Guild guild){
        return getPlayer(guild).getPlayingTrack().getInfo().isStream;
    }






////////////////////////////////////////
//COMMANDS
////////////////////////////////////////





////////////////////////////////////////
//END OF COMMANDS
////////////////////////////////////////

    public void setGuild(Guild g){
        guild = g;
    }

    public void setMyEvent(MessageReceivedEvent e){
        myEvent = e;
    }


    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        return Command.super.called(args, e);
        //not needed as of invCommands in CommandHandler
        //if(args.length < 1){
        //    sendErrorMsg(e, help());
        //    return false;
        //}
        //return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        setGuild(e.getGuild());
        switch(args[0].toLowerCase()){
            case "help":
            case "h":
                MsgSender.sendEmbedMsg(e, "COMMANDS", help(e));
                e.getMessage().delete().queue();
                break;
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if(success)
            System.out.println(TIME.getINFO()+ "command executed.");
        else
            System.out.println(TIME.getINFO()+ "error.");
    }

    @Override
    public String help(MessageReceivedEvent e) {
        String help =   //not needed as of invCommands in CommandHandler
                        //".music <command>\n" +
                        //".m <command>"+
                        "```" +
                        "`.play <url>`  or `.p <url>`  *add to queue*\n" +
                        "`.fplay <url>` or `.fp <url>` *play now* \n" +
                        "\n"+
                        "`.pause`           *pauses current track*\n"+
                        "`.resume`          *resumes current track* \n" +
                        "`.stop`            *stop the player*\n" +
                        "\n" +
                        "`.forward <h:m:s>` or `.fw <h:m:s>`\n" +
                        "`.rewind <h:m:s>`\n" +
                        "`.seek <h:m:s>`\n" +
                        "\n"+
                        "`.now` or `.np`    *displays currently playing track*\n" +
                        "`.last`            *last song*\n" +
                        "\n"+
                        "`.skip` or `.s`    *skips current track*\n" +
                        "`.skip <1 to 100>` *skips <1 to 100> tracks*\n" +
                        "\n" +
                        "`.shuffle`         *shuffles playlist*\n" +
                        "`.queue` or `.q`   *displays queue*\n" +
                        "`.qlist <page>`    *displays <page> of queue*\n" +
                        "\n" +
                        "`.leave`           *disconnects from voice*\n" +
                        "```";
        return help;
    }

}
