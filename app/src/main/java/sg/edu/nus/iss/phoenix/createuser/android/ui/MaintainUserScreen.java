package sg.edu.nus.iss.phoenix.createuser.android.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;

public class MaintainUserScreen extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static final int TYPE_CREATE = 0;
    private static final int TYPE_MODIFY = 1;
    private static final int TYPE_DELETE = 2;

    private static final String CREATE_BTN = "CREATE";
    private static final String MODIFY_BTN = "MODIFY";
    private static final String DELETE_BTN = "DELETE";

    private ListView maintainuserListView;
    private Button actionBtn;
    private MaintainUserAdapter adapter;
    private User user = new User();
    private int  actionType = TYPE_CREATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionType = ControlFactory.getMaintainUserController().getActionType();

        setContentView(R.layout.acitivity_maintainuser);
        initView();
    }

    private void initView(){
        maintainuserListView = (ListView) findViewById(R.id.maintainuser_list);
        adapter = new MaintainUserAdapter(this,user);
        maintainuserListView.setOnItemClickListener(this);
        maintainuserListView.setAdapter(adapter);

        actionBtn = (Button) findViewById(R.id.confirm_button);
        switch (actionType){
            case TYPE_CREATE:{
                actionBtn.setText(CREATE_BTN);
            }
            break;
            case TYPE_MODIFY:{
                actionBtn.setText(MODIFY_BTN);
            }
            break;
            case TYPE_DELETE:{
                actionBtn.setText(DELETE_BTN);
            }
            break;
        }

    }


    public void confirmChanges(View view){
        switch (actionType){
            case TYPE_CREATE:{
                createNewUser();
            }
            break;
            case TYPE_MODIFY:{
                modifyUser();
            }
            break;
            case TYPE_DELETE:{
                deleteUser();
            }
            break;
        }
    }

    private void createNewUser(){

    }

    private void modifyUser(){

    }

    private void deleteUser(){

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        adapter.didSelectCheckbox(position);
        adapter.notifyDataSetChanged();
    }
}
