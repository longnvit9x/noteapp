package com.example.vanlong.controllers;

import android.content.Context;
import android.util.Log;

import com.example.vanlong.models.Note;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by vanlong on 4/13/2017.
 */

public class NoteController {
    private Context mContext;

    public NoteController(Context mContext) {
        this.mContext = mContext;
    }

    public void writeToFileInternal(String data, String filename) {

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = mContext.openFileOutput(filename, 0);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String convertObjectToJsonString(List<Note> noteList) {
        JSONArray objJsonArray = new JSONArray();
        JSONObject jsonObject;
        try {
            for (int i = 0; i < noteList.size(); i++) {
                jsonObject = new JSONObject();
                jsonObject.put("Id", noteList.get(i).getmId());
                jsonObject.put("Title", noteList.get(i).getmTitle());
                jsonObject.put("Content", noteList.get(i).getmContent());
                jsonObject.put("Date", noteList.get(i).getmDate());
                objJsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objJsonArray.toString();
    }

    public List<Note> convertJsonStringToObject(String strJson) {
        List<Note> noteList = new ArrayList<>();
        if (strJson != "") {
            Note note;
            JSONArray jsonarray;
            try {
                jsonarray = new JSONArray(strJson);
                for (int i = 0; i < jsonarray.length(); i++) {

                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    int id = Integer.parseInt(jsonobject.getString("Id"));
                    String tieude = jsonobject.getString("Title");
                    String noidung = jsonobject.getString("Content");
                    String thoigian = jsonobject.getString("Date");
                    note = new Note(id, tieude, noidung, thoigian);
                    noteList.add(note);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return noteList;
    }

    public String readFromFileInternal(String filename) {
        String data = "";

        try {

            InputStream inputStream = mContext.openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                data = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return data;
    }
}
