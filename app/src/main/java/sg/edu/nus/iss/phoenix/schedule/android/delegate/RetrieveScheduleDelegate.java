package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.os.AsyncTask;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ReviewSelectScheduleProgramController;

public class RetrieveScheduleDelegate extends AsyncTask<String, Void, String> {

    private static final String TAG = RetrieveScheduleDelegate.class.getName();

    private ReviewSelectScheduleProgramController reviewSelectScheduleProgramController;

    public RetrieveScheduleDelegate(ReviewSelectScheduleProgramController reviewSelectScheduleProgramController){
        this.reviewSelectScheduleProgramController = reviewSelectScheduleProgramController;
    }

    public void execute(){

    }

    @Override
    protected String doInBackground(String... params) {
        return "";
    }

    @Override
    protected void onPostExecute(String result) {

    }
}