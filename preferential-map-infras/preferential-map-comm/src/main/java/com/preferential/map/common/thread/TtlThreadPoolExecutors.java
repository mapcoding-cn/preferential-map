package com.preferential.map.common.thread;

import com.alibaba.ttl.threadpool.TtlExecutors;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 线程池构造器
 */
public class TtlThreadPoolExecutors {

    /**
     * 固定大小线程池包装
     *
     * @param nThreads
     * @return
     */
    public static ExecutorService newTtlFixedThreadPool(int nThreads) {
        return TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(nThreads));
    }

    /**
     * 单个线程池包装
     *
     * @return
     */
    public static ExecutorService newTtlSingleThreadPool() {
        return TtlExecutors.getTtlExecutorService(Executors.newSingleThreadExecutor());
    }

    /**
     * cache线程池包装
     *
     * @return
     */
    public static ExecutorService newTtlCachedThreadPool() {
        return TtlExecutors.getTtlExecutorService(Executors.newCachedThreadPool());
    }

    /**
     * Scheduled线程池包装
     *
     * @return
     */
    public static ExecutorService newTtlScheduledThreadPool(int nThreads) {
        return TtlExecutors.getTtlExecutorService(Executors.newScheduledThreadPool(nThreads));
    }


    /**
     * 自定义线程池
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @return
     */
    public static ExecutorService newTtlThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue) {
        return new TtlThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 自定义线程池
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     * @return
     */
    public static ExecutorService newTtlThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        return new TtlThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    /**
     * 自定义线程池
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param handler
     * @return
     */
    public static ExecutorService newTtlThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        return new TtlThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    /**
     * 自定义线程池
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     * @param handler
     * @return
     */
    public static ExecutorService newTtlThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
            RejectedExecutionHandler handler) {
        return new TtlThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory,
                handler);
    }

}
