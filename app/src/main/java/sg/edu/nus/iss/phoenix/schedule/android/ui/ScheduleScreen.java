package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

public class ScheduleScreen extends AppCompatActivity {

    //Variable for UI
    private ImageView button_timeslot;
    private ImageView button_Newtimeslot;
    private ImageView button_presenter;
    private ImageView button_producer;
    private ImageView button_radioProgram;
    private CardView cardView_NewTime;
    private Button button_schedule_procced;
    private TextView textView_timeslot;
    private TextView textView_timeslotForCopy;
    private TextView textView_presenter;
    private TextView textView_producer;
    private TextView textView_radioprogram;

    //Variable for Calender
    private Calendar calendar = Calendar.getInstance();

    //Variable for Logic
    private SimpleDateFormat sdf;
    private String selecteddate;
    private String selectedTime;
    private String selecteddate_copy_to;
    private String selectedTime_copy_to;
    private static User selectedUser;
    private static int selectedrole;
    private static int scheduleMode;
    //private Presenter selectedPresenter;
    //private Producer selectedProducer;
    private static ProgramSlot selectedPS;
    private boolean isNewDateForCopy = false;


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
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void setupView() {
        cardView_NewTime = (CardView) findViewById(R.id.CardView_NewTime);
        button_timeslot = (ImageView) findViewById(R.id.button_timeslot);
        button_presenter = (ImageView) findViewById(R.id.button_presenter);
        button_producer = (ImageView) findViewById(R.id.button_producer);
        button_radioProgram = (ImageView) findViewById(R.id.button_radioProgram);
        button_schedule_procced = (Button) findViewById(R.id.button_schedule_procced);
        textView_timeslot = (TextView) findViewById(R.id.textView_timeslot);
        textView_timeslotForCopy = (TextView) findViewById(R.id.textView_Newtimeslot);
        textView_radioprogram = (TextView) findViewById(R.id.textView_radioprogram);

        cardView_NewTime.setVisibility(View.GONE);
        button_timeslot.setVisibility(View.VISIBLE);
        button_presenter.setVisibility(View.VISIBLE);
        button_producer.setVisibility(View.VISIBLE);
        button_radioProgram.setVisibility(View.VISIBLE);
        button_schedule_procced.setVisibility(View.VISIBLE);



        button_timeslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNewDateForCopy = false;
                selectNewTimeSlot();
            }
        });

        button_presenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ControlFactory.getReviewSelectPresenterProducerController().startUseCase(1, "PresenterName");
            }
        });

        button_producer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ControlFactory.getReviewSelectPresenterProducerController().startUseCase(2, "ProducerName");
            }
        });

        button_radioProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getReviewSelectProgramController().startUseCase();
            }
        });

        switch (scheduleMode) {
            case Constant.CREATE:
                button_schedule_procced.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowAlertDialog("Are you sure you want to create this schedule?");
                    }
                });
                break;

            case Constant.MODIFY:
                button_schedule_procced.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowAlertDialog("Are you sure you want to make these change(s)?");
                    }
                });
                break;

            case Constant.COPY:
                isNewDateForCopy = true;
                cardView_NewTime.setVisibility(View.VISIBLE);
                button_Newtimeslot = (ImageView) findViewById(R.id.button_Newtimeslot);
                button_Newtimeslot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectNewTimeSlot();
                    }
                });
                ImageView button_Newtimeslot = (ImageView) findViewById(R.id.button_Newtimeslot);
                button_Newtimeslot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectNewTimeSlot();
                    }
                });
                button_schedule_procced.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowAlertDialog("Are you sure you want to copy this schedule?");
                    }
                });
                break;

            case Constant.DELETE:
                button_timeslot.setVisibility(View.INVISIBLE);
                button_presenter.setVisibility(View.INVISIBLE);
                button_producer.setVisibility(View.INVISIBLE);
                button_radioProgram.setVisibility(View.INVISIBLE);

                button_schedule_procced.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowAlertDialog("Are you sure you want to delete this schedule?");
                    }
                });
                break;
        }

    }

    public static void selectedUser(User u, int role) {
        selectedUser = u;
        selectedrole = role;
    }

    public static void selectedProgramSlot(ProgramSlot ps) {
        selectedPS = ps;
    }

    private void updateUI() {
        textView_timeslot.setText(selecteddate + " " + selectedTime);

        if (selectedrole == Constant.PRESENTER) {
            textView_presenter = (TextView) findViewById(R.id.textView_presenter);
            textView_presenter.setText(selectedUser.getUserName());
        } else if (selectedrole == Constant.PRODUCER) {
            textView_producer = (TextView) findViewById(R.id.textView_producer);
            textView_producer.setText(selectedUser.getUserName());
        }
    }

    private void ShowAlertDialog(String string) {
        //TODO populate the proper program slot that needs to be created/modified/deleted/copied

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ScheduleScreen.this);
        builder.setMessage(string)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        switch (scheduleMode) {
                            case Constant.CREATE:
                                ControlFactory.getMaintainScheduleController().createSchedule(selectedPS);
                                break;

                            case Constant.MODIFY:
                                ControlFactory.getMaintainScheduleController().modifySchedule(selectedPS);
                                break;

                            case Constant.COPY:
                                ControlFactory.getMaintainScheduleController().copySchedule(selectedPS);
                                break;

                            case Constant.DELETE:
                                ControlFactory.getMaintainScheduleController().deleteSchedule(selectedPS);
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

    private void selectNewTimeSlot() {
        showCalendar();
    }


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

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            sdf = new SimpleDateFormat(myFormat, Locale.UK);
            if (!isNewDateForCopy) {
                selecteddate = sdf.format(calendar.getTime());
            } else {
                selecteddate_copy_to = sdf.format(calendar.getTime());
            }

            selecetNewTime();
        }
    };

    private void selecetNewTime() {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        new TimePickerDialog(ScheduleScreen.this, time, mHour, mMinute, false).show();
    }

    final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            String myFormat = "hh:mm"; //In which you need put here
            sdf = new SimpleDateFormat(myFormat, Locale.UK);

            if (!isNewDateForCopy) {
                selectedTime = sdf.format(calendar.getTime());
                if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
                    selectedTime = selectedTime + "AM";
                } else if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
                    selectedTime = selectedTime + "PM";
                }
            } else {
                selectedTime_copy_to = sdf.format(calendar.getTime());
                if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
                    selectedTime_copy_to = selectedTime_copy_to + "AM";
                } else if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
                    selectedTime_copy_to = selectedTime_copy_to + "PM";
                }
                textView_timeslotForCopy.setText(selecteddate_copy_to + " " + selectedTime_copy_to);
            }


            updateUI();
        }
    };

}

