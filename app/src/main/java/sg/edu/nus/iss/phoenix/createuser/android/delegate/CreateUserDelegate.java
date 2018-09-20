package sg.edu.nus.iss.phoenix.createuser.android.delegate;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

import sg.edu.nus.iss.phoenix.createuser.android.controller.MaintainUserController;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;

public class CreateUserDelegate extends AsyncTask <User, Void, String>{


    @Override
    protected String doInBackground(User... users) {

        URL searchUrl = MaintainUserController.buildUrl("/user/create",users[0]);

        String githubSearchResults = null;
        try {

            githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return githubSearchResults;

    }

    @Override
    protected void onPostExecute(String result) {

    }
}
