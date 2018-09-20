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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.entity.Presenter;
import sg.edu.nus.iss.phoenix.schedule.android.entity.Producer;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ScheduleProgram;

public class ReviewSelectScheduleProgramScreen extends AppCompatActivity {
    // Tag for logging
    private static final String TAG = ReviewSelectScheduleProgramScreen.class.getName();

    private ScheduleProgramAdapter mSPAdapter;
    private ListView mListView;
    private ScheduleProgram selectedSP = null;
    private FloatingActionButton floatingActionButton2;
    private SimpleDateFormat selecteddate;
    private Calendar calendar = Calendar.getInstance();
    private RetrieveScheduleDelegate retrieveScheduleDel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_schedule_program);

        setupFloatingButton();
        setupScheduleProgramAdapter();



        //Testing Dummy schedules
        RadioProgram rp = new RadioProgram("radioProgramName", "radioProgramDescription", "radioProgramDuration");
        Presenter presenter = new Presenter(12,"name","address","employmentDate");
        Producer producer = new Producer(234,"name","address","employmentDate");
        ProgramSlot ps = new ProgramSlot(123, rp, presenter, producer, 10, "WK", "1200",456);
        ArrayList<ProgramSlot> pslist = new ArrayList<ProgramSlot>();
        pslist.add(ps);
        pslist.add(ps);
        pslist.add(ps);
        ScheduleProgram schedulePrograms = new ScheduleProgram(pslist);
        ArrayList<ScheduleProgram> sclist = new ArrayList<ScheduleProgram>();
        sclist.add(schedulePrograms);
        sclist.add(schedulePrograms);
        sclist.add(schedulePrograms);
        sclist.add(schedulePrograms);
        DisplayScheduleProgram(sclist);
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

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            selecteddate = new SimpleDateFormat(myFormat, Locale.UK);

            retrieveScheduleProgram();
        }

    };

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void DisplayScheduleProgram(List<ScheduleProgram> schedulePrograms) {
        mSPAdapter.clear();
        for (int i = 0; i < schedulePrograms.size(); i++) {
            mSPAdapter.add(schedulePrograms.get(i));
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
        ArrayList<ScheduleProgram> schedulePrograms = new ArrayList<ScheduleProgram>();
        mSPAdapter = new ScheduleProgramAdapter(this, schedulePrograms);

        mListView = (ListView) findViewById(R.id.maintain_schedule_list);
        mListView.setAdapter(mSPAdapter);

        // Setup the item selection listener
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ScheduleProgram sp = (ScheduleProgram) adapterView.getItemAtPosition(position);
                selectedSP = sp;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // your stuff
            }
        });
    }

    private void ShowAlertDialog(String string) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ReviewSelectScheduleProgramScreen.this);
        builder.setMessage(string)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showCalendar();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ReviewSelectScheduleProgramScreen.this.finish();
                    }
                });
        builder.create();
        builder.show();
    }

    private void retrieveScheduleProgram(){
        retrieveScheduleDel = new RetrieveScheduleDelegate(ControlFactory.getReviewSelectScheduleController());
        retrieveScheduleDel.execute();
    }
}

