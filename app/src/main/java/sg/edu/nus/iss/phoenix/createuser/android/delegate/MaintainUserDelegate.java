package sg.edu.nus.iss.phoenix.createuser.android.delegate;

import android.os.AsyncTask;

import sg.edu.nus.iss.phoenix.createuser.android.controller.MaintainUserController;

public class MaintainUserDelegate extends AsyncTask<String, Void, String> {

    private MaintainUserController maintainUserController;


    public MaintainUserDelegate(MaintainUserController maintainUserController){
        this.maintainUserController = maintainUserController;
    }

    @Override
    protected String doInBackground(String... params) {
        return "";
    }

    @Override
    protected void onPostExecute(String result) {

    }
}