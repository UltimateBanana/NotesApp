package com.example.hannahbanana.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class NotesActivity extends AppCompatActivity {
    private EditText editContent;
    private ReadWrite readwrite;
    private boolean isNew;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarNotes);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        note = (Note) getIntent().getSerializableExtra("noteObject");
        FloatingActionButton fabNotes = (FloatingActionButton) findViewById(R.id.fabNotes);
        editContent = (EditText) findViewById(R.id.editContent);
        readwrite = new ReadWrite();
        isNew = true;

        if(note != null) {
            editContent.setText(note.getContent());
            isNew = false;
        }

        // Listeners
        fabNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("SAVED", "NotesActivity");
                String content = editContent.getText().toString();
                readwrite.Save(isNew, NotesActivity.this, note, content);
               //Toast.makeText(NotesActivity.this, editContent.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_delete:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    public void Save(boolean isNew) {
        File directory = getFilesDir();
        File[] files = directory.listFiles();
        String filename;

        if(isNew)
            filename = (files.length+1) + ".txt";
        else
            filename = note.getTitle();

        FileOutputStream outputStream;
        String contents = editContent.getText().toString();
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(contents.getBytes());
            outputStream.close();
            Toast.makeText(this, "Note saved! " + Open(filename), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String Open(String fileName) {
        String content = "";
        File file = getBaseContext().getFileStreamPath(fileName);

        if (file.exists()) {
            try {
                InputStream in = openFileInput(fileName);
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
            } catch (java.io.FileNotFoundException e) {} catch (Throwable t) {
                Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return content;
    }
    */
}
