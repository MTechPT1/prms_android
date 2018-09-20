package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.content.DialogInterface;
import android.content.Intent;
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
    private Button button_schedule_delete;
    private TextView textView_timeslot;
    private TextView textView_presenter;
    private TextView textView_producer;
    private TextView textView_radioprogram;

    private static User selectedUser;
    private static int selectedrole;

    private static int scheduleMode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_schedule_screen);

        Intent intent = getIntent();
        scheduleMode = intent.getIntExtra(Constant.SCHEDULEMODE, 10);

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
        button_schedule_delete =(Button) findViewById(R.id.button_schedule_delete);
        textView_timeslot = (TextView) findViewById(R.id.textView_timeslot);
        textView_radioprogram = (TextView) findViewById(R.id.textView_radioprogram);

        button_presenter.setVisibility(View.VISIBLE);
        button_producer.setVisibility(View.VISIBLE);
        button_radioProgram.setVisibility(View.VISIBLE);
        button_schedule_delete.setVisibility(View.INVISIBLE);

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

        switch(scheduleMode){
            case Constant.CREATE:
                break;

            case Constant.EDIT:
                break;

            case Constant.COPY:
                break;

            case Constant.DELETE:
                button_presenter.setVisibility(View.INVISIBLE);
                button_producer.setVisibility(View.INVISIBLE);
                button_radioProgram.setVisibility(View.INVISIBLE);

                button_schedule_delete.setVisibility(View.VISIBLE);
                button_schedule_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowAlertDialog("Are you sure you want to delete this schedule?");
                    }
                });
                break;


        }



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

    private void ShowAlertDialog(String string) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ScheduleScreen.this);
        builder.setMessage(string)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ControlFactory.getMaintainScheduleController().deleteSchedule();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create();
        builder.show();
    }

}

