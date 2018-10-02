package sg.edu.nus.iss.phoenix.createuser.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.createuser.android.controller.MaintainUserController;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;
import sg.edu.nus.iss.phoenix.schedule.android.controller.MaintainScheduleController;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.CreateScheduleDelegate;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_PROGRAM;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;

public class CreateUserDelegate extends AsyncTask <User, Void, Boolean>{

    private static final String TAG = CreateUserDelegate.class.getName();
    private MaintainUserController maintainUserController;
    String errorMsg = "Create User Failed";
    public CreateUserDelegate(MaintainUserController maintainUserController){
        this.maintainUserController = maintainUserController;
    }

    @Override
    protected Boolean doInBackground(User... users) {

        Uri builtUri = Uri.parse(PRMS_BASE_URL_USER).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"create").buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        JSONObject json = new JSONObject();
        JSONObject rolePresenter = new JSONObject();
        JSONObject roleProducer = new JSONObject();
        JSONArray roles = new JSONArray();

        try {
            json.put("id", users[0].getUserId());
            json.put("password",users[0].getPassWord());
            json.put("name",users[0].getUserName());
            json.put("joinDate",users[0].getJoinDate());

            if (users[0].isPresenter()) {
                rolePresenter.put("role", "presenter");
                rolePresenter.put("accessPrivilege", "something");
                roles.put(rolePresenter);
            }


            if (users[0].isProducer()) {
                roleProducer.put("role", "producer");
                roleProducer.put("accessPrivilege", "something");
                roles.put(roleProducer);
            }

            json.put("roles",roles);
            
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }

        boolean success = false;
        String jsonResp = null;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dos = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            dos = new DataOutputStream(httpURLConnection.getOutputStream());
            Log.v(TAG,json.toString());
            dos.writeUTF(json.toString());
            dos.write(256);
            Log.v(TAG, "Http POST response " + httpURLConnection.getResponseCode());
            Log.v(TAG, "Http POST response msg " + httpURLConnection.getResponseMessage());
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
            if (dos != null) {
                try {
                    dos.flush();
                    dos.close();
                } catch (IOException exception) {
                    Log.v(TAG, exception.getMessage());
                }
            }
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
        this.maintainUserController.userCreated(result.booleanValue(),errorMsg);
    }
}
