package com.zxm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.statelayout.R;
import com.zxm.widget.StateLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public StateLayout mStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStateLayout = ((StateLayout) findViewById(R.id.state_layout));
        findViewById(R.id.bt_loading).setOnClickListener(this);
        findViewById(R.id.bt_error).setOnClickListener(this);
        findViewById(R.id.bt_success).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_loading:
                mStateLayout.setState(StateLayout.STATE_LOADING);
                break;
            case R.id.bt_success:
                mStateLayout.setState(StateLayout.STATE_SUCCESS);
                break;
            case R.id.bt_error:
                mStateLayout.setState(StateLayout.STATE_ERROR);
                break;
        }
    }
}
