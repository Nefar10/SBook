package com.example.sbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity {
    EditText etText;
    int id = -1;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            text = bundle.getString("txt");
            id = bundle.getInt("id");
            etText = (EditText) findViewById(R.id.et_text);
            etText.setText(text);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id", id);
        outState.putString("txt",text);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        id = savedInstanceState.getInt("id");
        text = savedInstanceState.getString("txt");
    }
    public void accept (View ImageButton) {
        text = etText.getText().toString();
        if (text.length() > 0 && id > -1) {
            Intent add_note = new Intent();
            add_note.putExtra("result",text);
            add_note.putExtra("id",id);
            setResult(RESULT_OK,add_note);
            finish();
        }
    }
    public void cancel (View ImageButton) {
        Intent add_note = new Intent();
        add_note.putExtra("result",text);
        add_note.putExtra("id",-1);
        setResult(RESULT_OK,add_note);
        finish();
    }
    public void delete (View ImageButton) {
        Intent add_note = new Intent();
        add_note.putExtra("result","");
        add_note.putExtra("id",id);
        setResult(RESULT_OK,add_note);
        finish();
    }
}