package sg.edu.nus.iss.phoenix.createuser.android.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> userList = new ArrayList<User>();

    public UserListAdapter(Context context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    public int getCount() {
        return userList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }


    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_user_layout,parent,false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.username_text);
        textView.setText(userList.get(position).getUserName());

        addButtonAction(convertView,position);

        return convertView;
    }

    private void addButtonAction(final View convertview, final int position) {
        ImageButton optionBtn = (ImageButton) convertview.findViewById(R.id.editbutton_user);
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
        popup.getMenu().add(Menu.NONE, 2, 2, "DELETE");
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case 1:{ // edit
                        eidtUser(position);
                    }
                    break;
                    case 2:{ // add
                        addUser(position);
                    }
                    break;
                }
                return true;
            }
        });
        popup.show();
    }

    private void eidtUser(int p){
         System.out.print(p);
    }

    private void addUser(int p){
        System.out.print(p);
    }

}
