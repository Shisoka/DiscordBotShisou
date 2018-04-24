package com.shisokar.discord.bot.commands.music;

import com.shisokar.discord.bot.util.MsgSender;
import com.shisokar.discord.bot.util.TIME;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class LeaveVoice extends CmdMusic {

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        //super.action(args, e);
        setGuild(e.getGuild());

        disconnect(e);
    }

    private void disconnect(MessageReceivedEvent e){
        if(hasPlayer(guild)) {
            getManager(guild).purgeQueue();
            skip(guild);
        }
        guild.getAudioManager().closeAudioConnection();
        System.out.println(TIME.getINFO()+"left voice channel: ("+guild.getName()+") "+ voiceChannel);
        MsgSender.sendEmbedMsg(e, null, ":dizzy_face: left voice.");
        e.getMessage().delete().queue();
    }

    public void idleAction(Guild g){
        setGuild(g);

        if(hasPlayer(guild)) {
            getManager(guild).purgeQueue();
            skip(guild);
        }
        guild.getAudioManager().closeAudioConnection();
        System.out.println(TIME.getINFO()+"left voice channel: ("+guild.getName()+") "+ voiceChannel);
    }

}
