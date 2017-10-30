package locationCheckModule;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kekim98 on 2017-05-26.
 */

 class LogManager_bak {
    private static final String INDEX_KEY = "lastIndex";
    private static final String TAG = LogManager_bak.class.getSimpleName();
    private static final String PREF_LOCATION = "LOCATION_LOGGER";
    private static final String PREF_INDEX = "INDEX_LOGGER";

    public static int createLocation(Context ctx, String id, _Location lo){
       /* //only use once demo mode
        if(id.equals(LocationCheckService.DEFAULT_ID)){
            int tempID = getLastID(ctx);
            id = "정수기" + tempID;
        }

        SharedPreferences prefs = getPrefs(ctx);
        //check whether already same id exist
        if(prefs.getString(id, "").isEmpty()){
            String location = lo.getCID() + _LocationManager.DELIMITER
                    + lo.getADDR1() + _LocationManager.DELIMITER
                    + lo.getADDR2() + _LocationManager.DELIMITER
                    + lo.getADDR3() + _LocationManager.DELIMITER
                    + lo.getADDR4() + _LocationManager.DELIMITER
                    + lo.getLAT() + _LocationManager.DELIMITER
                    + lo.getLNG();

            prefs.edit().putString(id, location).apply();
            return 0;
        }*/
        return -1;
    }

    public static _Location getLocation(Context ctx, String id){
        _Location ret= new _Location();
       /* SharedPreferences prefs = getPrefs(ctx);
        String location = prefs.getString(id,"");

        if(!location.isEmpty()){
            Pattern pattern = Pattern.compile("([^-]*)-([^-]*)-([^-]*)-([^-]*)-([^-]*)-([^-]*)-([^-]*)");
            Matcher matcher = pattern.matcher(location);

            while(matcher.find()) {
                ret.setCID(matcher.group(1));
                ret.setADDR1(matcher.group(2));
                ret.setADDR2(matcher.group(3));
                ret.setADDR3(matcher.group(4));
                ret.setADDR4(matcher.group(5));
                ret.setLAT(matcher.group(6));
                ret.setLNG(matcher.group(7));
            }
        }*/
        return ret;
    }

    public static int isUserableID(Context ctx, String id){
        int ret = -1;
        SharedPreferences prefs = getPrefs(ctx);
        String location = prefs.getString(id,"");
        if(location.isEmpty()) ret = 0;

        return ret;
    }

    public static List<String> getAllDevice(Context ctx){
        List<String> ret= new ArrayList<String>();
        SharedPreferences prefs = getPrefs(ctx);
        Map<String, ?> allEntries = prefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d(TAG, entry.getKey() + ": " + entry.getValue().toString());
            ret.add(entry.getKey());
        }
        return ret;
    }

    public static _Location updateLocation(Context ctx, String id, _Location lo){
       /* String location = lo.getCID() + _LocationManager.DELIMITER
                + lo.getADDR1() + _LocationManager.DELIMITER
                + lo.getADDR2() + _LocationManager.DELIMITER
                + lo.getADDR3() + _LocationManager.DELIMITER
                + lo.getADDR4() + _LocationManager.DELIMITER
                + lo.getLAT() + _LocationManager.DELIMITER
                + lo.getLNG();
        SharedPreferences prefs = getPrefs(ctx);
        prefs.edit().putString(id, location).apply();*/

        return lo;
    }

    public static void deleteLocation(Context ctx, String id){
        SharedPreferences prefs = getPrefs(ctx);
        prefs.edit().remove(id).apply();
    }

    //only use once demo mode
    private static int getLastID(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_INDEX, Context.MODE_PRIVATE);
        int index = prefs.getInt(INDEX_KEY, 0);
        index++;
        prefs.edit().putInt(INDEX_KEY, index).apply();
        return index;
    }

    private static SharedPreferences getPrefs(Context ctx) {
        return ctx.getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE);
    }
}
