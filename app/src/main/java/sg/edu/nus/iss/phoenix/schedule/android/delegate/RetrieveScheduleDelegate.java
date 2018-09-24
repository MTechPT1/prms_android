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

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.android.controller.ReviewSelectScheduleProgramController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ScheduleProgram;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_PROGRAM;

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
        ScheduleProgram scheduleProgram = new ScheduleProgram();
        List<ProgramSlot> programSlots = new ArrayList<ProgramSlot>();
        if (result != null && !result.equals("")) {
            try {
                JSONObject reader = new JSONObject(result);
                //TODO change the name to what Karen would be sending from backend
                JSONArray rpArray = reader.getJSONArray("rpList");

                for (int i = 0; i < rpArray.length(); i++) {
                    ProgramSlot programSlot = new ProgramSlot();
                    JSONObject rpJson = rpArray.getJSONObject(i);
                    //TODO check if all these paramteres can be fetched in first call itself or needs to be
                    //fetched in multiple calls
                    String programName = rpJson.getString("programName");
                    String radioProgramDescription = rpJson.getString("radioProgramDescription");
                    String radioProgramDuration = rpJson.getString("radioProgramDuration");
                    RadioProgram radioProgram = new RadioProgram(programName,radioProgramDescription,radioProgramDuration);
                    programSlot.setRadioProgram(radioProgram);

                    String weekId = rpJson.getString("weekId");
                    if(weekId != null){
                        programSlot.setWeekId(Integer.valueOf(weekId));
                    }

                    String startTime = rpJson.getString("startTime");
                    programSlot.setStartTime(startTime);

                    String duration = rpJson.getString("duration");
                    if(duration != null){
                        programSlot.setDuration(Integer.valueOf(duration));
                    }

                    String assignedBy = rpJson.getString("assignedBy");
                    programSlot.setAssignedBy(assignedBy);

                    //TODO what should be done for presenter and producer

                    programSlots.add(programSlot);
                }
                scheduleProgram.setProgramSlots(programSlots);
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }

        if (reviewSelectScheduleProgramController != null){
            reviewSelectScheduleProgramController.displayScheduleProgram(scheduleProgram);
        }
    }
}