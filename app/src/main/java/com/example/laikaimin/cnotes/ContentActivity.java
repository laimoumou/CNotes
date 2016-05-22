package com.example.laikaimin.cnotes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContentActivity extends AppCompatActivity {

    private EditText notesEdit;

    private String notesTitle,notesContent,notesTime,notesFlag;

    private Toolbar mToolbar;

    private NotesDatabaseHelper dbHelper;



    public static void actionStart(Context context,String notesTitle,String notesContent,String notesTime,String flag){
        Intent intent = new Intent(context,ContentActivity.class);
        intent.putExtra("notesTitle",notesTitle);
        intent.putExtra("notesContent",notesContent);
        intent.putExtra("notesTime",notesTime);
        intent.putExtra("notesFlag",flag);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        dbHelper = new NotesDatabaseHelper(ContentActivity.this,"CNotes.db",null,1);
        notesEdit = (EditText) findViewById(R.id.contentEdit);
        notesTitle = getIntent().getStringExtra("notesTitle");
        notesContent = getIntent().getStringExtra("notesContent");
        notesTime = getIntent().getStringExtra("notesTime");
        notesFlag = getIntent().getStringExtra("notesFlag");
        if(notesFlag.equals("false")){
            notesEdit.setText("");
        }else{
            notesEdit.setText(notesContent);
        }


        mToolbar = (Toolbar) findViewById(R.id.contentbar);
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
        menu.add(Menu.NONE, Menu.FIRST + 4, 1, "删除");
        menu.add(Menu.NONE, Menu.FIRST + 5, 1, "设置");
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case Menu.FIRST + 4:
                notesEdit.setText("");
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Notes", "title = ?", new String[]{notesTitle});
                break;
            case Menu.FIRST + 5:
                Toast.makeText(ContentActivity.this,"目前没有设置功能",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }



    @Override
    protected void onPause(){
        super.onPause();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String oldTitle = notesTitle;
        notesContent = notesEdit.getText().toString();
        if(!notesContent.equals("")) {
            if (notesContent.length() > 8) {
                notesTitle = notesContent.substring(0, 8);
            } else {
                notesTitle = notesContent;
            }
            long time = System.currentTimeMillis();
            notesTime = "" + time;
            values.put("title", notesTitle);
            values.put("content", notesContent);
            values.put("time", notesTime);
            db.delete("Notes", "title = ?", new String[]{oldTitle});
            db.insert("Notes", null, values);
        }
    }
}
