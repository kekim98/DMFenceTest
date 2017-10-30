package locationCheckModule;

/**
 * Created by kekim98 on 2017-06-01.
 */

public class CheckInfo {
    int result = -1;
    String regInfo ="";
    String currInfo = "";

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    String distance = "0.0";

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getRegInfo() {
        return regInfo;
    }

    public void setRegInfo(String regInfo) {
        this.regInfo = regInfo;
    }

    public String getCurrInfo() {
        return currInfo;
    }

    public void setCurrInfo(String currInfo) {
        this.currInfo = currInfo;
    }
}
