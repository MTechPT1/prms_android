package sg.edu.nus.iss.phoenix.createuser.android.delegate;

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

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_AUTHENTICATE;

public class MaintainUserDelegate extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        return "";
    }

    @Override
    protected void onPostExecute(String result) {

    }
}