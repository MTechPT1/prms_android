package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ScheduleProgram;

public class ScheduleProgramAdapter extends ArrayAdapter<ScheduleProgram> {
    private Context context;
    ArrayList<ScheduleProgram> scheduleProgram;

    public ScheduleProgramAdapter(@NonNull Context context, ArrayList<ScheduleProgram> ScheduleProgram) {
        super(context, 0, ScheduleProgram);
        this.context = context;
        this.scheduleProgram = ScheduleProgram;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_scheduled_program_layout, parent, false);
        }

        ScheduleProgram currentSP = getItem(position);

        TextView textView_schedule_program = (TextView)listItemView.findViewById(R.id.textView_schedule_program);
        textView_schedule_program.setText("TimeSlot"+position);



        addButtonAction(listItemView,position);

        return listItemView;
    }

    private void addButtonAction(final View convertview, final int position) {
        ImageButton optionBtn = (ImageButton) convertview.findViewById(R.id.optionBtn);
        optionBtn.setTag(position);
        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = (int)v.getTag();
                addListOfOptionButtons(convertview, p);
            }
        });
    }

    private void addListOfOptionButtons(View v, final int position){
        PopupMenu popup = new PopupMenu(context, v.findViewWithTag(position), Gravity.CENTER);
        popup.getMenu().add(Menu.NONE, 1, 1, "EDIT");
        popup.getMenu().add(Menu.NONE, 2, 2, "COPY");
        popup.getMenu().add(Menu.NONE, 3, 3, "DELETE");
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case 1:{ // edit
                        editSchedule(position);
                    }
                    break;
                    case 2:{ // copy
                        copySchedule(position);
                    }
                    break;
                    case 3:{ // delete
                        deleteSchedule(position);
                    }
                    break;
                }
                return true;
            }
        });
        popup.show();
    }


    private void editSchedule(int p){
        ControlFactory.getReviewSelectScheduleController().setMaintainSchedule(Constant.EDIT);
    }

    private void copySchedule(int p){
        ControlFactory.getReviewSelectScheduleController().setMaintainSchedule(Constant.COPY);
    }

    private void deleteSchedule(int p){
        ControlFactory.getReviewSelectScheduleController().setMaintainSchedule(Constant.DELETE);
    }
}
