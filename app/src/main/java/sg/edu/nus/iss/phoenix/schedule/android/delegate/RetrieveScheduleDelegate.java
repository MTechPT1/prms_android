/**
 *@author: neelima nair
 */
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
        List<ProgramSlot> programSlots;
        if (result != null && !result.equals("")) {
            programSlots = setProgramSlotFromReponse(result);
            scheduleProgram.setProgramSlots(programSlots);
        } else {
            Log.v(TAG, "JSON response error.");
        }

        if (reviewSelectScheduleProgramController != null){
            reviewSelectScheduleProgramController.displayScheduleProgram(scheduleProgram);
        }
    }

    protected List<ProgramSlot> setProgramSlotFromReponse(String result){
        List<ProgramSlot> programSlots = new ArrayList<ProgramSlot>();
        try {
            Log.v(TAG,"result:"+result);
            JSONObject reader = new JSONObject(result);
            JSONArray rpArray = reader.getJSONArray("psList");

            for (int i = 0; i < rpArray.length(); i++) {
                ProgramSlot programSlot = new ProgramSlot();
                JSONObject rpJson = rpArray.getJSONObject(i);

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
                programSlots.add(programSlot);
            }
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }
        return programSlots;
    }
}