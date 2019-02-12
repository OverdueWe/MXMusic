package com.mxnavi.gf.misc.thread;

import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 描述 ： 线程池
 *
 * @author Mark
 * @date 2018.09.14
 */

public class ThreadPoolManager {

    private static final int THREAD_SIZE = 10;

    private static ThreadPoolManager mThreadPoolManager = null;

    private static ThreadPoolExecutor threadService = null;

    private static ScheduledExecutorService timerService = null;

    public ThreadPoolManager(){
    }

    public static ThreadPoolManager getInstance(){
        if(mThreadPoolManager == null){
            mThreadPoolManager = new ThreadPoolManager();
        }
        return mThreadPoolManager;
    }

    private ThreadPoolExecutor getThreadService(){
        if(!isThreadServiceEnable()){
            threadService = new ThreadPoolExecutor(10, 10,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
        }
        return threadService;
    }

    public void addThreadTask(Runnable runnable){
        getThreadService().submit(runnable);
    }

    public void shutDownThreadService(){
        if(isThreadServiceEnable()){
            getThreadService().shutdown();
        }
    }

    public void shutDownNowThreadService(){
        if(isThreadServiceEnable()){
            getThreadService().shutdownNow();
        }
    }

    private boolean isThreadServiceEnable(){
        return !(threadService == null||threadService.isShutdown()||threadService.isTerminated());
    }

    private ScheduledExecutorService getTimerService(){
        if(!isTimerServiceEnable()){
            timerService = new ScheduledThreadPoolExecutor(THREAD_SIZE);
        }
        return timerService;
    }

    public ScheduledFuture<?> addTimerTask(TimerTask timerTask,
                                                  long initialDelay,
                                                  long period,
                                                  TimeUnit unit){
        return getTimerService().scheduleAtFixedRate(timerTask,initialDelay,period, unit);
    }

    public ScheduledFuture<?> addDelayTask(Runnable timerTask, long delay, TimeUnit unit){
        return getTimerService().schedule(timerTask, delay, unit);
    }

    public void shutDownTimerService(){
        if(isTimerServiceEnable()){
            getTimerService().shutdown();
        }
    }

    public void shutDownNowTimerService(){
        if(isTimerServiceEnable()){
            getTimerService().shutdownNow();
        }
    }

    private boolean isTimerServiceEnable(){
        return !(timerService == null||timerService.isShutdown()||timerService.isTerminated());
    }

}
