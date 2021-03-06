package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.createuser.android.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.android.controller.ReviewSelectScheduleProgramController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ScheduleProgram;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_PROGRAM;

/**
 * <p><b>RetrieveScheduleDelegate</b> helps in retrieving the program slots from the backend by
 * invoking a restful web services api. This delegate is in turn invoked from the
 * <b>{@link ReviewSelectScheduleProgramController}</b> class.</p>
 *
 *@author: neelima nair
 */
public class RetrieveScheduleDelegate extends AsyncTask<String, Void, String> {

    private static final String TAG = RetrieveScheduleDelegate.class.getName();

    private ReviewSelectScheduleProgramController reviewSelectScheduleProgramController;

    public RetrieveScheduleDelegate(ReviewSelectScheduleProgramController reviewSelectScheduleProgramController){
        this.reviewSelectScheduleProgramController = reviewSelectScheduleProgramController;
    }

    /**
     * Called asynchronously to get the list of program slots for a week
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        Uri builtUri1 = Uri.parse(PRMS_BASE_URL_SCHEDULE_PROGRAM).buildUpon().build();
        Uri builtUri = Uri.withAppendedPath(builtUri1, params[0]).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri, params[1]).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return e.getMessage();
        }

        String jsonResp = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) jsonResp = scanner.next();
            if(urlConnection.getResponseCode() != 200 && urlConnection.getResponseCode() !=204){
                reviewSelectScheduleProgramController.displayError(urlConnection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }

        return jsonResp;
    }


    /**
     * Called asynchronously post the data is received from backend
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {
        List<ProgramSlot> programSlots = new ArrayList<ProgramSlot>();
        ScheduleProgram scheduleProgram = new ScheduleProgram();
        try{
            if (result != null && !result.equals("")) {
                JSONObject reader = new JSONObject(result);
                JSONArray rpArray = reader.getJSONArray("psList");
                for (int i = 0; i < rpArray.length(); i++) {
                    ProgramSlot programSlot = new ProgramSlot();
                    JSONObject rpJson = rpArray.getJSONObject(i);
                    programSlot= setProgramSlotFromReponse(rpJson);
                    programSlots.add(programSlot);

                }


                scheduleProgram.setProgramSlots(programSlots);
            } else {
                Log.v(TAG, "JSON response error.");
            }
        } catch (JSONException e) {
        Log.v(TAG, e.getMessage());
        }


        if (reviewSelectScheduleProgramController != null){
            reviewSelectScheduleProgramController.displayScheduleProgram(scheduleProgram);
        }
    }

    protected ProgramSlot setProgramSlotFromReponse(JSONObject rpJson){
        ProgramSlot programSlot = new ProgramSlot();
        try {
            Log.v(TAG,"rpJson:"+rpJson);

                String programName = rpJson.getString("programName");
                RadioProgram radioProgram = new RadioProgram();
                radioProgram.setRadioProgramName(programName);
                programSlot.setRadioProgram(radioProgram);

                String programSlotId = rpJson.getString("programSlotId");
                if(programSlotId != null){
                    programSlot.setId(Integer.valueOf(programSlotId));
                }
                String weekId = rpJson.getString("weekId");
                if(weekId != null){
                    programSlot.setWeekId(Integer.valueOf(weekId));
                }
                String startTime = rpJson.getString("startDate");
                programSlot.setStartTime(startTime);
                String duration = rpJson.getString("duration");
                if(duration != null){
                    programSlot.setDuration(Integer.valueOf(duration));
                }
                String assignedBy = rpJson.getString("assignedBy");
                programSlot.setAssignedBy(assignedBy);

                String presenterId = rpJson.getString("presenterId");
                User presenter = new User();
                presenter.setUserId(presenterId);
                programSlot.setPresenter(presenter);
                String producerId = rpJson.getString("producerId");
                User producer = new User();
                producer.setUserId(producerId);
                programSlot.setProducer(producer);

                Log.v(TAG,programName+"::"+weekId);
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }
        return programSlot;
    }
}