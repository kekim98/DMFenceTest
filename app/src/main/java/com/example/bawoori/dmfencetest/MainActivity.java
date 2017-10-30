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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import locationCheckModule.LocationCheckService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    LocationCheckService mService;
    boolean mBound = false;

    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bind to LocationCheckService
        Intent intent = new Intent(this, LocationCheckService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        setContentView(R.layout.activity_main);
        setTitle("Geofences 리스트");

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1) ;

        ListView listview = (ListView) findViewById(R.id.deviceList) ;
        listview.setAdapter(adapter) ;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position) ;
                Log.d(TAG, "selected item:" + strText);

                Intent intent=new Intent(MainActivity.this, CheckActivity.class);
                intent.putExtra("DEVICE_ID", strText);
                startActivity(intent);
            }
        }) ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocationCheckService
//        Intent intent = new Intent(this, LocationCheckService.class);
//        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
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
    public void onAddDevice(View v) {
        Intent intent=new Intent(this, RegDeviceActivity.class);
        startActivity(intent);
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

               mService.initService();
               refresh();
               Log.d(TAG, "ServiceConnected-mBound:" + mBound);
           }

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            Log.d(TAG, "onServiceDisconnected().................................");
        }
    };

    private void refresh(){
        if(mBound){
            String[] devices = mService.getAllDevice();
            adapter.clear();
            for(int i=0; i<devices.length; i++){
                adapter.add(devices[i]);
            }
            adapter.notifyDataSetChanged();
            Log.d(TAG, "refresh().................................");
        }
    }

}
