package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ScheduleProgram;

public class PresenterProducerScreen extends AppCompatActivity {
    // Tag for logging
    private static final String TAG = PresenterProducerScreen.class.getName();

    private ScheduleProgramAdapter mSPAdapter;
    private ListView mListView;
    private ScheduleProgram selectedSP = null;
    private FloatingActionButton floatingActionButton2;


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

        //ControlFactory.getReviewSelectScheduleController().onDisplay(ReviewSelectScheduleProgramScreen.this);
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
            case R.id.action_select:
                if (selectedSP == null) {
                    // Prompt for the selection of a radio program.
                    Toast.makeText(this, "Select a radio program first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no selected radio program.");
                }
                else {
                    //Log.v(TAG, "Selected radio program: " + selectedSP.getRadioProgramName() + "...");
                    //ControlFactory.getReviewSelectProgramController().selectProgram(selectedSP);
                }
        }

        return true;
    }


    public void showPrograms(List<ScheduleProgram> schedulePrograms) {
        mSPAdapter.clear();
        //for (int i = 0; i < ScheduleProgram.size(); i++) {
        //    mSPAdapter.add(ScheduleProgram.get(i));
        //}
    }

    private void setupFloatingButton(){
        floatingActionButton2 = (FloatingActionButton)findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setupScheduleProgramAdapter(){
        ArrayList<ScheduleProgram> schedulePrograms = new ArrayList<ScheduleProgram>();
        mSPAdapter = new ScheduleProgramAdapter(this, schedulePrograms);

        mListView = (ListView) findViewById(R.id.maintain_schedule_list);
        mListView.setAdapter(mSPAdapter);

        // Setup the item selection listener
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Log.v(TAG, "Radio program at position " + position + " selected.");
                ScheduleProgram sp = (ScheduleProgram) adapterView.getItemAtPosition(position);
                // Log.v(TAG, "Radio program name is " + rp.getRadioProgramName());
                selectedSP = sp;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // your stuff
            }
        });
    }
}

