package com.mxnavi.gf.server.provider.network.rx;

import com.mxnavi.gf.misc.util.MxLog;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * 描述 ：RxJava调度管理
 *
 * @author Mark
 * @date 2018.12.03
 */

public class FlowableRetryDelay implements
        Function<Flowable<? extends Throwable>,Publisher<?>>{

    private static final String TAG = "FlowableRetryDelay";

    private final int maxRetries;
    private final int retryDelayMillis = 5 * 1000;
    private int retryCount;

    public FlowableRetryDelay(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public Publisher<?> apply(Flowable<? extends Throwable> flowable) throws Exception {
        return flowable.flatMap(new Function<Throwable, Publisher<?>>() {
            @Override
            public Publisher<?> apply(Throwable throwable) throws Exception {
                MxLog.d(TAG, "Retry time : " + retryCount);
                if (++retryCount <= maxRetries) {
                    // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                    return Flowable.timer(retryDelayMillis,
                            TimeUnit.MILLISECONDS);
                }
                // Max retries hit. Just pass the error along.
                return Flowable.error(throwable);
            }
        });
    }
}
