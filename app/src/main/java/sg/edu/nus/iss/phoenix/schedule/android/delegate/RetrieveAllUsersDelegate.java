package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.os.AsyncTask;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ReviewSelectPresenterProducerController;

public class RetrievePresenterDelegate extends AsyncTask<String, Void, String> {

    private static final String TAG = RetrievePresenterDelegate.class.getName();

    private ReviewSelectPresenterProducerController reviewSelectPresenterProducerController;

    public RetrievePresenterDelegate(ReviewSelectPresenterProducerController reviewSelectPresenterProducerController){
        this.reviewSelectPresenterProducerController = reviewSelectPresenterProducerController;
    }

    @Override
    protected String doInBackground(String... params) {
        return "";
    }

    @Override
    protected void onPostExecute(String result) {

    }
}