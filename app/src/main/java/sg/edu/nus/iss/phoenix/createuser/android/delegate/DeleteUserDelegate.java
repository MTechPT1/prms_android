package sg.edu.nus.iss.phoenix.createuser.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.createuser.android.controller.MaintainUserController;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_PROGRAM;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;

public class DeleteUserDelegate extends AsyncTask <User, Void, Boolean>{

    private static final String TAG = DeleteUserDelegate.class.getName();
    private MaintainUserController maintainUserController;
    String errorMsg = "Delete User Failed";
    public DeleteUserDelegate(MaintainUserController maintainUserController){
        this.maintainUserController = maintainUserController;
    }

    @Override
    protected Boolean doInBackground(User... users) {

        String name = null;
        try {
            name = URLEncoder.encode(String.valueOf(users[0].getUserId()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        Uri builtUri = Uri.parse(PRMS_BASE_URL_USER).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"delete/" + users[0].getUserId().toString()).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
           // return new Boolean(false);
        }

        boolean success = false;
        String jsonResp = null;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setUseCaches (false);
            System.out.println(httpURLConnection.getResponseCode());
            Log.v(TAG, "Http DELETE response " + httpURLConnection.getResponseCode());
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                success = true;
            }else {
                InputStream in = httpURLConnection.getErrorStream();
                Log.v(TAG, "Http POST response content " + httpURLConnection.getErrorStream());
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    jsonResp = scanner.next();
                    errorMsg = errorMessages(jsonResp);
                }
                success = false;
            }
        } catch (IOException exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return new Boolean(success);

    }

    private String errorMessages(String result){
        String errorMessage =null;
        try {
            JSONObject reader = new JSONObject(result);
            errorMessage = reader.getString("errorMessage");
            //String status = reader.getString("httpStatus");
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }
        return errorMessage;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        this.maintainUserController.userDeleted(result,errorMsg);
    }
}