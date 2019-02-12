package com.mxnavi.gf.mxmusic;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mxnavi.gf.misc.singleton.SingletonManager;
import com.mxnavi.gf.mxmusic.util.AppUtil;
import com.mxnavi.gf.mxmusic.widget.state.EmptyView;
import com.mxnavi.gf.mxmusic.widget.state.ErrorView;
import com.mxnavi.gf.mxmusic.widget.state.LoadingView;
import com.mxnavi.gf.superui.state.StateManager;
import com.mxnavi.gf.superui.study.HorizontalScrollViewEx;

import java.util.ArrayList;

/**
 * describle : MainActivity
 *
 * @author Mark
 * @date 2018.12.03
 */

public class MainActivity extends Activity {

    private StateManager mStateManager;

    private HorizontalScrollViewEx mListContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_study);
//        init();
        initView();
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

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = (HorizontalScrollViewEx) findViewById(R.id.ll_container);
        final int screenWidth = AppUtil.getScreenMetrics(this).widthPixels;
        final int screenHeight = AppUtil.getScreenMetrics(this).heightPixels;
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(
                    R.layout.widget_content_layout, mListContainer, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            mListContainer.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.widget_content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "click item",
                        Toast.LENGTH_SHORT).show();
                SingletonManager.getService(Object.class);

            }
        });
    }
}
