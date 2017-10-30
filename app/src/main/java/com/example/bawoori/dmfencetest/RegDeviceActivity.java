package com.example.bawoori.dmfencetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import locationCheckModule.LocationCheckService;

public class RegDeviceActivity extends AppCompatActivity {
    private static final String TAG = RegDeviceActivity.class.getSimpleName();
    LocationCheckService mService;
    boolean mBound = false;
    private static final String ADD_SUCCESS_MSG = "정상 등록 되었습니다.";
    private static final String ADD_FAIL_MSG = "이미 등록된 Geofences가 있습니다.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bind to LocationCheckService
        Intent intent = new Intent(this, LocationCheckService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        setContentView(R.layout.activity_reg_device);
        setTitle("등록 화면");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume().mBound:" + mBound);
    }

    @Override
    protected void onPause() {
        super.onPause();
        InputMethodManager immhide = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }

    }

    /**
     * Called when a button is clicked (the button in the layout file attaches to
     * this method with the android:onClick attribute)
     */
    public void onRegDevice(View v) {
        EditText userInput = (EditText) findViewById(R.id.userDevice);
        String deviceId = userInput.getText().toString();
        if (deviceId.isEmpty()) {
            Toast.makeText(this,
                    "입력값이 없습니다. 다시 입력해 주세요"
                    , Toast.LENGTH_LONG).show();
            return;
        }
        if (mBound) {
            int ret = mService.isValidID(deviceId);
            if (ret == -1) {
                Toast.makeText(this,
                        "이미 사용된 ID입니다. 다시 입력해 주세요"
                        , Toast.LENGTH_LONG).show();
                return;
            }
        }
        Intent intent = new Intent(this, AddDeviceActivity.class);
        intent.putExtra("DEVICE_ID", deviceId);
        startActivity(intent);
        finish();
    }

    public void onCancelRegDevice(View v) {
        finish();
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocationCheckService, cast the IBinder and get LocationCheckService instance
            if (!mBound) {
                LocationCheckService.LocalBinder binder = (LocationCheckService.LocalBinder) service;
                mService = binder.getService();
                mBound = true;
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            Log.d(TAG, "onServiceDisconnected().................................");
        }

    };
}

