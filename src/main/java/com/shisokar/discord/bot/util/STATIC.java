package com.shisokar.discord.bot.util;

import com.moandjiezana.toml.Toml;

import java.io.File;
import java.util.List;

public class STATIC {

    public static final String VERSION = "1.4.7";

    public static final String UPDATE_MSG = "**Version "+VERSION+" by Shisokar** [24/4/2018]" +
                                            "*latest addition: idle timer and twitch support*";

    public static String PREFIX = ".";

    public static List<String> get_WHITELIST_GUILDS(){
        File file = new File("config.toml");
        Toml toml = new Toml().read(file);
        List<String> guilds = toml.getList("whitelist");
        return guilds;
    };

    public static long getIdleTimer(){
        File file = new File("config.toml");
        Toml toml = new Toml().read(file);
        long time = toml.getLong("IdleTimer");
        return time;
    }

    public static long getIdlePauseMultiplier(){
        File file = new File("config.toml");
        Toml toml = new Toml().read(file);
        long multiplier = toml.getLong("IdlePauseMultiplier");
        return multiplier;
    }

    public static String get_YOUTUBE_API_KEY(){
        File file = new File("config.toml");
        Toml toml = new Toml().read(file);
        String APIkey = toml.getString("YoutubeAPI");
        return APIkey;
    }

    public static final String YOUTUBE_THUMBNAIL = "https://img.youtube.com/vi/<ID>/default.jpg";

    public static final String MUSICAL_NOTE =  "https://puu.sh/wDO6I/cf8501f2c2.png";
    public static final String MUSICAL_NOTES = "https://puu.sh/wDOUC/d674969f9a.png";
    public static final String ARROR_FORWARD = "https://puu.sh/wDObQ/22bf9bd1b3.png";


    public static final String ID_BECOME_AS_GODS   = "c9Ku9HIumpM";
    public static final String YT_BECOME_AS_GODS   = "https://www.youtube.com/watch?v=c9Ku9HIumpM";
    public static final String GIF_BECOME_AS_GODS  = "http://puu.sh/xLVx9/275c2bd456.gif";
    public static final String HELP_BECOME_AS_GODS = "B E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\nᅠᅠᅠᅠᅠᅠᅠᅠᅠB E C O M E  A S  G O D S\n";

    public static final String MUSIC_HELP = "**`.play <url>`  or `.p <url>`**            .....add to queue\n" +
                                            "_url can contain timestamp to start at given time_\n" +
                                            "**`.play <search>`  `.p <search>`**    .....search youtube\n" +
                                            "─────────────────────────────────────\n" +
                                            "**`.pause`**                                               .....pauses current track\n" +
                                            "**`.resume`**                                             .....resumes current track\n" +
                                            "**`.stop`**                                                 .....stop the player, erase queue\n" +
                                            "─────────────────────────────────────\n" +
                                            "**`.skip` or `.s`**                                       .....skips current track\n" +
                                            "**`.skip <1 to 100>`**                           .....skips 1 to 100 tracks\n" +
                                            "\n" +
                                            "**`.shuffle`**                                            .....shuffles playlist\n" +
                                            "─────────────────────────────────────\n" +
                                            "**`.forward <h:m:s>`** or `.fw <h:m:s>`\n" +
                                            "**`.rewind <h:m:s>`**\n" +
                                            "**`.seek <h:m:s>`**     .....example: .seek 2:21\n" +
                                            "─────────────────────────────────────\n" +
                                            "**`.now` or `.np`**                                       .....displays currently playing track\n" +
                                            "**`.last`**                                                  .....displays last track\n" +
                                            "─────────────────────────────────────\n" +
                                            "**`.queue` or `.q`**                                     .....displays queue\n" +
                                            "**`.qlist <page>`**                                  .....displays <page> of queue\n";

    /*public static class ServerPerm{
        public final String[][] PERMS = {{"role_name","rolen_name"}};
        public final String[]   SERVER = {"server_id"};


        public static String[] get(String server){
            for (int i = 0; i < SERVER.length; i++) {
                if(SERVER[i].equalsIgnoreCase(server)){
                    return PERMS[i];
                }
            }
            return null;
        }
    }*/
}
