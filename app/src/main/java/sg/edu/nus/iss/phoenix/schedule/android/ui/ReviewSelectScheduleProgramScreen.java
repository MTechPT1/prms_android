package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ScheduleProgram;

public class ReviewSelectScheduleProgramScreen extends AppCompatActivity implements ScheduleProgramAdapter.ModifyScheduleListener {
    // Tag for logging
    private static final String TAG = ReviewSelectScheduleProgramScreen.class.getName();

    //Variable for UI
    private ScheduleProgramAdapter mSPAdapter;
    private ListView mListView;
    private FloatingActionButton floatingActionButton2;

    //Variable for Calender
    private SimpleDateFormat sdf;
    private String selecteddate;
    private Calendar calendar = Calendar.getInstance();
    private int weekId = 1;
    private int year = 2018;

    //Variable for delegate
    private RetrieveScheduleDelegate retrieveScheduleDel;

    //Variable for logic
    private ScheduleProgram schedulePrograms;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_schedule_program);

        setupFloatingButton();
        setupScheduleProgramAdapter();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);

        ShowAlertDialog("Please select a date to review...");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_view_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_view_schedule:
                showCalendar();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private void test() {

        //Testing Dummy schedules
        RadioProgram rp1 = new RadioProgram("Symphony92.4", "Symphony", "30");
        RadioProgram rp2 = new RadioProgram("Yes93.3", "Best", "30");
        User presenter = new User();
        presenter.setProducer(true);
        presenter.setPresenter(true);
        presenter.setUserId("1");
        presenter.setUserName("WK");
        presenter.setJoinDate("1 Jan");
        User producer = new User();
        producer.setProducer(true);
        producer.setPresenter(true);
        producer.setUserId("1");
        producer.setUserName("WK");
        producer.setJoinDate("1 Jan");
        ProgramSlot ps = new ProgramSlot(1, rp1, presenter, producer, 10, "WK", "1200", 1);
        ProgramSlot ps2 = new ProgramSlot(2, rp2, presenter, producer, 20, "WK2", "1200", 2);
        ArrayList<ProgramSlot> pslist = new ArrayList<>();
        pslist.add(ps);
        pslist.add(ps2);
        ScheduleProgram schedulePrograms = new ScheduleProgram(pslist);

        displayScheduleProgram(schedulePrograms);
    }

    private void showCalendar() {
        new DatePickerDialog(ReviewSelectScheduleProgramScreen.this, date, calendar
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

            weekId = calendar.get(Calendar.WEEK_OF_YEAR);
            year = calendar.get(Calendar.YEAR);

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            sdf = new SimpleDateFormat(myFormat, Locale.UK);
            selecteddate = sdf.format(calendar.getTime());
            retrieveScheduleProgram();

            //WK: Dummy Schedule
            //test();
        }

    };

    public void displayScheduleProgram(ScheduleProgram schedulePrograms) {
        this.schedulePrograms = schedulePrograms;
        if(this.schedulePrograms != null){
            if(this.schedulePrograms.getProgramSlots() != null){
                mSPAdapter.clear();
                for (int i = 0; i < schedulePrograms.getProgramSlots().size(); i++) {
                    mSPAdapter.add(schedulePrograms.getProgramSlots().get(i));
                }
                mSPAdapter.notifyDataSetChanged();
            }
        }
    }

    private void setupFloatingButton() {
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getReviewSelectScheduleController().selectCreateSchedule(Constant.CREATE);
            }
        });
    }

    private void setupScheduleProgramAdapter() {
        ArrayList<ProgramSlot> PS = new ArrayList<>();
        mSPAdapter = new ScheduleProgramAdapter(this, PS, this);

        mListView = (ListView) findViewById(R.id.maintain_schedule_list);
        mListView.setAdapter(mSPAdapter);

    }

    private void ShowAlertDialog(String string) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ReviewSelectScheduleProgramScreen.this);
        builder.setMessage(string)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showCalendar();
                    }
                });
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        ReviewSelectScheduleProgramScreen.this.finish();
//                    }
//                });
        builder.create();
        builder.show();
    }

    private void retrieveScheduleProgram() {
        ControlFactory.getReviewSelectScheduleController().retrieveScheduleProgram(ReviewSelectScheduleProgramScreen.this, String.valueOf(weekId), String.valueOf(year));
    }

    @Override
    public void editSchedule(int position) {
        ControlFactory.getReviewSelectScheduleController().setMaintainSchedule(Constant.MODIFY, schedulePrograms.getProgramSlots().get(position));
    }

    @Override
    public void copySchedule(int position) {
        ControlFactory.getReviewSelectScheduleController().setMaintainSchedule(Constant.COPY, schedulePrograms.getProgramSlots().get(position));
    }

    @Override
    public void deleteSchedule(int position) {
        ControlFactory.getReviewSelectScheduleController().setMaintainSchedule(Constant.DELETE, schedulePrograms.getProgramSlots().get(position));
    }
}

