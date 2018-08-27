package sg.edu.nus.iss.phoenix.radioprogram.android.ui;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

public class MaintainProgramScreen extends AppCompatActivity {
    // Tag for logging
    private static final String TAG = MaintainProgramScreen.class.getName();

    private EditText mRPNameEditText;
    private EditText mRPDescEditText;
    private EditText mDurationEditText;
    private RadioProgram rp2edit = null;
    KeyListener mRPNameEditTextKeyListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_program);

        // Find all relevant views that we will need to read user input from
        mRPNameEditText = (EditText) findViewById(R.id.maintain_program_name_text_view);
        mRPDescEditText = (EditText) findViewById(R.id.maintain_program_desc_text_view);
        mDurationEditText = (EditText) findViewById(R.id.maintain_program_duration_text_view);
        // Keep the KeyListener for name EditText so as to enable editing after disabling it.
        mRPNameEditTextKeyListener = mRPNameEditText.getKeyListener();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getProgramController().onDisplayProgram(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new radioprogram, hide the "Delete" menu item.
        if (rp2edit == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save radio program.
                if (rp2edit == null) { // Newly created.
                    Log.v(TAG, "Saving radio program " + mRPNameEditText.getText().toString() + "...");
                    RadioProgram rp = new RadioProgram(mRPNameEditText.getText().toString(),
                            mRPDescEditText.getText().toString(), mDurationEditText.getText().toString());
                    ControlFactory.getProgramController().selectCreateProgram(rp);
                }
                else { // Edited.
                    Log.v(TAG, "Saving radio program " + rp2edit.getRadioProgramName() + "...");
                    rp2edit.setRadioProgramDescription(mRPDescEditText.getText().toString());
                    rp2edit.setRadioProgramDuration(mDurationEditText.getText().toString());
                    ControlFactory.getProgramController().selectUpdateProgram(rp2edit);
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                Log.v(TAG, "Deleting radio program " + rp2edit.getRadioProgramName() + "...");
                ControlFactory.getProgramController().selectDeleteProgram(rp2edit);
                return true;
            // Respond to a click on the "Cancel" menu option
            case R.id.action_cancel:
                Log.v(TAG, "Canceling creating/editing radio program...");
                ControlFactory.getProgramController().selectCancelCreateEditProgram();
                return true;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Log.v(TAG, "Canceling creating/editing radio program...");
        ControlFactory.getProgramController().selectCancelCreateEditProgram();
    }

    public void createProgram() {
        this.rp2edit = null;
        mRPNameEditText.setText("", TextView.BufferType.EDITABLE);
        mRPDescEditText.setText("", TextView.BufferType.EDITABLE);
        mDurationEditText.setText("", TextView.BufferType.EDITABLE);
        mRPNameEditText.setKeyListener(mRPNameEditTextKeyListener);
    }

    public void editProgram(RadioProgram rp2edit) {
        this.rp2edit = rp2edit;
        if (rp2edit != null) {
            mRPNameEditText.setText(rp2edit.getRadioProgramName(), TextView.BufferType.NORMAL);
            mRPDescEditText.setText(rp2edit.getRadioProgramDescription(), TextView.BufferType.EDITABLE);
            mDurationEditText.setText(rp2edit.getRadioProgramDuration(), TextView.BufferType.EDITABLE);
            mRPNameEditText.setKeyListener(null);
        }
    }
}
