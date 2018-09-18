package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;

public class ScheduleScreen extends AppCompatActivity {
    // Tag for logging
    private static final String TAG = ScheduleScreen.class.getName();
    private Button button_presenter;
    private Button button_producer;
    private Button button_radioProgram;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_schedule_screen);

        setupView();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    private void setupView() {
        button_presenter = (Button) findViewById(R.id.button_presenter);
        button_producer = (Button) findViewById(R.id.button_producer);
        button_radioProgram = (Button) findViewById(R.id.button_radioProgram);

        button_presenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getReviewSelectPresenterProducerController().startUseCase();
            }
        });

        button_producer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getReviewSelectPresenterProducerController().startUseCase();
            }
        });

        button_radioProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getReviewSelectProgramController().startUseCase();
            }
        });
    }

}

