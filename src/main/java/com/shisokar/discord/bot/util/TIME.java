package com.shisokar.discord.bot.util;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TIME {

    @NotNull
    public static String get() {
        return new SimpleDateFormat("[HH:mm:ss]").format(new Date());
    }
    @NotNull
    public static String getINFO()   { return get()+" [Info] "; }
    @NotNull
    public static String getCONFIG() { return get()+" [Config] "; }

}
