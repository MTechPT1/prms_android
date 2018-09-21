package sg.edu.nus.iss.phoenix.createuser.android.controller;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.createuser.android.delegate.CreateUserDelegate;
import sg.edu.nus.iss.phoenix.createuser.android.delegate.DeleteUserDelegate;
import sg.edu.nus.iss.phoenix.createuser.android.delegate.MaintainUserDelegate;
import sg.edu.nus.iss.phoenix.createuser.android.delegate.ModifyUserDelegate;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;
import sg.edu.nus.iss.phoenix.createuser.android.ui.MaintainUserScreen;
import sg.edu.nus.iss.phoenix.createuser.android.ui.UserListScreen;

import static android.support.constraint.Constraints.TAG;

public class MaintainUserController {
     private UserListScreen userListScreen;
     private MaintainUserScreen maintainUserScreen;
    static private String PRMS_BASE_URL = "https://localhost";

     private int actionType;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public void startUsecase(){
         Intent intent = new Intent(MainController.getApp(), UserListScreen.class);
         MainController.displayScreen(intent);
     }

     public void onDisplayUserList(UserListScreen userListScreen){
         this.userListScreen = userListScreen;
         new MaintainUserDelegate(this).execute("all");
     }

     public void setMaintainUser(int actionType){
        this.actionType = actionType;
        Intent intent = new Intent(MainController.getApp(), MaintainUserScreen.class);
        MainController.displayScreen(intent);
    }

    public void processCreateUser(User user){
        new CreateUserDelegate(this).execute(user);


    }

    public void processDeleteUser(User user){
        new DeleteUserDelegate(this).execute(user);

    }

    public void processModifyUser(User user){
        new ModifyUserDelegate(this).execute(user);
    }

    static public URL buildUrl(String endpoint, User user){
        Uri.Builder uri = Uri.parse(PRMS_BASE_URL).buildUpon().appendPath(endpoint)
                .appendQueryParameter("id",user.getUserId())
                .appendQueryParameter("password","abcd")
                .appendQueryParameter("name",user.getUserName())
                .appendQueryParameter("role","Presenter");

        Log.v(TAG,uri.toString());

        URL url = null;

        try{
            url = new URL(uri.toString());

        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public void userCreated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUsecase();
    }
}
