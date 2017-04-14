package com.example.vanlong.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.vanlong.models.Note;
import com.example.vanlong.noteapp.R;

import java.util.List;

/**
 * Created by vanlong on 4/14/2017.
 */

public class CustomListView extends ArrayAdapter<Note> {
    private List<Note> mNoteisList;
    private Activity mActivity;
    private int resource;

    public CustomListView(Activity context, int resource, List<Note> objects) {
        super(context, resource, objects);
        this.mNoteisList = objects;
        this.resource = resource;
        this.mActivity = context;
    }

    ViewHolder viewHolder = new ViewHolder();
    public class ViewHolder {
        private CheckBox cbCheck;
        private TextView txtTieuDe;
        private TextView txtThoiGian;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder holder = null;
        if (convertView == null) {

            convertView = LayoutInflater.from(mActivity).inflate(resource, null);
            holder = new ViewHolder();

            holder.cbCheck = (CheckBox) convertView.findViewById(R.id.cb_check);
            holder.txtTieuDe = (TextView) convertView.findViewById(R.id.txtTieuDe);
            holder.txtThoiGian = (TextView) convertView.findViewById(R.id.txtThoiGian);
            convertView.setTag(holder);
            holder.cbCheck.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Note note = (Note) cb.getTag();
                    note.setSelected(cb.isChecked());
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Note note = mNoteisList.get(position);
        holder.txtTieuDe.setText(note.getmTitle());
        holder.txtThoiGian.setText(note.getmDate());
        holder.cbCheck.setChecked(note.isSelected());
        holder.cbCheck.setTag(note);

        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPosition(Note item) {

        return super.getPosition(item);
    }


}
