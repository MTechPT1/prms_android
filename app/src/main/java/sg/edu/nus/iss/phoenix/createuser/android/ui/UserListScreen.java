package sg.edu.nus.iss.phoenix.createuser.android.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;

public class UserListScreen extends AppCompatActivity {

    private ListView userListView;
    private UserListAdapter adapter;
    //private ArrayList<User> userList = new ArrayList<User>();
    private RelativeLayout mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        initView();
    }

    private void initView(){
         //initDummyData();
         userListView = (ListView) findViewById(R.id.userlist);
         adapter = new UserListAdapter(this);
         userListView.setAdapter(adapter);
         mLoadingIndicator = (RelativeLayout) findViewById(R.id.loadingPanel);
         ControlFactory.getMaintainUserController().onDisplayUserList(UserListScreen.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add){
            // create new user
            ControlFactory.getMaintainUserController().setMaintainUser(0,null);
            return true;
        }else{
            return false;
        }
    }

    public void displayAllUsers(ArrayList<User> users){
        adapter.setUserList(!users.isEmpty() ? users: new ArrayList<User>());
        adapter.notifyDataSetChanged();
    }

    public void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

}


