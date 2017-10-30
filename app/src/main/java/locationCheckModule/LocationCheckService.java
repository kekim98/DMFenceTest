package locationCheckModule;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by kekim98 on 2017-05-26.
 */

public class LocationCheckService extends Service {
    private static final String TAG = LocationCheckService.class.getSimpleName();
    public static final String DEFAULT_ID = "DEMO";
    private static final int SUCCESS = 0;
    private static final int FAIL = -1;
    //FIXME : 제품 버전에서는 반드시 DEBUG = false 해야 함
    public static final boolean DEBUG = false;
    private static final String DISTANCE_UNIT = "meter";
    private static final double VALIDE_DISTANCE = 40.0f; //40meter
    private static String sDistance = "";

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    // Random number generator
    //private final Random mGenerator = new Random();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public LocationCheckService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocationCheckService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private _Location getCurrentLocation(){
        _Location lo = new _Location();
        /*String tempAddr = _LocationManager.findWifiMacAddr(this);

        Pattern pattern = Pattern.compile("([^-]*)-([^-]*)-([^-]*)-([^-]*)");
        Matcher matcher = pattern.matcher(tempAddr);
        String addr1="", addr2="", addr3="", addr4 ="";
        while(matcher.find()) {
            addr1 = matcher.group(1);
            addr2 = matcher.group(2);
            addr3 = matcher.group(3);
            addr4 = matcher.group(4);
        }
        lo.setADDR1(addr1);
        lo.setADDR2(addr2);
        lo.setADDR3(addr3);
        lo.setADDR4(addr4);

        String cid = _LocationManager.findCellID(this);
        lo.setCID(cid);

        String lat = String.valueOf(_LocationManager.getLat());
        lo.setLAT(lat);

        String lng = String.valueOf(_LocationManager.getLng());
        lo.setLNG(lng);*/
        return lo;
    }

    /** method for clients */
    public String[] getAllDevice() {
        List<String> devices = LogManager_bak.getAllDevice(this);

        return devices.toArray(new String[devices.size()]);
    }

    public int addDevice(String id){
        _Location lo = getCurrentLocation();
        /*if (DEBUG) {
            Toast.makeText(this,
                    "CID: " + lo.getCID() + "\n" + "ADDR1: " + lo.getADDR1() + "\n" + "ADDR2: " + lo.getADDR2()
                    , Toast.LENGTH_LONG).show();
        }*/

        return LogManager_bak.createLocation(this, id, lo);
    }
    public CheckInfo checkServiceDone(String id){
        CheckInfo checkInfo = new CheckInfo();
       /* checkInfo.setResult(FAIL);

        Log.d(TAG, "checkServiceDone.id:" + id);
        if(id.isEmpty()) {
            //return FAIL;
            return checkInfo;
        }

        _Location clo = getCurrentLocation();
        _Location olo = LogManager_bak.getLocation(this, id); //olo = registered device id

        String regInfo = "R_CID: " + olo.getCID() + "\n"
                + "R_ADDR1: " + olo.getADDR1() + "\n"
                + "R_ADDR2: " + olo.getADDR2() + "\n"
                + "R_ADDR3: " + olo.getADDR3() + "\n"
                + "R_ADDR4: " + olo.getADDR4() + "\n"
                + "R_위도: " +  olo.getLAT() + "\n"
                + "R_경도: " +  olo.getLNG() ;
        String currInfo = "C_CID: " + clo.getCID() + "\n"
                + "C_ADDR1: " + clo.getADDR1() + "\n"
                + "C_ADDR2: " + clo.getADDR2() + "\n"
                + "C_ADDR3: " + clo.getADDR3() + "\n"
                + "C_ADDR4: " + clo.getADDR4() + "\n"
                + "C_위도: " +  clo.getLAT() + "\n"
                + "C_경도: " +  clo.getLNG() ;

        checkInfo.setRegInfo(regInfo);
        checkInfo.setCurrInfo(currInfo);

        if (DEBUG) {
            Toast.makeText(this,
                    regInfo
                    , Toast.LENGTH_LONG).show();
        }

        //if(isValideGPS(clo, olo) || isValideCELL(clo,olo) || isValideWIFI(clo,olo)){
        if(isValideGPS(clo, olo) &&  isValideWIFI(clo,olo)){
            checkInfo.setResult(SUCCESS);
            checkInfo.setDistance(sDistance);

            return checkInfo;
        }

//        if(clo.getCID().equals(olo.getCID()) ){
//            //return SUCCESS;
//            checkInfo.setResult(SUCCESS);
//            return checkInfo;
//        }else{
//            if(!clo.getADDR1().isEmpty() && (clo.getADDR1().equals(olo.getADDR1())  || clo.getADDR1().equals(olo.getADDR2()))) {checkInfo.setResult(SUCCESS); return checkInfo;}
//            if(!clo.getADDR2().isEmpty() && (clo.getADDR2().equals(olo.getADDR1())  || clo.getADDR2().equals(olo.getADDR2()))) {checkInfo.setResult(SUCCESS); return checkInfo;}
//
//        }
        //return FAIL;
        checkInfo.setDistance(sDistance);*/
        return checkInfo;
    }
    private boolean isValideCELL(_Location lo1, _Location lo2){
     //   if(lo1.getCID().equals(lo2.getCID())) return true;
        return false;
    }

    private boolean isValideWIFI(_Location lo1, _Location lo2){
       /* if(lo1.getADDR1().isEmpty() && lo2.getADDR1().isEmpty()) return true; //wifi가 없으면 true로 처리

        if(!lo1.getADDR1().isEmpty()
                && (lo1.getADDR1().equals(lo2.getADDR1())
                || lo1.getADDR1().equals(lo2.getADDR2())
                || lo1.getADDR1().equals(lo2.getADDR3())
                || lo1.getADDR1().equals(lo2.getADDR4()))) return true;
        if(!lo1.getADDR2().isEmpty()
                && (lo1.getADDR2().equals(lo2.getADDR1())
                || lo1.getADDR2().equals(lo2.getADDR2())
                || lo1.getADDR2().equals(lo2.getADDR3())
                || lo1.getADDR2().equals(lo2.getADDR4()))) return true;

        if(!lo1.getADDR3().isEmpty()
                && (lo1.getADDR3().equals(lo2.getADDR1())
                || lo1.getADDR3().equals(lo2.getADDR2())
                || lo1.getADDR3().equals(lo2.getADDR3())
                || lo1.getADDR3().equals(lo2.getADDR4()))) return true;

        if(!lo1.getADDR4().isEmpty()
                && (lo1.getADDR4().equals(lo2.getADDR1())
                || lo1.getADDR4().equals(lo2.getADDR2())
                || lo1.getADDR4().equals(lo2.getADDR3())
                || lo1.getADDR4().equals(lo2.getADDR4()))) return true;*/

        return false;
    }

    private boolean isValideGPS(_Location lo1, _Location lo2){
        double lat1, lat2, lon1, lon2;
        lat1 = parseDouble(lo1.getLAT());
        lat2 = parseDouble(lo2.getLAT());
        lon1 = parseDouble(lo1.getLNG());
        lon2 = parseDouble(lo2.getLNG());

        int distance = (int) _LocationManager.distance(lat1,lon1,lat2,lon2,DISTANCE_UNIT);

        sDistance = "Distance:" + String.valueOf(distance) + "meter";
        Log.d(TAG,  sDistance);
        if(lat1==0 || lat2==0) return false; //유효하지 않은 정보는 false로 처리

        if (distance <= VALIDE_DISTANCE) return true;

        return false;
    }

    private double parseDouble(String lat) {
        if (lat == null || lat.isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(lat);
    }

    public _Location updateDevice(String id){
        _Location lo = LogManager_bak.updateLocation(this, id, getCurrentLocation());
        return lo;
    }

    public int isValidID(String id){
        return LogManager_bak.isUserableID(this, id);
    }

    public int initService() { _LocationManager.initLocationManager(this); return 0;}

}
