package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;

/**
 * <p><b>PresenterProducerAdapter</b> Adapater of the Users</p>
 *
 * @author: Wai Kin
 */
public class PresenterProducerAdapter extends ArrayAdapter<User> {
    private Context context;
    ArrayList<User> users;

    /**
     * Constructor of the PreseneterProducerAdapter
     *
     * @param context context
     * @param users   list of users
     */
    public PresenterProducerAdapter(@NonNull Context context, ArrayList<User> users) {
        super(context, 0, users);
        this.context = context;
        this.users = users;
    }

    /**
     * getView() for ArrayAdapter
     *
     * @param position    position in the list
     * @param convertView view
     * @param parent      viewGroup
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_presenter_producer_layout, parent, false);
        }

        TextView textView_schedule_program = (TextView) listItemView.findViewById(R.id.textview_userName);
        textView_schedule_program.setText(this.users.get(position).getUserName());


        return listItemView;
    }


}
