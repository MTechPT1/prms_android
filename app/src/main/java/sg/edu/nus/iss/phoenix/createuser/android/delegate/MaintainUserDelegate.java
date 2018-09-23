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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.createuser.android.controller.MaintainUserController;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_AUTHENTICATE;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;

public class MaintainUserDelegate extends AsyncTask<String, Void, String> {

    private MaintainUserController maintainUserController;
    private static final String TAG = MaintainUserDelegate.class.getName();

    public MaintainUserDelegate(MaintainUserController maintainUserController){
        this.maintainUserController = maintainUserController;
    }

    @Override
    protected String doInBackground(String... params) {

        Uri builtUri = Uri.parse(PRMS_BASE_URL_USER).buildUpon()
                .appendQueryParameter("role_type", params[0])
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

        List<User> userList = new ArrayList<User>();
        if (result != null && !result.equals("")) {
            try {
                JSONObject reader = new JSONObject(result);
                //TODO change the name to what Leon would be sending from backend
                JSONArray rpArray = reader.getJSONArray("userList");

                for (int i = 0; i < rpArray.length(); i++) {
                    User user = new User();

                    JSONObject rpJson = rpArray.getJSONObject(i);
                    //TODO check if all these paramteres can be fetched in first call itself or needs to be
                    //fetched in multiple calls
                    String userid = rpJson.getString("id");
                    String username = rpJson.getString("name");
                    String password = rpJson.getString("password");
                    JSONArray roles = rpJson.getJSONArray("roles");

                    //TODO add roles later
                    for (int j = 0; i < roles.length(); j++) {
                        JSONObject rolesJSON = rpArray.getJSONObject(j);
                        String rolename = rpJson.getString("role");

                        if (rolename.compareToIgnoreCase("PRESENTER") == 0)
                                user.setPresenter(true);


                        if (rolename.compareToIgnoreCase("PRODUCER") == 0)
                            user.setProducer(true);
                    }


                    user.setUserId(userid);
                    user.setUserName(username);

                   //TODO what should be done for presenter and producer

                    userList.add(user);
                }

            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }

        maintainUserController.displayPresenterProducerListScreen(userList);

    }
}