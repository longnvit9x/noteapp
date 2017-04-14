package com.example.vanlong.noteapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vanlong.adapter.CustomListView;
import com.example.vanlong.adapter.NoteAdapter;
import com.example.vanlong.controllers.NoteController;
import com.example.vanlong.models.Note;
import com.example.vanlong.models.OnFragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by vanlong on 4/13/2017.
 */

public class FragmentListNote extends Fragment {
    ListView lvShow;
    private NoteAdapter mNoteAdapter;
    private NoteController mNoteControler;
    private CustomListView mCustomListView;
    List<Note> noteList;
    boolean isCreate = true;
    private OnFragmentManager mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_note, container, false);
        lvShow = (ListView) view.findViewById(R.id.lvShow);
        addControl();
        addEvent();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentManager) {
            mListener = (OnFragmentManager) context;
        } else {
            Log.e("Warning", "must implement onViewSelected");
        }
    }



    private void addEvent() {
        lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailNoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Note", noteList.get(position));
                intent.putExtra("Data", bundle);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        lvShow.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mCustomListView.getCount(); i++) {
                    lvShow.getChildAt(i).findViewById(R.id.cb_check).setVisibility(View.VISIBLE);
                }
                mListener.onDataSelected(2);
                return true;
            }
        });
    }

    private void addControl() {
        mNoteControler = new NoteController(getActivity());
        if (isCreate) {
            FileOutputStream fos = null;
            try {
                fos = getActivity().openFileOutput(MainActivity.FILENAME, MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            isCreate = false;
        }
        noteList = new ArrayList<>();

        noteList = mNoteControler.convertJsonStringToObject(mNoteControler.readFromFileInternal(MainActivity.FILENAME));
        mCustomListView = new CustomListView(this.getActivity(), R.layout.item_listview_note, noteList);
        lvShow.setAdapter(mCustomListView);
    }

    public void reloadData(Note note) {
        noteList.add(note);
        mCustomListView.notifyDataSetChanged();
        WriteData(noteList);
    }

    private void WriteData(List<Note> noteList) {
        String data = mNoteControler.convertObjectToJsonString(noteList);
        mNoteControler.writeToFileInternal(data, MainActivity.FILENAME);

    }

    public void deleteData() {

        for (int i = 0; i < noteList.size(); i++) {
            if (noteList.get(i).isSelected()) {
                noteList.remove(i);
            }
            mCustomListView.notifyDataSetChanged();
            WriteData(noteList);
        }
        if (noteList.size()==0){
            mListener.onDataSelected(1);
        }
    }

    public void cancelDeleteData() {
        for (int i = 0; i < mCustomListView.getCount(); i++) {
            lvShow.getChildAt(i).findViewById(R.id.cb_check).setVisibility(View.GONE);
            noteList.get(i).setSelected(false);
        }
    }
}
