package com.mxnavi.gf.server.provider.network.rx;

import com.mxnavi.gf.misc.util.MxLog;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Function;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * 描述 ：请求重试管理
 *
 * @author Mark
 * @date 2018.08.13
 */
public class ObservableRetryDelay implements
        Function<Observable<? extends Throwable>, Observable<?>> {

    private static final String TAG = "ObservableRetryDelay";

    private final int maxRetries;
    private final int retryDelayMillis = 5 * 1000;
    private int retryCount;

    public ObservableRetryDelay(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) {
        return observable
                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        MxLog.d(TAG, "Retry time : " + retryCount);
                        if (++retryCount <= maxRetries) {
                            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                            return Observable.timer(retryDelayMillis,
                                    TimeUnit.MILLISECONDS);
                        }
                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    }
                });
    }
}

