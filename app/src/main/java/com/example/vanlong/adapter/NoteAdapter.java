package com.example.vanlong.adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.vanlong.models.Note;
import com.example.vanlong.noteapp.R;

import java.util.List;

/**
 * Created by vanlong on 4/13/2017.
 */

public class NoteAdapter extends ArrayAdapter<Note> {
    private  boolean mIShowCheckBook= false;
    private Activity context;
    private int resource;
    private List<Note> objects;
    public NoteAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Note> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(this.context).inflate(this.resource, parent, false);
        CheckBox cbCheck = (CheckBox) view.findViewById(R.id.cb_check);
        TextView txtTieuDe = (TextView) view.findViewById(R.id.txtTieuDe);
        TextView txtThoiGian = (TextView) view.findViewById(R.id.txtThoiGian);
        if(mIShowCheckBook) {
            cbCheck.setVisibility(View.VISIBLE);
        } else {
            cbCheck.setVisibility(View.INVISIBLE);
        }
        Note note = this.objects.get(position);
        txtTieuDe.setText(note.getmTitle());
        txtThoiGian.setText(note.getmDate());
        return view;
    }


    @Override
    public int getCount() {
        return this.objects.size();
    }

    public void setmIShowCheckBook(boolean mIShowCheckBook) {
        this.mIShowCheckBook = mIShowCheckBook;
    }
}
