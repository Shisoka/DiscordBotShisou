package com.shisokar.discord.bot.util;


import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class MsgSender {

    private static EmbedBuilder embed;

    public static void sendEmbedMsg(MessageReceivedEvent event, String desc){
        embed = new EmbedBuilder();
        if(desc   != null)    embed.setDescription(desc);
        event.getTextChannel().sendMessage(embed.setColor(Color.PINK).build()).queue();
    }

    public static void sendEmbedMsg(MessageReceivedEvent event, String author, String desc){
        embed = new EmbedBuilder();
        if(author != null)  embed.setAuthor(author, null, null);
        if(desc   != null)  embed.setDescription(desc);
        event.getTextChannel().sendMessage(embed.setColor(Color.PINK).build()).queue();
    }

    public static void sendEmbedMsg(MessageReceivedEvent event, Member author, String desc, String thumbnailId){
        embed = new EmbedBuilder();
        if(desc   != null)      embed.setDescription(desc);
        if(thumbnailId != null) embed.setThumbnail(STATIC.YOUTUBE_THUMBNAIL.replace("<ID>", thumbnailId));
        if(author != null) {
            String name = author.getNickname() != null ? author.getNickname() : author.getUser().getName();
            embed.setAuthor(name, author.getUser().getAvatarUrl(), author.getUser().getAvatarUrl());
        }
        event.getTextChannel().sendMessage(embed.setColor(Color.PINK).build()).queue();
    }

    public static void sendEmbedMsg(MessageReceivedEvent event, String desc, String thumbnailId, String footer, String footerIcon){
        embed = new EmbedBuilder();
        if(desc   != null)         embed.setDescription(desc);
        if(thumbnailId != null)    embed.setThumbnail(STATIC.YOUTUBE_THUMBNAIL.replace("<ID>", thumbnailId));
        if(footer != null
            && footerIcon != null) embed.setFooter(footer, footerIcon);
        event.getTextChannel().sendMessage(embed.setColor(Color.PINK).build()).queue();
    }

    public static void sendEmbedMsg(MessageReceivedEvent event, String author, String authorIcon, String desc, String thumbnailId, String footer, String footerIcon){
        embed = new EmbedBuilder();
        if(desc   != null)      embed.setDescription(desc);
        if(thumbnailId != null) embed.setThumbnail(STATIC.YOUTUBE_THUMBNAIL.replace("<ID>", thumbnailId));
        if(author != null)      embed.setAuthor(author, null, authorIcon);
        if(footer != null && footerIcon != null)
            embed.setFooter(footer, footerIcon);
        event.getTextChannel().sendMessage(embed.setColor(Color.PINK).build()).queue();
    }

    public static void sendEmbedMsg(MessageReceivedEvent event, Member author, String desc, String thumbnailId, String footer, String footerIcon){
        embed = new EmbedBuilder();
        if(desc   != null)      embed.setDescription(desc);
        if(thumbnailId != null) embed.setThumbnail(STATIC.YOUTUBE_THUMBNAIL.replace("<ID>", thumbnailId));
        if(author != null) {
            String name = author.getNickname() != null ? author.getNickname() : author.getUser().getName();
            embed.setAuthor(name, author.getUser().getAvatarUrl(), author.getUser().getAvatarUrl());
        }
        if(footer != null && footerIcon != null)
            embed.setFooter(footer, footerIcon);
        event.getTextChannel().sendMessage(embed.setColor(Color.PINK).build()).queue();
    }

    public static void sendEmbedMsgTwitch(MessageReceivedEvent event, String headline, String desc, String thumbnail, String footer, String footerIcon){
        embed = new EmbedBuilder();
        if(desc   != null)    embed.setDescription(desc);
        if(thumbnail != null) embed.setThumbnail(thumbnail);
        if(headline != null)  embed.setAuthor(headline,null, null);
        if(footer != null && footerIcon != null)
            embed.setFooter(footer, footerIcon);
        event.getTextChannel().sendMessage(embed.setColor(Color.PINK).build()).queue();
    }

    public static void sendErrorMsg(MessageReceivedEvent event, String content) {
        event.getTextChannel().sendMessage(
                new EmbedBuilder().setColor(Color.red).setDescription(content).build()
        ).queue();
    }

    public static void sendMsg(MessageReceivedEvent event, String content){
        event.getTextChannel().sendMessage(content).queue();
    }

}
