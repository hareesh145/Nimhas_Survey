package com.ganesh.nimhans.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.ganesh.nimhans.CallBackListner;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.model.ViewUserResponse;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<ViewUserResponse> list = new ArrayList<ViewUserResponse>();
    private Context context;
    CallBackListner callBackListner;


    public MyCustomAdapter(ArrayList<ViewUserResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_view_user, null);
        }

        TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
        TextView list_item_id = (TextView) view.findViewById(R.id.list_item_id);
        list_item_id.setText("" + (position + 1));
        listItemText.setText(list.get(position).getName());

        ImageView deleteBtn = (ImageView) view.findViewById(R.id.delete_btn);
        ImageView addBtn = (ImageView) view.findViewById(R.id.add_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
//                list.remove(position); //or some other task
//                notifyDataSetChanged();

                if (callBackListner != null)
                    callBackListner.onButtonClickListner(position, 1);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBackListner != null)
                    callBackListner.onButtonClickListner(position, 2);
            }
        });

        return view;
    }
    public void setCallBackListner(CallBackListner callBackListner) {
        this.callBackListner = callBackListner;
    }
}
