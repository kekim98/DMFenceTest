package com.example.bawoori.dmfencetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import locationCheckModule.LocationCheckService;
import locationCheckModule._Location;

public class AddDeviceActivity extends AppCompatActivity {

    private static final String TAG = AddDeviceActivity.class.getSimpleName();
    private static final String ADD_SUCCESS_MSG = "정상 등록 되었습니다.";
    private static final String ADD_FAIL_MSG = "이미 등록된 Geofences가 있습니다.";

    LocationCheckService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        setTitle("등록 결과 화면");

        // Bind to LocationCheckService
        Intent intent = new Intent(this, LocationCheckService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
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

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocationCheckService, cast the IBinder and get LocationCheckService instance
            if(!mBound) {
                LocationCheckService.LocalBinder binder = (LocationCheckService.LocalBinder) service;
                mService = binder.getService();
                mBound = true;

                Bundle extras = getIntent().getExtras();
                int result = -1;
                _Location lo =null;
                String device_id="";
                if(extras != null){
                    device_id = extras.getString("DEVICE_ID");
                    lo = mService.updateDevice(device_id);
                    result = 0; // always return true
                }else {
                    result = mService.addDevice(LocationCheckService.DEFAULT_ID);
                }
                if(result == 0){
                    TextView textView1 = (TextView) findViewById(R.id.addResultMsg) ;
                    textView1.setText(ADD_SUCCESS_MSG) ;

                    TextView id = (TextView) findViewById(R.id.VID);
                    id.setText(device_id);

                   /* TextView CELLID = (TextView) findViewById(R.id.VCELLID);
                    CELLID.setText(lo.getCID());

                    TextView ADDR1 = (TextView) findViewById(R.id.VADDR1);
                    ADDR1.setText(lo.getADDR1());

                    TextView ADDR2 = (TextView) findViewById(R.id.VADDR2);
                    ADDR2.setText(lo.getADDR2());*/

                    TextView LAT = (TextView) findViewById(R.id.VLAT);
                    LAT.setText(lo.getLAT());

                    TextView LNG = (TextView) findViewById(R.id.VLNG);
                    LNG.setText(lo.getLNG());


                }else{
                    TextView textView1 = (TextView) findViewById(R.id.addResultMsg) ;
                    textView1.setText(ADD_FAIL_MSG) ;
                }
                Log.d(TAG, "ServiceConnected.................................");
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            Log.d(TAG, "onServiceDisconnected().................................");
        }

    };
}
