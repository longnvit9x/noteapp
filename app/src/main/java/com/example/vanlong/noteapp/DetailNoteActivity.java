package com.example.vanlong.noteapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vanlong.controllers.NoteController;
import com.example.vanlong.models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanlong on 4/13/2017.
 */

public class DetailNoteActivity extends Activity {
    private TextView txtTitle, txtThoiGian;
    private EditText txtNoDung;
    private Button btnXoa, btnSua;
    private boolean reslf = false;
    private NoteController mNoteController;
    private List<Note> noteList;
    Note note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        txtTitle = (EditText) findViewById(R.id.txtTT);
        txtThoiGian = (TextView) findViewById(R.id.txtDateTime);
        txtNoDung = (EditText) findViewById(R.id.txtND);
        btnXoa = (Button) findViewById(R.id.btnXoa);
        btnSua = (Button) findViewById(R.id.btnSua);
        getDataD();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void addEvent() {

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote(noteList);
                Intent intent = new Intent(DetailNoteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(DetailNoteActivity.this);
                builder.setMessage("Bạn có thực sự muốn xóa hay không?")
                        .setCancelable(false)
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteNote(noteList);
                                Intent intent = new Intent(DetailNoteActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                return;
                            }
                        });
                builder.create().show();
            }

        });
    }

    private void updateNote(List<Note> noteList) {
        for (int i = 0; i < noteList.size(); i++) {
            if (noteList.get(i).getmId() == note.getmId()) {
                noteList.get(i).setmTitle(txtTitle.getText().toString());
                noteList.get(i).setmContent(txtNoDung.getText().toString());
                noteList.get(i).setmDate(txtThoiGian.getText().toString());
            }
            reloadData(noteList);
        }

    }

    private void deleteNote(List<Note> noteList) {
        for (int i = 0; i < noteList.size(); i++) {
            if (noteList.get(i).getmId() == note.getmId()) {
                noteList.remove(i);
            }
            reloadData(noteList);
        }
    }

    private void reloadData(List<Note> noteList) {
        String data = mNoteController.convertObjectToJsonString(noteList);
        mNoteController.writeToFileInternal(data, MainActivity.FILENAME);
    }

    private void getDataD() {
        mNoteController = new NoteController(this);
        noteList = mNoteController.convertJsonStringToObject(mNoteController.readFromFileInternal(MainActivity.FILENAME));
        note = (Note) getIntent().getBundleExtra("Data").getSerializable("Note");
        txtNoDung.setText(note.getmContent());
        txtThoiGian.setText(note.getmDate());
        txtTitle.setText(note.getmTitle());

    }

}
