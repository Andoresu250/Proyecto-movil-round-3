package com.example.dell.round3.Activity.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dell.round3.FirebaseModels.Submit;
import com.example.dell.round3.R;

import java.util.ArrayList;

public class SubmitAdapter extends BaseAdapter {

    private ArrayList<Submit> submits;
    private Context context;

    public SubmitAdapter(ArrayList<Submit> submits, Context context) {
        this.submits = submits;
        this.context = context;
    }

    @Override
    public int getCount() {
        return submits.size();
    }

    @Override
    public Object getItem(int position) {
        return submits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Submit submit = submits.get(position);
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.row_submit, null);
        }
        TextView name = (TextView) view.findViewById(R.id.submitStudentNameTV);
        name.setText(submit.getStudentName());
        view.setTag(submit);
        return view;
    }
}
