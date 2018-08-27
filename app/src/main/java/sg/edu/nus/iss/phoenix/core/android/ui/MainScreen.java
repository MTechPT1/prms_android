package sg.edu.nus.iss.phoenix.core.android.ui;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;

public class MainScreen extends AppCompatActivity {
    private Button mbtn_radio_program;
    private Button mbtn_schedule;
    private Button mbtn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbtn_radio_program = (Button) findViewById(R.id.button_radio_program);
        // Set a click listener on Maintain Program Button.
        mbtn_radio_program.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                ControlFactory.getMainController().selectMaintainProgram();
            }

        });

        mbtn_schedule = (Button) findViewById(R.id.button_schedule);
        // Set a click listener on Maintain Schedule Button.
        mbtn_schedule.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                ControlFactory.getMainController().selectMaintainSchedule();
            }

        });

        // Log out  - Back to PRMS  Activity - Pre Login Page
        mbtn_logout = (Button) findViewById(R.id.btnLogout);
        // Set a click listener on Logout Button
        mbtn_logout.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                ControlFactory.getMainController().selectLogout();
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getMainController().onDisplay(this);
    }

    @Override
    public void onBackPressed() {
        // MainScreen does not currently support Back button.
        return;
    }

    public void showUsername(String username) {
        TextView usernameView = (TextView) findViewById(R.id.username);
        usernameView.setText(username);
    }
}

