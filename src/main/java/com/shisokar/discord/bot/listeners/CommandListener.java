package com.shisokar.discord.bot.listeners;

import com.shisokar.discord.bot.commands.music.CmdMusic;
import com.shisokar.discord.bot.core.CommandHandler;
import com.shisokar.discord.bot.util.TIME;
import com.shisokar.discord.bot.util.YoutubeSearch;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import com.shisokar.discord.bot.util.STATIC;

public class CommandListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event){
        if(whitelist_check(event))
        {
            if(!CmdMusic.activeYTsearches.isEmpty()){
                if(youtubeSearchDecision(event)) {
                    return;
                }
            }
            if(event.getMessage().getContent().startsWith(STATIC.PREFIX)
               && !event.getMessage().getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {

                CommandHandler.handleCommand(CommandHandler.parse.parser(event.getMessage().getContent(), event));
            }
        }
    }

    private boolean whitelist_check(MessageReceivedEvent event){
        //boolean check = false;
        String guild = event.getGuild().getId();
        for(String s : STATIC.get_WHITELIST_GUILDS()){
            if(guild.equals(s)){
                return true;
            }
        }
        return false;
    }

    private boolean youtubeSearchDecision(MessageReceivedEvent event){
        if(event.getMessage().getContent().startsWith(STATIC.PREFIX)){
            return false;
        }
        if(event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())){
            String key = event.getMessage().getContent();
            if(! (key.contains("(") && key.contains(")"))){
                return false;
            }
            key = key.substring(key.indexOf("(")+1,key.indexOf(")"));
            if(CmdMusic.activeYTsearches.containsKey(key)
                && event.getMessage().getContent().contains("```Markdown")){
                CmdMusic.activeYTsearches.get(key).queryResultId = event.getMessageId();
                return true;
            }
        }
        if(CmdMusic.activeYTsearches.containsKey(event.getAuthor().getId())){
            YoutubeSearch ytSearch = CmdMusic.activeYTsearches.get(event.getAuthor().getId());
            if(event.getTextChannel().getId().equals(ytSearch.channelId)){
                try {
                    int choice = Integer.parseInt(event.getMessage().getContent());
                    if ((choice < 0) || (choice > ytSearch.MAX_RESULTS)) {
                        return false; //was no? youtube-search message
                    }
                    ytSearch.decision = choice;
                    ytSearch.event = event;
                    return true; //was youtube-search message
                } catch (Exception e) {
                    System.out.println(TIME.getINFO() + " no integer");
                    return false; //was no? youtube-search message
                }
            }
        }
        return false; //no youtube-search message
    }

}
