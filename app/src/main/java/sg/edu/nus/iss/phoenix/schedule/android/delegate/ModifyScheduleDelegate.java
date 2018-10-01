/**
 *@author: neelima nair
 */
package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sg.edu.nus.iss.phoenix.schedule.android.controller.MaintainScheduleController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_PROGRAM;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.getWeekId;

public class ModifyScheduleDelegate extends AsyncTask<ProgramSlot, Void, Boolean> {

    private static final String TAG = ModifyScheduleDelegate.class.getName();

    private MaintainScheduleController maintainScheduleController;

    public ModifyScheduleDelegate(MaintainScheduleController maintainScheduleController){
        this.maintainScheduleController = maintainScheduleController;
    }

    /**
     * Called asynchronously to invoke the web service to modify the program slot
     * @param params
     * @return
     */
    @Override
    protected Boolean doInBackground(ProgramSlot... params) {

        Uri builtUri = Uri.parse(PRMS_BASE_URL_SCHEDULE_PROGRAM).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"update").buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        JSONObject json = new JSONObject();
        try {
            json.put("programSlotId", params[0].getId());
            json.put("assignedBy", params[0].getAssignedBy());
            json.put("duration", params[0].getDuration());
            json.put("startDate", params[0].getStartTime());
            json.put("programName", params[0].getRadioProgram().getRadioProgramName());
            json.put("presenterId", params[0].getPresenter().getUserId());
            json.put("producerId", params[0].getProducer().getUserId());
            json.put("weekId", getWeekId(params[0].getStartTime()));
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }

        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dos = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeUTF(json.toString());
            dos.write(256);
            Log.v(TAG, "Http PUT response " + httpURLConnection.getResponseCode());
            success = true;
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
     * Called asynchronously post the call is returned from backend
     * @param result
     */
    @Override
    protected void onPostExecute(Boolean result) {
        maintainScheduleController.scheduleModified(result.booleanValue());
    }
}