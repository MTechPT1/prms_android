package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;

public class ScheduleScreen extends AppCompatActivity{

    private Button button_presenter;
    private Button button_producer;
    private Button button_radioProgram;
    private TextView textView_timeslot;
    private TextView textView_presenter;
    private TextView textView_producer;
    private TextView textView_radioprogram;

    private static User selectedUser;
    private static int selectedrole;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_schedule_screen);

        setupView();
        updateUI();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void setupView() {
        button_presenter = (Button) findViewById(R.id.button_presenter);
        button_producer = (Button) findViewById(R.id.button_producer);
        button_radioProgram = (Button) findViewById(R.id.button_radioProgram);

        button_presenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getReviewSelectPresenterProducerController().startUseCase(1, "PresenterName");
            }
        });

        button_producer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getReviewSelectPresenterProducerController().startUseCase(2, "ProducerName");
            }
        });

        button_radioProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getReviewSelectProgramController().startUseCase();
            }
        });

        textView_timeslot = (TextView) findViewById(R.id.textView_timeslot);
        textView_radioprogram = (TextView) findViewById(R.id.textView_radioprogram);

    }

    public static void selectedUser(User u , int role){
        selectedUser = u;
        selectedrole = role;
    }

    private void updateUI(){
        if(selectedrole == Constant.PRESENTER){
            textView_presenter = (TextView) findViewById(R.id.textView_presenter);
            textView_presenter.setText(selectedUser.getUserName());
        } else if(selectedrole == Constant.PRODUCER){
            textView_producer = (TextView) findViewById(R.id.textView_producer);
            textView_producer.setText(selectedUser.getUserName());
        }
    }

}

