package com.shisokar.discord.bot.commands.music;

import com.shisokar.discord.bot.audio.AudioInfo;
import com.shisokar.discord.bot.util.MsgSender;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PreviouslyPlaying extends CmdMusic {

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        //super.action(args, e);
        setGuild(e.getGuild());

        lastSong(e);
    }

    private void lastSong(MessageReceivedEvent e){
        if(getManager(guild).lastSong != null){
            Member author = getManager(guild).lastSong.getAuthor();
            String footer = author.getNickname() != null ? author.getNickname() : author.getUser().getName();
            String footerIcon = author.getUser().getAvatarUrl();
            String title = getManager(guild).lastSong.getTrack().getInfo().title;
            String id    = getManager(guild).lastSong.getTrack().getIdentifier();
            String desc = "**Title:** "+title+"\n"+
                    "**Link:**  __http://youtu.be/"+id+"__";
            AudioInfo info = getManager(guild).lastSong;
            MsgSender.sendEmbedMsg(e, "LAST SONG", null, desc, id, footer, footerIcon);
        } else {
            MsgSender.sendEmbedMsg(e, "no last song.");
        }
        e.getMessage().delete().queue();
    }

}
