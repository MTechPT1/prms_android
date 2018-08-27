package sg.edu.nus.iss.phoenix.radioprogram.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

public class RadioProgramAdapter extends ArrayAdapter<RadioProgram> {

    public RadioProgramAdapter(@NonNull Context context,  ArrayList<RadioProgram> radioPrograms) {
        super(context, 0, radioPrograms);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_radio_program, parent, false);
        }
        //    Word currentWord = getItem(position);
        RadioProgram currentRP = getItem(position);

        EditText radioPMName = (EditText)listItemView.findViewById(R.id.maintain_program_name_text_view);
        radioPMName.setText(currentRP.getRadioProgramName(), TextView.BufferType.NORMAL);
        radioPMName.setKeyListener(null); // This disables editing.

        EditText radioPMDesc = (EditText)listItemView.findViewById(R.id.maintain_program_desc_text_view);
        radioPMDesc.setText(currentRP.getRadioProgramDescription(), TextView.BufferType.NORMAL);
        radioPMDesc.setKeyListener(null);

        EditText radioPMDuration = (EditText)listItemView.findViewById(R.id.maintain_program_duration_text_view);
        radioPMDuration.setText(currentRP.getRadioProgramDuration(), TextView.BufferType.NORMAL);
        radioPMDuration.setKeyListener(null);

        return listItemView;
    }
}
