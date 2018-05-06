package com.example.hannahbanana.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadWrite {

    public void Save(boolean isNew, Context context, Note note, String content) {
        File directory = context.getFilesDir();
        File[] files = directory.listFiles();
        String filename;

        if(isNew)
            filename = (files.length+1) + ".txt";
        else
            filename = note.getTitle();

        FileOutputStream outputStream;
        String contents = content;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(contents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String Open(String fileName, Context context) {
        String content = "";
        File file = context.getFileStreamPath(fileName);

        if (file.exists()) {
            try {
                InputStream in = context.openFileInput(fileName);
                if ( in != null) {
                    InputStreamReader tmp = new InputStreamReader( in );
                    BufferedReader reader = new BufferedReader(tmp);
                    String str;
                    StringBuilder buf = new StringBuilder();
                    while ((str = reader.readLine()) != null) {
                        buf.append(str + "\n");
                    } in .close();
                    content = buf.toString();
                }
            } catch (java.io.FileNotFoundException e) {

            } catch (Throwable t) {

            }
        }
        return content;
    }

    public List<Note> prepareNotes(Context context) {
        List<Note> listNote = new ArrayList<>();
        ReadWrite readwrite = new ReadWrite();
        File directory = context.getFilesDir();
        File[] files = directory.listFiles();
        String theFile;

        for (int f = 1; f <= files.length; f++) {
            theFile = f + ".txt";
            Note note = new Note(theFile, Open(theFile, context));
            listNote.add(note);
        }

        return listNote;
    }
}
