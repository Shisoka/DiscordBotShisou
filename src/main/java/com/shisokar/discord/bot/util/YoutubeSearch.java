package com.shisokar.discord.bot.util;

import com.shisokar.discord.bot.commands.music.CmdMusic;
import com.shisokar.discord.bot.commands.music.Play;
import com.shisokar.discord.bot.web.YoutubeSearcher;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.List;

public class YoutubeSearch implements Runnable {
    public static HashMap<String, Thread> searches = new HashMap<>();
    private static final long  DECISION_TIME = 12; //seconds
    public  static final int   MAX_RESULTS = 8;
    private final YoutubeSearcher youtube;
    private boolean playNow;
    public int     decision  = -1;
    public String  queryResultId = null;
    public String  channelId = "";
    public String  authorId  = "";
    public MessageReceivedEvent event = null;
    public List<String> searchResults;
    public TextChannel textChannel = null;

    public YoutubeSearch(YoutubeSearcher youtube){
        this.youtube = youtube;
    }

    public boolean search(MessageReceivedEvent event, String query, final boolean playNow) {
        this.playNow = playNow;
        authorId = event.getAuthor().getId();
        channelId = event.getChannel().getId();
        textChannel = event.getTextChannel();
        event.getChannel().sendTyping();
        searchResults = youtube.getResults(query, MAX_RESULTS);
        if(searchResults == null){
            MsgSender.sendErrorMsg(event, "An error occured while searching");
            return false;
        }
        if(searchResults.isEmpty()){
            MsgSender.sendEmbedMsg(event, null, "No results found for \""+query+"\"");
            return false;
        }
        CmdMusic.activeYTsearches.put(authorId, this);
        printQueryResults(event);
        searches.put(authorId, new Thread(this));
        searches.get(authorId).start();
        return true;
    }

    private void printQueryResults(MessageReceivedEvent event){
        String out = event.getAuthor().getAsMention()+" ("+authorId+")\n"
                    +"```Markdown\n"
                    +"#SEARCH RESULTS\n";
        int i=1;
        for(String s : searchResults){
            s = s.substring(s.indexOf("|")+1, s.length());
            out += "<"+(i++)+"> "+s+"\n";
        }
        out += "```";
        MsgSender.sendMsg(event, out);
    }

    @Override
    public void run() {
        String key = authorId;
        boolean run = true;
        long startTime = System.currentTimeMillis();
        while(run && decision == -1){
            try {
                Thread.sleep(250);
            } catch (InterruptedException e){
                run = false;
                System.out.println(TIME.getINFO()+"thread interrupted.");
            }
            if(decision != -1){
                run = false;
            }
            long currentTime = System.currentTimeMillis();
            if(currentTime > (startTime + DECISION_TIME*1000)) {
                run = false;
            }
        }

        if(decision != -1){
            String videoId = searchResults.get(decision-1);
            videoId = videoId.substring(0,videoId.indexOf("|"));
            String[] args = {playNow?"fplay":"play", "http://youtu.be/"+videoId};
            new Play().action(args, event);
        }

        if(queryResultId != null) {
            try {
                textChannel.deleteMessageById(queryResultId).queue();
            } catch (NullPointerException e){
                System.out.println("null");
            }
        }

        CmdMusic.activeYTsearches.remove(key);
        try {
            Thread t = searches.get(key);
            searches.remove(key);
            t.join();
        } catch (InterruptedException e) {
            System.out.println(TIME.getINFO()+"interrupted.");
        }
    }
}
