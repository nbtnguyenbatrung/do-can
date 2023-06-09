package com.dogoo.SystemWeighingSas.unitity.tasks;

import lombok.NonNull;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Giúp tạo ThreadPool nhanh trong Java
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class ThreadPool {

    private static final AtomicInteger poolNumber = new AtomicInteger(1);

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        boolean daemon = true;
        String namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        int coreSize;
        int maxSize;
        int queueSize = 0;
        long keepAliveTimeout = 60L;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        ThreadFactory threadFactory;

        public Builder setDaemon(boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        public Builder setNamePrefix(String namePrefix) {
            this.namePrefix = namePrefix;
            return this;
        }

        public Builder setCoreSize(int coreSize) {
            this.coreSize = coreSize;
            return this;
        }

        public Builder setMaxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public Builder setQueueSize(int queueSize) {
            this.queueSize = queueSize;
            return this;
        }

        public Builder setKeepAliveTimeout(long keepAliveTimeout) {
            this.keepAliveTimeout = keepAliveTimeout;
            return this;
        }

        public Builder setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder setThreadFactory(ThreadFactory threadFactory) {
            this.threadFactory = threadFactory;
            return this;
        }

        public ExecutorService build() {
            if (maxSize < coreSize) maxSize = coreSize;
            BlockingQueue<Runnable> workQueue = queueSize <= 0
                    ? new LinkedBlockingQueue<>()
                    : new ArrayBlockingQueue<>(queueSize);
            if (threadFactory == null) {
                threadFactory = new DataCollectionThreadFactory(namePrefix, daemon);
            }
            return new ThreadPoolExecutor(coreSize,
                    maxSize,
                    keepAliveTimeout,
                    timeUnit,
                    workQueue,
                    threadFactory);
        }
    }

    static class DataCollectionThreadFactory implements ThreadFactory {

        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final boolean daemon;

        DataCollectionThreadFactory(String prefix, boolean daemon) {
            this.namePrefix = prefix + "-";
            this.daemon = daemon;
        }

        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
            t.setDaemon(daemon);
            t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
