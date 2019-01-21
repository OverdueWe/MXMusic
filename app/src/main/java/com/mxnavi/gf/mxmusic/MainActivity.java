package com.mxnavi.gf.mxmusic;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.mxnavi.gf.mxmusic.widget.state.EmptyView;
import com.mxnavi.gf.mxmusic.widget.state.ErrorView;
import com.mxnavi.gf.mxmusic.widget.state.LoadingView;
import com.mxnavi.gf.superui.state.StateManager;

/**
 * describle : MainActivity
 *
 * @author Mark
 * @date 2018.12.03
 */

public class MainActivity extends Activity {

    private StateManager mStateManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mStateManager = new StateManager.Builder()
                .setContext(MainActivity.this)
                .setContentView(findViewById(R.id.tv_mian_success))
                .setStateOnClickListener(stateOnClickListener)
                .setEmptyView(EmptyView.class)
                .setErrorView(ErrorView.class)
                .setLoadView(LoadingView.class)
                .build();

        findViewById(R.id.btn_main_empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStateManager.loadState(StateManager.State.EMPTY);
            }
        });

        findViewById(R.id.btn_main_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStateManager.loadState(StateManager.State.ERROR);
            }
        });

        findViewById(R.id.btn_main_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStateManager.loadState(StateManager.State.LOADING);
            }
        });

        findViewById(R.id.btn_main_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStateManager.loadState(StateManager.State.SUCCESS);
            }
        });
    }

    private StateManager.StateOnClickListener stateOnClickListener = new StateManager.StateOnClickListener() {
        @Override
        public void onStateClick(StateManager.State state) {
            Toast.makeText(MainActivity.this, "State : " + state.toString(), Toast.LENGTH_SHORT).show();
        }
    };
}
