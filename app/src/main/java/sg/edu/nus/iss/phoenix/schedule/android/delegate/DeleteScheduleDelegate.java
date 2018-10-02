package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.schedule.android.controller.MaintainScheduleController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_PROGRAM;

/**
 * <p><b>DeleteScheduleDelegate</b> helps in deleting the program slots. It invokes the delete
 * restful web services api. This delegate is in turn invoked from the
 * <b>{@link MaintainScheduleController}</b> class.</p>
 *
 *@author: neelima nair
 */
public class DeleteScheduleDelegate extends AsyncTask<ProgramSlot, Void, Boolean> {

    private static final String TAG = DeleteScheduleDelegate.class.getName();

    private MaintainScheduleController maintainScheduleController;

    private String errorMessage;

    /**
     * getErrorMessage gets the error message string which contains the message
     * returned from the backend.
     * @return String
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * setErrorMessage sets the error message string which contains the message
     * returned from the backend.
     * @param errorMessage String
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public DeleteScheduleDelegate(MaintainScheduleController maintainScheduleController){
        this.maintainScheduleController = maintainScheduleController;
    }

    /**
     * Called asynchronously to invoke the web service to delete the program slot
     * @param params
     * @return
     */
    @Override
    protected Boolean doInBackground(ProgramSlot... params) {
        // Encode the name of radio program in case of the presence of special characters.
        String programSlotId = null;
        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        try {
            programSlotId = URLEncoder.encode(String.valueOf(params[0].getId()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }
        Uri builtUri = Uri.parse(PRMS_BASE_URL_SCHEDULE_PROGRAM).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"delete").buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri, programSlotId).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setUseCaches (false);
            System.out.println(httpURLConnection.getResponseCode());
            Log.v(TAG, "Http DELETE response " + httpURLConnection.getResponseCode());
            success = handleError(httpURLConnection);
        } catch (Exception exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return new Boolean(success);
    }

    /**
     * This method retrieves and sets the error message in case the web service returns
     * an error. It returns true if no error is there. Else returns a false.
     * @param httpURLConnection
     * @return
     */
    private boolean handleError(HttpURLConnection httpURLConnection){
        boolean success = true;
        try {
            Log.v(TAG, "Http PUT response " + httpURLConnection.getResponseCode());
            if(httpURLConnection.getResponseCode() != 200 && httpURLConnection.getResponseCode() !=204) {
                InputStream in = httpURLConnection.getErrorStream();
                String jsonResp = null;
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) jsonResp = scanner.next();
                Log.v(TAG, "Reponse message" + jsonResp);

                try{
                    if (jsonResp != null && !jsonResp.equals("")) {
                        JSONObject reader = new JSONObject(jsonResp);
                        setErrorMessage(reader.getString("errorMessage"));
                        success = false;
                    }
                } catch (JSONException e) {
                    Log.v(TAG, e.getMessage());
                }
            }
        }catch (IOException exception) {
            Log.v(TAG, exception.getMessage());
        }
        return success;
    }

    /**
     * Called asynchronously post the call is returned from backend
     * @param result
     */
    @Override
    protected void onPostExecute(Boolean result) {
        if (result){
            maintainScheduleController.scheduleDeleted(result.booleanValue());
        }else{
            maintainScheduleController.displayError(getErrorMessage());
        }
    }
}