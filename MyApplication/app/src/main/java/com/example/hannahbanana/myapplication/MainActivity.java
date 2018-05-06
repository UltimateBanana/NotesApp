package com.example.hannahbanana.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Note> listNote = new ArrayList<>();
    private RecyclerView recyclerNotes;
    private NotesAdapter notesAdapter;
    private RecyclerView.LayoutManager notesLayoutManager;
    private Toolbar toolbar;
    private ReadWrite readwrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerNotes = (RecyclerView) findViewById(R.id.recyclerNotes);
        readwrite = new ReadWrite();

        // use a linear layout manager
        notesLayoutManager = new LinearLayoutManager(this);
        recyclerNotes.setLayoutManager(notesLayoutManager);

        // specify an adapter (see also next example)
        notesAdapter = new NotesAdapter(listNote);
        recyclerNotes.setItemAnimator(new DefaultItemAnimator());
        recyclerNotes.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerNotes.setAdapter(notesAdapter);

        // Populate RecyclerView
        prepareNotes();
        notesAdapter.notifyDataSetChanged();
        recyclerNotes.invalidate();

        // Listeners
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(i);
            }
        });

        recyclerNotes.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerNotes, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Note note = listNote.get(position);
                Toast.makeText(getApplicationContext(), note.getContent() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, NotesActivity.class);
                i.putExtra("noteObject", note);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void prepareNotes() {
        ReadWrite readwrite = new ReadWrite();
        File directory = getFilesDir();
        File[] files = directory.listFiles();
        String theFile;

        for (int f = 1; f <= files.length; f++) {
            theFile = f + ".txt";
            Note note = new Note(theFile, readwrite.Open(theFile, this));
            listNote.add(note);
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
}
