package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

/**
 * <p><b>ScheduleScreen</b> UI of the schedule screen</p>
 *
 * @author: Wai Kin
 */
public class ScheduleScreen extends AppCompatActivity {

    //Variable for UI
    private ImageView button_timeslot;
    private ImageView button_presenter;
    private ImageView button_producer;
    private ImageView button_radioProgram;
    private ImageView button_radioProgram_duration;
    private Button button_schedule_procced;
    private TextView textView_timeslot;
    private TextView textView_presenter;
    private TextView textView_producer;
    private TextView textView_radioprogram;
    private TextView textView_radioprogram_durationValue;
    private Spinner spinner;
    private AlertDialog alert;

    //Variable for Calender
    private Calendar calendar = Calendar.getInstance();

    //Variable for Logic
    private SimpleDateFormat sdf;
    private String selecteddate;
    private String selectedTime;
    private int selectedDuration = 0;
    private static int scheduleMode;
    private User selectedPresenter;
    private User selectedProducer;
    private static ProgramSlot programSlot;

    /**
     * OnCreate() for Android AppCompact activity, getting the intent data and setting up the UI
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_schedule_screen);

        Intent intent = getIntent();
        scheduleMode = intent.getIntExtra(Constant.SCHEDULEMODE, 0);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            programSlot = (ProgramSlot) getIntent().getSerializableExtra(Constant.PRORGRAMSLOT); //Obtaining data
        }

        setupView();
        updateUI();
    }

    /**
     * onResume() for Android AppCompact activity, updating the UI when resume to the application
     */
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * Setting up the UI
     */
    private void setupView() {
        button_timeslot = (ImageView) findViewById(R.id.button_timeslot);
        button_presenter = (ImageView) findViewById(R.id.button_presenter);
        button_producer = (ImageView) findViewById(R.id.button_producer);
        button_radioProgram = (ImageView) findViewById(R.id.button_radioProgram);
        button_radioProgram_duration = (ImageView) findViewById(R.id.button_radioProgram_duration);
        button_schedule_procced = (Button) findViewById(R.id.button_schedule_procced);
        textView_timeslot = (TextView) findViewById(R.id.textView_timeslot);
        textView_radioprogram = (TextView) findViewById(R.id.textView_radioprogram);
        textView_radioprogram_durationValue = (TextView) findViewById(R.id.textView_radioprogram_durationValue);

        button_timeslot.setVisibility(View.VISIBLE);
        button_presenter.setVisibility(View.VISIBLE);
        button_producer.setVisibility(View.VISIBLE);
        button_radioProgram.setVisibility(View.VISIBLE);
        button_schedule_procced.setVisibility(View.VISIBLE);


        button_timeslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNewTimeSlot();
            }
        });

        button_presenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getMaintainUserController().getPresenterProducerScreen(Constant.PRESENTER, ScheduleScreen.this);
            }
        });

        button_producer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getMaintainUserController().getPresenterProducerScreen(Constant.PRODUCER, ScheduleScreen.this);
            }
        });

        button_radioProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getReviewSelectProgramController().startUseCase(ScheduleScreen.this);
            }
        });

        button_radioProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getReviewSelectProgramController().startUseCase(ScheduleScreen.this);
            }
        });

        button_radioProgram_duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDurationDialog();
            }
        });

        switch (scheduleMode) {
            case Constant.CREATE:
                button_schedule_procced.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validation()) {
                            ShowAlertDialog("Are you sure you want to create this schedule?");
                        }
                    }
                });
                break;

            case Constant.MODIFY:
                button_schedule_procced.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validation()) {
                            ShowAlertDialog("Are you sure you want to make these change(s)?");
                        }
                    }
                });
                break;

            case Constant.COPY:
                button_schedule_procced.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validation()) {
                            ShowAlertDialog("Are you sure you want to copy this schedule?");
                        }
                    }
                });
                break;

            case Constant.DELETE:
                button_timeslot.setVisibility(View.INVISIBLE);
                button_presenter.setVisibility(View.INVISIBLE);
                button_producer.setVisibility(View.INVISIBLE);
                button_radioProgram.setVisibility(View.INVISIBLE);
                button_radioProgram_duration.setVisibility(View.INVISIBLE);

                button_schedule_procced.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validation()) {
                            ShowAlertDialog("Are you sure you want to delete this schedule?");
                        }
                    }
                });
                break;
        }

    }

    /**
     * To show the duration selection dialog
     */
    public void showDurationDialog() {

        View view = getLayoutInflater().inflate(R.layout.item_duration, null);
        final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleScreen.this);

        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (selectedDuration != 0) {
                            if (programSlot == null) {
                                programSlot = new ProgramSlot();
                            }
                            programSlot.setDuration(selectedDuration);
                            updateUI();
                            dialog.cancel();
                        }
                    }
                });

        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ScheduleScreen.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.duration_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDuration = Integer.valueOf(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(view);
        alert = builder.create();
        alert.show();

    }

    /**
     * Update the programslot with selected radio program
     *
     * @param rd selected radio program
     */
    public void selectedRadioProgram(RadioProgram rd) {
        if (programSlot == null) {
            programSlot = new ProgramSlot();
        }
        this.programSlot.setRadioProgram(rd);
        textView_radioprogram.setError(null);

        updateUI();
    }

    /**
     * Updating the UI
     */
    private void updateUI() {
        textView_timeslot = (TextView) findViewById(R.id.textView_timeslot);
        textView_presenter = (TextView) findViewById(R.id.textView_presenter);
        textView_producer = (TextView) findViewById(R.id.textView_producer);
        textView_radioprogram = (TextView) findViewById(R.id.textView_radioprogram);
        textView_radioprogram_durationValue = (TextView) findViewById(R.id.textView_radioprogram_durationValue);

        if (programSlot != null) {
            if (programSlot.getStartTime() != null) {
                textView_timeslot.setText(programSlot.getStartTime());
            }
            if (programSlot.getPresenter() != null) {
                Log.i("Tag", "getPresenter: " + programSlot.getPresenter().getUserId());
                textView_presenter.setText(programSlot.getPresenter().getUserId());
            }
            if (programSlot.getProducer() != null) {
                Log.i("Tag", "getProducer: " + programSlot.getProducer().getUserId());
                textView_producer.setText(programSlot.getProducer().getUserId());
            }
            if (programSlot.getRadioProgram() != null) {
                textView_radioprogram.setText(programSlot.getRadioProgram().getRadioProgramName());
            }
            if (programSlot.getDuration() != 0) {
                textView_radioprogram_durationValue.setText(String.valueOf(programSlot.getDuration()));
            }
        }

    }

    /**
     * Show the confirmation dialog
     *
     * @param string confirmation message
     */
    private void ShowAlertDialog(String string) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ScheduleScreen.this);
        builder.setMessage(string)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        programSlot.setAssignedBy(Constant.loggedUserName);

                        switch (scheduleMode) {
                            case Constant.CREATE:
                                ControlFactory.getMaintainScheduleController().createSchedule(programSlot, ScheduleScreen.this);
                                break;

                            case Constant.MODIFY:
                                ControlFactory.getMaintainScheduleController().modifySchedule(programSlot, ScheduleScreen.this);
                                break;

                            case Constant.COPY:
                                ControlFactory.getMaintainScheduleController().copySchedule(programSlot, ScheduleScreen.this);
                                break;

                            case Constant.DELETE:
                                ControlFactory.getMaintainScheduleController().deleteSchedule(programSlot, ScheduleScreen.this);
                                break;
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create();
        builder.show();
    }

    /**
     * To select new date
     */
    private void selectNewTimeSlot() {
        showCalendar();
    }

    /**
     * Pop up the DatePickerDialog
     */
    private void showCalendar() {
        new DatePickerDialog(ScheduleScreen.this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "yyyy-MM-dd";
            sdf = new SimpleDateFormat(myFormat, Locale.UK);
            selecteddate = sdf.format(calendar.getTime());

            selecetNewTime();
        }
    };

    /**
     * To select new time
     */
    private void selecetNewTime() {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        new TimePickerDialog(ScheduleScreen.this, time, mHour, mMinute, true).show();
    }

    final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            String myFormat = "HH:mm:ss";
            sdf = new SimpleDateFormat(myFormat, Locale.UK);

            selectedTime = sdf.format(calendar.getTime());

            if (programSlot == null) {
                programSlot = new ProgramSlot();
            }
            programSlot.setStartTime(selecteddate + " " + selectedTime);
            textView_timeslot.setError(null);

            updateUI();
        }
    };

    /**
     * Update the programslot with selected presenter or produce
     *
     * @param role         presenter or producer
     * @param selecteduser selected presenter or selected producer
     */
    public void selectedPresenterProducer(int role, User selecteduser) {
        if (programSlot == null) {
            programSlot = new ProgramSlot();
        }

        if (role == Constant.PRESENTER) {
            this.selectedPresenter = selecteduser;
            this.programSlot.setPresenter(this.selectedPresenter);
            textView_presenter.setError(null);
        } else if (role == Constant.PRODUCER) {
            this.selectedProducer = selecteduser;
            this.programSlot.setProducer(this.selectedProducer);
            textView_producer.setError(null);
        }

        updateUI();
    }

    /**
     * To validate whether the editText is filled with data
     *
     * @return true if all data have filled in
     */
    public boolean validation() {

        if (scheduleMode != Constant.DELETE) {
            if (textView_timeslot.getText().equals("")) {
                textView_timeslot.requestFocus();
                textView_timeslot.setError("No data");
                return false;
            } else {
                textView_timeslot.setError(null);
            }

            if (textView_presenter.getText().equals("")) {
                textView_presenter.requestFocus();
                textView_presenter.setError("No data");
                return false;
            } else {
                textView_presenter.setError(null);
            }

            if (textView_producer.getText().equals("")) {
                textView_producer.requestFocus();
                textView_producer.setError("No data");
                return false;
            } else {
                textView_producer.setError(null);
            }

            if (textView_radioprogram.getText().equals("")) {
                textView_radioprogram.requestFocus();
                textView_radioprogram.setError("No data");
                return false;
            } else {
                textView_radioprogram.setError(null);
            }

            if (textView_radioprogram_durationValue.getText().equals("")) {
                textView_radioprogram_durationValue.requestFocus();
                textView_radioprogram_durationValue.setError("No data");
                return false;
            } else {
                textView_radioprogram_durationValue.setError(null);
            }
        }

        return true;
    }

    /**
     * To display the error message
     *
     * @param errorMessage
     */
    public void displayError(String errorMessage) {
        Toast.makeText(ScheduleScreen.this, errorMessage, Toast.LENGTH_LONG).show();
    }
}

