package sg.edu.nus.iss.phoenix.createuser.android.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;

public class UserListScreen extends AppCompatActivity {

    private ListView userListView;
    private UserListAdapter adapter;
    //private ArrayList<User> userList = new ArrayList<User>();

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

//    void initDummyData(){
//        User user1 = new User();
//        user1.setPresenter(true);
//        user1.setProducer(false);
//        user1.setUserId("11223232");
//        user1.setUserName("ChangLing Liu1");
//        userList.add(user1);
//
//        User user2 = new User();
//        user2.setPresenter(true);
//        user2.setProducer(false);
//        user2.setUserId("1122d2323232");
//        user2.setUserName("ChangLing Liu2");
//        userList.add(user2);
//
//        User user3 = new User();
//        user3.setPresenter(true);
//        user3.setProducer(false);
//        user3.setUserId("1123232");
//        user3.setUserName("ChangLing Liu3");
//        userList.add(user3);
//
//        User user4 = new User();
//        user4.setPresenter(true);
//        user4.setProducer(false);
//        user4.setUserId("112323232");
//        user4.setUserName("ChangLing Liu4");
//        userList.add(user4);
//    }

}


