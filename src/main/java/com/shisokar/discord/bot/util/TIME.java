package com.shisokar.discord.bot.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TIME {
    public static String get() {
        return new SimpleDateFormat("[HH:mm:ss]").format(new Date());
    }
    public static String getINFO() {
        return get()+" [Info] ";
    }

}
