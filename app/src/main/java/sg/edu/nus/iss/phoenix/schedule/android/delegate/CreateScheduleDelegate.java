package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.schedule.android.controller.MaintainScheduleController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_PROGRAM;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.getWeekId;

/**
 * <p><b>CreateScheduleDelegate</b> helps in creating the program slots. It invokes the create
 * restful web services api. This delegate is in turn invoked from the
 * <b>{@link MaintainScheduleController}</b> class.</p>
 *
 *@author: neelima nair
 */
public class CreateScheduleDelegate extends AsyncTask<ProgramSlot, Void, Boolean> {

    private static final String TAG = CreateScheduleDelegate.class.getName();

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

    public CreateScheduleDelegate(MaintainScheduleController maintainScheduleController){
        this.maintainScheduleController = maintainScheduleController;
    }

    /**
     * Called asynchronously to invoke the web service to create the program slot
     * @param params
     * @return
     */
    @Override
    protected Boolean doInBackground(ProgramSlot... params) {
        boolean success = false;
        Uri builtUri = Uri.parse(PRMS_BASE_URL_SCHEDULE_PROGRAM).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"create").buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        JSONObject json = createJSON(params);
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dos = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeUTF(json.toString());
            dos.write(256);
            success = handleError(httpURLConnection);
        } catch (Exception exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
            if (dos != null) {
                try {
                    dos.flush();
                    dos.close();
                } catch (IOException exception) {
                    Log.v(TAG, exception.getMessage());
                }
            }
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
     * Creates a jsonobject from a program slot object
     * @param params ProgramSlot
     * @return JSONObject
     */
    private JSONObject createJSON(ProgramSlot... params){
        JSONObject json = new JSONObject();
        int duration =1;
        if(params[0] != null && params[0].getDuration() != 0){
            duration = params[0].getDuration();
        }
        try {
            json.put("assignedBy", params[0].getAssignedBy());
            json.put("duration", duration);
            json.put("startDate", params[0].getStartTime());
            json.put("programName", params[0].getRadioProgram().getRadioProgramName());
            json.put("presenterId", params[0].getPresenter().getUserId());
            json.put("producerId", params[0].getProducer().getUserId());
            json.put("weekId", getWeekId(params[0].getStartTime()));

        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }
        return json;
    }

    /**
     * Called asynchronously post the call is returned from backend
     * @param result
     */
    @Override
    protected void onPostExecute(Boolean result) {
        if (result){
            maintainScheduleController.scheduleCreated(result.booleanValue());
        }else{
            maintainScheduleController.displayError(getErrorMessage());
        }
    }
}