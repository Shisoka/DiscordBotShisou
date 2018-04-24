package com.shisokar.discord.bot.util;

import com.moandjiezana.toml.Toml;

import java.io.File;

public class SECRETS {

    public static final String BOT_TOKEN = "";

    public static String getToken(){
        File file = new File("config.toml");
        Toml toml = new Toml().read(file);
        String token = toml.getString("Token");
        return token;
    }

}
