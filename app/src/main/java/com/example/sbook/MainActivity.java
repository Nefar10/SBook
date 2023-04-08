package com.example.sbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> myNotes = new ArrayList<String>();
    Boolean wasInit = false;
    //Настройки
    Boolean set_big_text = false;
    Boolean set_hints = true;
    int set_color = 0xE6E100;
    int set_opacity = 0x88;
    private LinearLayout mySLayout;
    private NestedScrollView mySView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySView = findViewById(R.id.mainScrollView);
        mySLayout = new LinearLayout(this);
        mySLayout.setOrientation(LinearLayout.VERTICAL);
        mySView.addView(mySLayout);
        Log.i("myInfo", String.valueOf(wasInit));
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("notes",myNotes);
        outState.putBoolean("wasinit",wasInit);
        Log.i("myInfo", "savestate");

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        myNotes = savedInstanceState.getStringArrayList("notes");
        wasInit = savedInstanceState.getBoolean("wasinit");
        Log.i("myInfo", "loadstate");
        for (int i = 1; i <= myNotes.size(); i++) {
            TextView noteView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, // ширина элемента
                    LinearLayout.LayoutParams.WRAP_CONTENT // высота элемента
            );
            params.setMargins(20, 20, 20, 20);
            noteView.setLayoutParams(params);
            noteView.setId(i);
            noteView.setText(myNotes.get(i - 1));
            Log.i("myInfo", noteView.getText().toString());
            noteView.setTextSize(20);
            noteView.setMinHeight(140);
            noteView.setMaxHeight(300);
            noteView.setTextColor(0xFF000000);
            noteView.setGravity(Gravity.CENTER_VERTICAL);
            noteView.setBackgroundResource(R.drawable.rectangle_2);
            noteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Ну давай!\nНашипи ещё что-нибудь!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Intent add_note = new Intent(MainActivity.this, AddNoteActivity.class);
                    add_note.putExtra("id", noteView.getId());
                    add_note.putExtra("txt", noteView.getText());
                    startActivityForResult(add_note, 1);
                }
            });
            mySLayout.addView(noteView);
        }
 //       mySView.addView(mySLayout);
    }
    public void addClick (View ImageButton) {
        Toast toast = Toast.makeText(getApplicationContext(), "Ну давай!\nНашипи ещё что-нибудь!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        Intent add_note = new Intent(this,  AddNoteActivity.class);
        add_note.putExtra("id", myNotes.size()+1);
        add_note.putExtra("txt", "");
        startActivityForResult(add_note, 1);
    }
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent note) {
        super.onActivityResult(requestCode,resultCode, note);
        if (note == null) {
            return;
        }
        int id = note.getIntExtra("id",-1);
        if (id > -1 & id <= myNotes.size()) {
            Log.i("myInfo",String.valueOf(id));
            myNotes.set(id-1, note.getStringExtra("result"));
            TextView tVNotes = findViewById(id);
            tVNotes.setText(myNotes.get(id-1));
            if (myNotes.get(id-1).length() == 0) {
                myNotes.remove(id-1);
                mySLayout.removeView(tVNotes);
                Log.i("myInfo",String.valueOf(id));
                if (id <= myNotes.size()) {
                    for (int i = id; i <= myNotes.size(); i++) {
                        tVNotes = findViewById(i+1);
                        Log.i("myInfo",String.valueOf(i));
                        tVNotes.setId(i);
                    }
                }
            }
            return;
        }
        if (id > myNotes.size()) {
            myNotes.add(note.getStringExtra("result"));
            mySLayout.setOrientation(LinearLayout.VERTICAL);
            TextView noteView = new TextView(this);
            Log.i("myInfo", noteView.getText().toString());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // ширина элемента
                LinearLayout.LayoutParams.WRAP_CONTENT // высота элемента
            );
            params.setMargins(20, 20, 20, 20);
            noteView.setLayoutParams(params);
            noteView.setId(id);
            noteView.setText(myNotes.get(id - 1));
            noteView.setTextSize(20);
            noteView.setMinHeight(140);
            noteView.setMaxHeight(300);
            noteView.setTextColor(0xFF000000);
            noteView.setGravity(Gravity.CENTER_VERTICAL);
            noteView.setBackgroundResource(R.drawable.rectangle_2);
            noteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Ну давай!\nНашипи ещё что-нибудь!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Intent add_note = new Intent(MainActivity.this, AddNoteActivity.class);
                    add_note.putExtra("id", noteView.getId());
                    add_note.putExtra("txt", noteView.getText());
                    startActivityForResult(add_note, 1);
                }
            });
            mySLayout.addView(noteView);
        }
    }
    public void intro (View ImageButton) {
        Toast.makeText(getApplicationContext(), "Привет!\nЯ змея Барбара.\nМогу запомнить бешшшеное количество заметок!\nПроверим?", Toast.LENGTH_SHORT).show();
    }
}