package sg.edu.nus.iss.phoenix.authenticate.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.authenticate.android.controller.LoginController;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_AUTHENTICATE;

public class LoginDelegate extends AsyncTask<String, Void, String> {
    // Tag for logging
    private static final String TAG = LoginDelegate.class.getName();

    private LoginController loginController;
    String userAuthResults = null;

    public LoginDelegate(LoginController loginController) {
        this.loginController = loginController;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri builtUri = Uri.parse(PRMS_BASE_URL_AUTHENTICATE).buildUpon()
                .appendQueryParameter("username", params[0])
                .appendQueryParameter("password", params[1])
                .build();
        Log.v(TAG, "Uri: " + builtUri.toString());
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

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject reader = new JSONObject(result);
            String authPass = "true";
            String authStatus = reader.getString("authStatus");
            String username = reader.getString("username");
            if (authStatus.equals(authPass)) {
                Log.v(TAG, "Logged in as " + username + ".");
                loginController.loggedIn(true, username);
            } else {
                Log.v(TAG, "Failed to log in.");
                loginController.loggedIn(false, username);
            }
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }
    }
}
