package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;

/**
 * <p><b>ReviewSelectPresenterProducerScreen</b> UI of the presenter producer screen</p>
 *
 *@author: Wai Kin
 */
public class ReviewSelectPresenterProducerScreen extends AppCompatActivity {

    private String userName;
    private int userRole;
    private ArrayList<User> users;
    private User user;
    private User selectedUser;
    private PresenterProducerAdapter ppAdapter;
    private ListView ListViewPresenterProducer;


    /**
     * OnCreate() for Android AppCompact activity, getting the intent data and setting up the UI
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        userRole = intent.getIntExtra(Constant.PRESENTERPRODUCER, Constant.PRESENTER);
        setContentView(R.layout.acitivity_presenter_producer_screen);

        setupView();
    }

    /**
     * onPostCreate() for Android AppCompact activity, getting the list of Users
     *
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        ControlFactory.getMaintainUserController().onDisplayPresenterProducerList(ReviewSelectPresenterProducerScreen.this);
    }

    /**
     * Setting up the UI
     */
    private void setupView() {
        CardView cvPresenter = (CardView) findViewById(R.id.cardView_Presenter);
        CardView cvProducer = (CardView) findViewById(R.id.cardView_Producer);

        if (userRole == Constant.PRESENTER) {
            cvPresenter.setVisibility(View.VISIBLE);
            cvProducer.setVisibility(View.GONE);
            updateUserName(Constant.PRESENTER, userName);
        } else if (userRole == Constant.PRODUCER) {
            cvPresenter.setVisibility(View.GONE);
            cvProducer.setVisibility(View.VISIBLE);
            updateUserName(Constant.PRODUCER, userName);
        }

        Button button_presenter_producer_done = (Button) findViewById(R.id.button_presenter_producer_done);
        button_presenter_producer_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getMaintainUserController().selectedUser(userRole, selectedUser);
                finish();
            }
        });
    }

    /**
     * Update the UI with selected User
     * @param role presenter or producer
     * @param userName selected name of presenter or producer
     */
    private void updateUserName(int role, String userName) {

        TextView textView_review_presenter = (TextView) findViewById(R.id.textView_review_presenter);
        TextView textView_review_producer = (TextView) findViewById(R.id.textView_review_producer);

        if (role == Constant.PRESENTER) {
            textView_review_presenter.setText(userName);
        } else if (userRole == Constant.PRODUCER) {
            textView_review_producer.setText(userName);
        }
    }

    /**
     * To show the user lists
     * @param users list of users
     */
    public void AllUsersRetrieved(ArrayList<User> users) {
        showUser(users);
    }

    /**
     * Setting up the listView
     * @param users list of users
     */
    public void showUser(ArrayList<User> users) {

        //filter the list of presenter or producer
        ArrayList<User> users_filtered = new ArrayList<User>();
        for(User u: users){
            if ((userRole == Constant.PRESENTER) && (u.isPresenter())) {
                users_filtered.add(u);
            } else if ((userRole == Constant.PRODUCER) && (u.isProducer())) {
                users_filtered.add(u);
            }
        }

        ppAdapter = new PresenterProducerAdapter(this, users_filtered);

        ListViewPresenterProducer = (ListView) findViewById(R.id.ListViewPresenterProducer);
        ListViewPresenterProducer.setAdapter(ppAdapter);

        // Setup the item selection listener
        ListViewPresenterProducer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = (User) parent.getItemAtPosition(position);

                updateUserName(userRole, selectedUser.getUserName());
            }
        });
    }

}

