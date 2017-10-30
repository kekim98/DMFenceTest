package locationCheckModule;

/**
 * Created by kekim98 on 2017-05-26.
 */

public class _Location {
    /*String CID="";  // cell id
    String ADDR1=""; //wifi mac addr
    String ADDR2="";
    String ADDR3="";
    String ADDR4="";*/
    String LAT=""; //위도
    String LNG=""; //경도

    public String getLAT() {
        return LAT;
    }

    public void setLAT(String LAT) {
        this.LAT = LAT;
    }

    public String getLNG() {
        return LNG;
    }

    public void setLNG(String LNG) {
        this.LNG = LNG;
    }
}
