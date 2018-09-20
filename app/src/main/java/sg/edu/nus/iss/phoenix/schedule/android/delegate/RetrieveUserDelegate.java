package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.os.AsyncTask;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ReviewSelectPresenterProducerController;

public class RetrieveProducerDelegate extends AsyncTask<String, Void, String> {

    private static final String TAG = RetrieveProducerDelegate.class.getName();

    private ReviewSelectPresenterProducerController reviewSelectPresenterProducerController;

    public RetrieveProducerDelegate(ReviewSelectPresenterProducerController reviewSelectPresenterProducerController){
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