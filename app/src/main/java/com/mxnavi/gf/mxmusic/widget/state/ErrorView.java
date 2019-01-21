package com.mxnavi.gf.mxmusic.widget.state;

import android.content.Context;
import android.view.View;

import com.mxnavi.gf.mxmusic.R;
import com.mxnavi.gf.superui.state.StateView;

public class ErrorView extends StateView {

    public ErrorView(Context context) {
        super(context);
        getRootView().findViewById(R.id.btn_widget_error_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry();
            }
        });
    }

    @Override
    public int getLayoutID() {
        return R.layout.widget_state_error;
    }
}
