package sg.edu.nus.iss.phoenix.core.android.delegate;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DelegateHelper {
    private static final String TAG = DelegateHelper.class.getName();

    // RESTful parameters.
    public final static String PRMS_BASE_URL_AUTHENTICATE = "http://58.182.85.189:8080/phoenix/rest/Login/doLogin?";
    public final static String PRMS_BASE_URL_RADIO_PROGRAM = "http://58.182.85.189:8080/phoenix/rest/radioprogram";

    public final static String PRMS_BASE_URL_SCHEDULE_PROGRAM = "http://58.182.85.189:8080/phoenix/rest/schedule";
    public final static String PRMS_BASE_URL_USER = "http://58.182.85.189:8080/phoenix/rest/user";
   /* public final static String PRMS_BASE_URL_AUTHENTICATE = "http://172.17.83.219:8080/phoenix/rest/Login/doLogin?";
    public final static String PRMS_BASE_URL_RADIO_PROGRAM = "http://172.17.83.219:8080/phoenix/rest/radioprogram";

    public final static String PRMS_BASE_URL_SCHEDULE_PROGRAM = "http://172.17.83.219:8080/phoenix/rest/schedule";
    public final static String PRMS_BASE_URL_USER = "http://172.17.83.219:8080/phoenix/rest/user";
*/

    public static int getWeekId(String startDate){
        Calendar calendar = new GregorianCalendar();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date trialTime = format.parse (startDate);
            calendar.setTime(trialTime);
            System.out.println("Week number:"+calendar.get(Calendar.WEEK_OF_YEAR));
        }catch (ParseException p){
            Log.v(TAG, p.getMessage());
        }
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

}
