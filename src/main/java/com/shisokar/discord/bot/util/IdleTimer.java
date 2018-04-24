package com.shisokar.discord.bot.util;

import com.shisokar.discord.bot.commands.music.LeaveVoice;
import net.dv8tion.jda.core.entities.Guild;

public class IdleTimer implements Runnable{
    private boolean idle = false;
    private long startTime = 0;
    private long IDLELIMIT = STATIC.getIdleTimer();
    private long pauseMultiplier = 1;
    private Guild guild;
    private Thread thread = null;

    public IdleTimer(Guild g) {
        guild = g;
    }

    public void setIdle(){
        if(!idle) {
            System.out.println(TIME.getINFO() + "Idle timer started.");
            startTime = System.currentTimeMillis();
            idle = true;
            Thread t = new Thread(this);
            thread = t;
            t.start();
        }
    }

    public void stop(){
        if(idle){
            System.out.println(TIME.getINFO()+"Idle timer stopped.");
            idle = false;
            if(thread != null) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    System.out.println(TIME.getINFO() + "interrupted.");
                }
            }
            thread = null;
        }
    }

    public void songPaused(){
        pauseMultiplier = STATIC.getIdlePauseMultiplier();
        setIdle();
    }

    public void songResumed(){
        pauseMultiplier = 1;
        stop();
    }

    @Override
    public void run() {
        while(idle) {
            long currentTime = System.currentTimeMillis();
            //System.out.println("if(currentTime > (startTime + IDLELIMIT*1000))");
            //System.out.println(currentTime +"> ("+startTime+" + "+IDLELIMIT*1000);
            if(currentTime > (startTime + IDLELIMIT*pauseMultiplier*1000)) {
                disconnect();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e){
                disconnect();
                System.out.println(TIME.getINFO()+"thread interrupted.");
            }
        }

    }

    private void disconnect(){
        idle = false;
        thread = null;
        System.out.println(TIME.getINFO()+"Idle time is over!");
        LeaveVoice leaver = new LeaveVoice();
        leaver.idleAction(guild);
    }
}
