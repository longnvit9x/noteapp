package com.example.vanlong.noteapp;

import android.app.Fragment;
import android.content.Context;
import android.icu.text.DateTimePatternGenerator;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.vanlong.controllers.FragmentController;
import com.example.vanlong.models.Note;
import com.example.vanlong.models.OnFragmentManager;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vanlong on 4/13/2017.
 */

public class FragmentNewNote extends Fragment {
    TextView txtTieuDe, txtNoiDung, txtDateTime;
    Button btnThem;
    int idcount = 0;
    private FragmentController mFragmentController;
    private OnFragmentManager mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_note, container, false);
        txtTieuDe = (TextView) view.findViewById(R.id.txtThemTieuDe);
        txtNoiDung = (TextView) view.findViewById(R.id.txtNoiDung);
        btnThem = (Button) view.findViewById(R.id.btnThem);
        addControl();
        addEvent();
        return view;
    }

    private void addEvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                String s = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a").format(calendar.getTime());
                }
                Note note = new Note(idcount, txtTieuDe.getText().toString(), txtNoiDung.getText().toString(), s);
                idcount++;
                mFragmentController.removeFragmentContent(MainActivity.fragmentNewNote);
                mListener.onDataSelected(1);
                mFragmentController.showFragmentContent(MainActivity.fragmentListNote);
                reloadData(MainActivity.fragmentListNote, note);
                //new MainActivity().btnHuy.setVisibility(View.INVISIBLE);
                // new MainActivity().btnNew.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addControl() {
        mFragmentController = new FragmentController(getActivity());
    }

    public void reloadData(Fragment fragment, Note note) {
        if (fragment instanceof FragmentListNote) {
            ((FragmentListNote) fragment).reloadData(note);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentManager){
            mListener= (OnFragmentManager ) context;
        } else {
           Log.e("Warning", "must implement onViewSelected");
        }
    }

}
