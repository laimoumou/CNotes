package com.example.laikaimin.cnotes;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Notes> notesList = new ArrayList<Notes>();



    private GridView notesView;

    private Toolbar mToolbar;


    private NotesAdapter notesAdapter;

    private NotesDatabaseHelper notesDatabaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesDatabaseHelper = new NotesDatabaseHelper(MainActivity.this,"CNotes.db",null,1);
        initNotes();
        notesAdapter = new NotesAdapter(MainActivity.this,R.layout.boxview,notesList);
        notesView = (GridView) findViewById(R.id.notesView);
        notesView.setAdapter(notesAdapter);

        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentTime = notesList.get(position).getTime();
                SQLiteDatabase database = notesDatabaseHelper.getWritableDatabase();
                Cursor cursor = database.query("Notes",new String[]{"title","content","time"},"time = ?",new String[]{currentTime},null,null,null);
                if(cursor.moveToFirst()){
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    ContentActivity.actionStart(MainActivity.this,title,content,currentTime,"true");
                }
            }
        });



        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("CNotes");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.thetab);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "添加");
        menu.add(Menu.NONE, Menu.FIRST + 2, 1, "设置");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case Menu.FIRST + 1:
                ContentActivity.actionStart(MainActivity.this, "null", "null", "null", "false");
                break;
            case Menu.FIRST + 2:
                Toast.makeText(MainActivity.this,"目前没有设置功能",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    private void initNotes(){
        SQLiteDatabase db = notesDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query("Notes",null,null,null,null,null,null);
        notesList.clear();
        if(cursor.moveToFirst()){
            do{
                String notesTitle = cursor.getString(cursor.getColumnIndex("title"));
                String notesContent = cursor.getString(cursor.getColumnIndex("content"));
                String notesTime = cursor.getString(cursor.getColumnIndex("time"));
                if(notesContent.length() > 25) {
                    notesContent = notesContent.substring(0, 25);
                }
                Notes notes = new Notes(notesTitle,notesContent,notesTime);
                notesList.add(0,notes);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }






    @Override
    protected void onRestart(){
        super.onRestart();
        initNotes();
        notesAdapter.notifyDataSetChanged();


    }



}
