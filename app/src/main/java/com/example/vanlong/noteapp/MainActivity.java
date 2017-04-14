package com.example.vanlong.noteapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vanlong.controllers.FragmentController;
import com.example.vanlong.models.OnFragmentManager;

public class MainActivity extends AppCompatActivity implements OnFragmentManager {
    public static final String IS_HUY = "isHuy";
    public static final String FILENAME = "note.txt";
    private TextView txtTitle;
    public Button btnNew, btnXoa, btnHuy, btnSua;
    public static final FragmentListNote fragmentListNote = new FragmentListNote();
    public static final FragmentNewNote fragmentNewNote = new FragmentNewNote();
    private FragmentController mFragmentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNew = (Button) findViewById(R.id.btnNew);
        btnSua = (Button) findViewById(R.id.btnSua);
        btnXoa = (Button) findViewById(R.id.btnDelete);
        btnHuy = (Button) findViewById(R.id.btnCancel);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        addData();
        addEvent();
    }


    private void addEvent() {
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentController.hideFragmentContent(fragmentListNote);
                mFragmentController.addFragmentContent(R.id.fragment_container, fragmentNewNote);
                isFragmenNewShow();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Cancel fragment new note
                mFragmentController.removeFragmentContent(fragmentNewNote);
                mFragmentController.showFragmentContent(fragmentListNote);
                // Cancel process deleteData
                fragmentListNote.cancelDeleteData();
                isFragmenListNoteShow();


            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListNote.deleteData();
            }
        });


    }

    private void addData() {
        mFragmentController = new FragmentController(this);
        mFragmentController.addFragmentContent(R.id.fragment_container, fragmentListNote);
    }

    @Override
    public void onDataSelected(int data) {
        if (data == 1) {
            isFragmenListNoteShow();
        } else if (data == 2) {
            isProcessDeleteShow();
        }
    }

    public void isFragmenListNoteShow() {
        btnNew.setVisibility(View.VISIBLE);
        btnXoa.setVisibility(View.INVISIBLE);
        btnHuy.setVisibility(View.INVISIBLE);
    }

    public void isFragmenNewShow() {
        btnXoa.setVisibility(View.INVISIBLE);
        btnHuy.setVisibility(View.VISIBLE);
        btnNew.setVisibility(View.INVISIBLE);
    }
    public void isProcessDeleteShow(){
        btnXoa.setVisibility(View.VISIBLE);
        btnHuy.setVisibility(View.VISIBLE);
        btnNew.setVisibility(View.INVISIBLE);
    }
}

