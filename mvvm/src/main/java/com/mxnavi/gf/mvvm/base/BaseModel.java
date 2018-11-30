package com.mxnavi.gf.mvvm.base;

import com.mxnavi.gf.mvvm.event.EventBus;
import com.mxnavi.gf.mvvm.rx.RxManager;

/**
 * describle : Base of Model
 *
 * @author Mark
 * @date 2018.11.30
 */

public class BaseModel extends RxManager {

    public BaseModel() {

    }

    protected void postEvent(Object eventKey, String tag, Object t) {
        EventBus.getInstance().postEvent(eventKey, tag, t);
    }

}
