package sg.edu.nus.iss.phoenix.createuser.android.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;

public class MaintainUserAdapter extends BaseAdapter {

    private static final int TYPE_NAME = 0;
    private static final int TYPE_PASSWORD = 1;
    private static final int TYPE_DATE = 2;
    private static final int TYPE_ROLE = 3;

    private static final String PRESENTER = "Presenter";
    private static final String PRODUCER = "Producer";

    private static final Integer NUM_ITEMS = 3;

    private String[] roles = {PRESENTER,PRODUCER};

    private Context context;
    private User user = new User();
    Calendar calendar = Calendar.getInstance();


    public MaintainUserAdapter(Context context,  User user) {
        this.context = context;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getCount() {
        return NUM_ITEMS + roles.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position){
        if (position ==0){
            return TYPE_NAME;
        } else if (position == 1){
            return TYPE_PASSWORD;
        }else if (position == 2){
            return TYPE_DATE;
        }
        else {
            return TYPE_ROLE;
        }
    }

    public Object getItem(int position) {
        int type = getItemViewType(position);
        if (type == TYPE_NAME){
            return user.getUserName();
        }else if (type == TYPE_PASSWORD){
            return user.getPassWord();
        } else if (type == TYPE_DATE){
            return user.getJoinDate();
        } else{
             return roles[position-NUM_ITEMS];
        }
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        ViewHolder inputTextHolder = null;
//        ViewHolder passwordHolder = null;
        ViewHolder joindateHolder = null;
        if (convertView == null){
             switch (type){
                 case TYPE_NAME:{
                     convertView = LayoutInflater.from(context).inflate(
                             R.layout.item_edittext_layout,parent,false);
                     EditText editText = (EditText)convertView.findViewById(R.id.text_input_user);
                     editText.setHint("User Name...");
                     inputTextHolder = new ViewHolder(convertView,R.id.text_input_user);
                     monitorEdit(inputTextHolder,TYPE_NAME,position);
                     editText.setText(user.getUserName() !=null ? user.getUserName(): "");
                     convertView.setTag(inputTextHolder);
                 }
                 break;
                 case TYPE_PASSWORD:{
                     convertView = LayoutInflater.from(context).inflate(
                             R.layout.item_edittext_layout,parent,false);
                     EditText editText = (EditText)convertView.findViewById(R.id.text_input_user);
                     editText.setHint("Password...");
                     inputTextHolder = new ViewHolder(convertView,R.id.text_input_user);
                     monitorEdit(inputTextHolder,TYPE_PASSWORD,position);
                     editText.setText(user.getPassWord() !=null ? user.getPassWord(): "");
                     convertView.setTag(inputTextHolder);
                 }
                 break;
                 case TYPE_DATE:{
                     convertView = LayoutInflater.from(context).inflate(R.layout.item_startdate_layout,parent,false);
                     EditText editText = (EditText)convertView.findViewById(R.id.text_input_joindate);
                     joindateHolder = new ViewHolder(convertView,R.id.text_input_joindate);
                     moniterDateEdit(joindateHolder,TYPE_DATE,position);
                     convertView.setTag(joindateHolder);
                 }
                 break;
                 case TYPE_ROLE:{
                     convertView = LayoutInflater.from(context).inflate(
                             R.layout.item_roleselection_layout,parent,false);
                     CheckedTextView textView = (CheckedTextView) convertView.findViewById(R.id.role_selection_text);
                     textView.setText(roles[position-NUM_ITEMS]);
                     textView.setChecked(roleSelected(position));
                 }
                 break;
             }
        }else{
            switch (type){
                case TYPE_NAME:{
                    inputTextHolder = (ViewHolder)convertView.getTag();
                }
                break;
                case TYPE_PASSWORD:{
                    inputTextHolder = (ViewHolder)convertView.getTag();
                }
                break;
                case TYPE_DATE:{
                    joindateHolder = (ViewHolder)convertView.getTag();
                }
                break;
            }
        }

        switch (type){
            case TYPE_NAME:{
              //  inputTextHolder.editText.setText(user.getUserName() !=null ? user.getUserName(): "");
               // monitorEdit(usernameHolder,TYPE_NAME,position);
            }
            break;
            case TYPE_DATE:{
               joindateHolder.editText.setText(user.getJoinDate() !=null ? user.getJoinDate(): "");
               // moniterDateEdit(joindateHolder,TYPE_DATE,position);
            }
            break;
            case TYPE_PASSWORD:{
               // inputTextHolder.editText.setText(user.getPassWord() !=null ? user.getPassWord(): "");
              //  monitorEdit(passwordHolder,TYPE_PASSWORD,position);
            }
            break;
            case TYPE_ROLE:{
                CheckedTextView textView = (CheckedTextView) convertView.findViewById(R.id.role_selection_text);
                textView.setChecked(roleSelected(position));
            }
            break;
        }

        return convertView;
    }

    //Monitor check box selection
    protected void didSelectCheckbox(int postion){
        if (postion+1 == roles.length + NUM_ITEMS){
            user.setProducer(!user.isProducer());
        }else if (postion + 2 == roles.length + NUM_ITEMS){
            user.setPresenter(!user.isPresenter());
        }
    }

    // Moniter the date text view
    public void moniterDateEdit(final ViewHolder viewHolder, final int type, int position){

        switch (type){
            case TYPE_NAME:{
                viewHolder.editText.setText(user.getJoinDate()!=null?user.getUserName():"");
            }
            break;
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
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                user.setJoinDate(sdf.format(calendar.getTime()));
                viewHolder.editText.setText(user.getJoinDate());
               // notifyDataSetChanged();
            }

        };

        viewHolder.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(context, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    //Monitor the text changes in Edit text
    private void monitorEdit(ViewHolder holder, final int type, int position){

         holder.editText.clearFocus();
        if (holder.editText.getTag() instanceof TextWatcher) {
            //holder.editText.removeTextChangedListener((TextWatcher) holder.editText.getTag());
            holder.editText.addTextChangedListener(null);

        }
        switch (type){
            case TYPE_NAME:{
               holder.editText.setText(user.getUserName()!=null?user.getUserName():"");
            }
            break;
            case TYPE_PASSWORD:{
                holder.editText.setText(user.getPassWord()!=null?user.getPassWord():"");
            }
            break;
        }

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                switch (type) {
                    case TYPE_NAME: {
                        user.setUserName(!TextUtils.isEmpty(s) ? s.toString() : "");
                        //Toast.makeText(context, quizItemObj.getQuiz_title(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case TYPE_PASSWORD: {
                        user.setPassWord(!TextUtils.isEmpty(s) ? s.toString() : "");
                        //Toast.makeText(context, quizItemObj.getQuiz_title(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        };

        holder.editText.addTextChangedListener(watcher);
        holder.editText.setTag(watcher);
    }

    // check role selection.
    protected boolean roleSelected(int position){
       return position + 1 == NUM_ITEMS + roles.length ? user.isProducer(): user.isPresenter();
    }

    private class ViewHolder{
        private EditText editText;

        private ViewHolder(View convertView,int id) {
            editText = (EditText) convertView.findViewById(id);
        }
    }
}