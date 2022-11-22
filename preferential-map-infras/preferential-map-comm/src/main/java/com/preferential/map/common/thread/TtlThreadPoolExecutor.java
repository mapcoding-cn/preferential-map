package com.preferential.map.common.thread;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ttl线程池修饰类
 */
public class TtlThreadPoolExecutor extends ThreadPoolExecutor {

    public TtlThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public TtlThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public TtlThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public TtlThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
            RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(Runnable command) {
        Runnable runnable = TtlRunnable.get(command);
        Runnable modifyRunnable = modifyRunnable(runnable);
        super.execute(modifyRunnable);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        Runnable runnable = TtlRunnable.get(task);
        Runnable modifyRunnable = modifyRunnable(runnable);
        return super.submit(modifyRunnable, result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        Runnable runnable = TtlRunnable.get(task);
        Runnable modifyRunnable = modifyRunnable(runnable);
        return super.submit(modifyRunnable);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        Callable command = TtlCallable.get(task);
        Callable callable = modifyCallable(command);
        return super.submit(callable);
    }

    /**
     * 防止动态数据源失效
     *
     * @param c
     * @param <T>
     * @return
     */
    private <T> Callable<T> modifyCallable(Callable<T> c) {
        return c;

    }

    /**
     * 防止动态数据源失效
     *
     * @param r
     * @return
     */
    private <T> Runnable modifyRunnable(Runnable r) {
        return r;

    }
}
