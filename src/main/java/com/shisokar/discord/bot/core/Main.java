package com.shisokar.discord.bot.core;

import com.shisokar.discord.bot.commands.*;
import com.shisokar.discord.bot.commands.music.*;
import com.shisokar.discord.bot.listeners.CommandListener;
import com.shisokar.discord.bot.listeners.ReadyListener;
import com.shisokar.discord.bot.util.STATIC;
import com.shisokar.discord.bot.util.TIME;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import com.shisokar.discord.bot.util.SECRETS;

import javax.security.auth.login.LoginException;
import java.util.List;


public class Main {

    private static JDABuilder builder;

    public static void main(String... args){

        builder = new JDABuilder(AccountType.BOT);
        builder.setToken(SECRETS.getToken());
        builder.setAutoReconnect(true);

        builder.setGame(Game.of(" say .help"));

        addListeners();
        addCommands();

        try {
            JDA jda = builder.buildBlocking();
        } catch (LoginException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }
        configs();
    }

    private static void addCommands(){
        CommandHandler.commands.put("ping",     new CmdPing());
        CommandHandler.commands.put("say",      new CmdSay());
        CommandHandler.commands.put("clear",    new CmdClear());
        CommandHandler.commands.put("c",        new CmdClear());
        CommandHandler.commands.put("help",     new CmdHelp());
        CommandHandler.commands.put("version",  new CmdVersion());

        CommandHandler.commands.put("m",        new CmdMusic());
        CommandHandler.commands.put("music",    new CmdMusic());

        //music commands
        CommandHandler.invCommands.put("play",     new Play());
        CommandHandler.invCommands.put("p",        new Play());
        CommandHandler.invCommands.put("fplay",    new Play());
        CommandHandler.invCommands.put("fp",       new Play());
        CommandHandler.invCommands.put("pause",    new Pause());
        CommandHandler.invCommands.put("resume",   new Resume());
        CommandHandler.invCommands.put("stop",     new Stop());
        CommandHandler.invCommands.put("now",      new NowPlaying());
        CommandHandler.invCommands.put("np",       new NowPlaying());
        CommandHandler.invCommands.put("skip",     new Skip());
        CommandHandler.invCommands.put("s",        new Skip());
        CommandHandler.invCommands.put("forward",  new SeekRewindForward());
        CommandHandler.invCommands.put("fw",       new SeekRewindForward());
        CommandHandler.invCommands.put("rewind",   new SeekRewindForward());
        CommandHandler.invCommands.put("seek",     new SeekRewindForward());
        CommandHandler.invCommands.put("shuffle",  new Shuffle());
        CommandHandler.invCommands.put("queue",    new Queue());
        CommandHandler.invCommands.put("q",        new Queue());
        CommandHandler.invCommands.put("qlist",    new Queue());
        CommandHandler.invCommands.put("leave",    new LeaveVoice());
        CommandHandler.invCommands.put("last",     new PreviouslyPlaying());
    }

    private static void addListeners(){
        builder.addEventListener(new ReadyListener());
        builder.addEventListener(new CommandListener());
    }

    private static void configs(){
        List<String> guilds = STATIC.get_WHITELIST_GUILDS();
        guilds.forEach(g -> System.out.println(TIME.getINFO()+"whitelisted guild: "+g));
        System.out.println(TIME.getINFO()+"IdleTimer = "+STATIC.getIdleTimer()+" seconds");
        System.out.println(TIME.getINFO()+"IdlePauseMultiplier = "+STATIC.getIdlePauseMultiplier()+"x");
        System.out.println(TIME.getINFO()+"Audio Volume = "+STATIC.get_AUDIO_VOLUME()+"%");
    }

}
