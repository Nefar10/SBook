package com.example.sbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> myNotes = new ArrayList<String>();
    Boolean wasInit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!wasInit) {
            myNotes.add("Это моя первая заметка");
            myNotes.add("А здесь надо написать побольше.\nЭто моя вторая тестовая заметка.");
            myNotes.add("Это моя третяя заметка");
            myNotes.add("А здесь надо написать побольше.\nЭто моя четвертая тестовая заметка.");
            myNotes.add("Это моя пятая заметка");
            myNotes.add("А здесь надо написать побольше.\nЭто моя шестая тестовая заметка.");
            myNotes.add("Это моя седьмая заметка");
            myNotes.add("А здесь надо написать побольше.\nЭто моя восьмая тестовая заметка.");
            myNotes.add("Это моя девятая заметка");
            myNotes.add("А здесь надо написать побольше.\nЭто моя десятая тестовая заметка.");
            wasInit = true;
        }
        NestedScrollView mySView = findViewById(R.id.mainScrollView);
        LinearLayout mySLayout = new LinearLayout(this);
        mySLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 1; i <= myNotes.size(); i++) {
            TextView noteView = new TextView(this);
            noteView.setText(myNotes.get(i-1));
            Log.i("myInfo",noteView.getText().toString());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // ширина элемента
                LinearLayout.LayoutParams.WRAP_CONTENT // высота элемента
            );
            params.setMargins(20,20,20,20);
            noteView.setLayoutParams(params);
            noteView.setTextSize(20);
            noteView.setMinHeight(140);
            noteView.setTextColor(0xFF000000);
            noteView.setGravity(Gravity.CENTER_VERTICAL);
            noteView.setBackgroundResource(R.drawable.rectangle_2);
            mySLayout.addView(noteView);
        }
        mySView.addView(mySLayout);
    }
    /*
        <TextView

        android:onClick="editNote"/>
     */
}