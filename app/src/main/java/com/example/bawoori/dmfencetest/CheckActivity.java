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
import android.widget.LinearLayout;
import android.widget.TextView;

import locationCheckModule.CheckInfo;
import locationCheckModule.LocationCheckService;

public class CheckActivity extends AppCompatActivity {
    private static final String TAG = CheckActivity.class.getSimpleName();
    private static final String CHK_SUCCESS_MSG = "GeoFence 정보.";
   // private static final String CHK_FAIL_MSG = "서비스가 정상 완료되지 못하였습니다.";
    LocationCheckService mService;
    boolean mBound = false;
    static String device_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        setTitle("GeoFence Info 화면");

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
    /** Called when a button is clicked (the button in the layout file attaches to
     * this method with the android:onClick attribute) */
    public void onReAddDevice(View v) {
        Log.d(TAG, "device_id:" + device_id);
        Intent intent=new Intent(this, AddDeviceActivity.class);
        intent.putExtra("DEVICE_ID", device_id);
        startActivity(intent);
        finish();
    }

    public void onCancle(View v) {
        finish();
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

                if (extras != null) {
                    device_id = extras.getString("DEVICE_ID");
                    //The key argument here must match that used in the other activity
                }
                CheckInfo checkInfo = mService.checkServiceDone(device_id);
                int result = checkInfo.getResult();

                TextView deviceIfo = (TextView) findViewById(R.id.deviceInfo);
                deviceIfo.setText(checkInfo.getRegInfo() + "\n\n" + checkInfo.getCurrInfo() + "\n\n" +checkInfo.getDistance());

               /* if(result == 0){
                    TextView textView1 = (TextView) findViewById(R.id.checkResultMsg) ;
                    String msg = "[" + device_id + "] " + CHK_SUCCESS_MSG;
                    textView1.setText(msg) ;
                }else{
                    TextView textView1 = (TextView) findViewById(R.id.checkResultMsg) ;
                    String msg = "[" + device_id + "] " + CHK_FAIL_MSG;
                    textView1.setText(msg) ;

                    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.reRegLayout);
                    linearLayout.setVisibility(View.VISIBLE);
                }*/

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
