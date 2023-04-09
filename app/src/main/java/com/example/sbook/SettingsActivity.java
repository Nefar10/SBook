package com.example.sbook;

import static java.lang.Math.log;
import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

public class SettingsActivity extends AppCompatActivity {
    Boolean set_big_text = false;
    Boolean set_hints = true;
    int set_color = 0xE6E100;
    int set_opacity = 50;

    TextView tVNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            set_big_text = bundle.getBoolean("set_big_text");
            set_hints = bundle.getBoolean("set_hints");
            set_color = bundle.getInt("set_color");
            set_opacity = bundle.getInt("set_opacity");
            SwitchCompat bigText = (SwitchCompat) findViewById(R.id.sw_big_text);
            Slider colorSlider = findViewById(R.id.sw_color);
            colorSlider.setValueFrom(0);
            colorSlider.setValueTo(0xFFFFFF);
            colorSlider.setStepSize(1);
            colorSlider.setValue(set_color);
            CheckBox hints = (CheckBox) findViewById(R.id.cb_hints);
            SeekBar opacity = (SeekBar) findViewById(R.id.sb_opacity);
            bigText.setChecked(set_big_text);
            tVNotes = findViewById(R.id.tV_note);
            bigText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        set_big_text = true;
                        tVNotes.setTextSize(30);
                        if (set_hints) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Сочувствую\nЯ тоже плохо вижу!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);                 //формируем и выводим мотивирующий тост
                            if (v != null) v.setGravity(Gravity.CENTER);
                            toast.show();
                        }
                    } else {
                        set_big_text = false;
                        tVNotes.setTextSize(20);
                        if (set_hints) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Очень рада\nза твоё зрение!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);                 //формируем и выводим мотивирующий тост
                            if (v != null) v.setGravity(Gravity.CENTER);
                            toast.show();
                        }
                    }
                }
            });
            hints.setChecked(set_hints);
            hints.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        set_hints = true;
                        Toast toast = Toast.makeText(getApplicationContext(), "Люблю подсказывать!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);                 //формируем и выводим мотивирующий тост
                        if( v != null) v.setGravity(Gravity.CENTER);
                        toast.show();
                    } else {
                        set_hints = false;
                        Toast toast = Toast.makeText(getApplicationContext(), "Хозяин - барин!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);                 //формируем и выводим мотивирующий тост
                        if( v != null) v.setGravity(Gravity.CENTER);
                        toast.show();
                    }
                }
            });
            opacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    set_opacity = seekBar.getProgress();
                    changeBoxColor();
                    // Обработчик изменений в SeekBar
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Обработчик начала перемещения ползунка в SeekBar
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (set_hints) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Ок!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);                 //формируем и выводим мотивирующий тост
                        if (v != null) v.setGravity(Gravity.CENTER);
                        toast.show();
                    }
                }

            });
            opacity.setProgress(set_opacity);
            colorSlider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(Slider slider, float value, boolean fromUser) {
                    set_color = round(value);
                    changeBoxColor();
                }
            });
        }
    }
    public void changeBoxColor () {
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_2);
        ColorFilter colorFilter = new PorterDuffColorFilter(0x1000000 * (set_opacity + 100)  + set_color, PorterDuff.Mode.SRC_IN);
        drawable.setColorFilter(colorFilter);
        tVNotes.setBackground(drawable);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("set_big_text",set_big_text);
        outState.putBoolean("set_hints",set_hints);
        outState.putInt("set_color",set_color);
        outState.putInt("set_opacity",set_opacity);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        set_big_text = savedInstanceState.getBoolean("set_big_text");
        set_hints = savedInstanceState.getBoolean("set_hints");
        set_color = savedInstanceState.getInt("set_color");
        set_opacity = savedInstanceState.getInt("set_opacity");
    }
    public void accept (View ImageButton) {
        Intent sett = new Intent();
        sett.putExtra("set_big_text", set_big_text);
        sett.putExtra("set_hints", set_hints);
        sett.putExtra("set_color",set_color);
        sett.putExtra("set_opacity",set_opacity);
        setResult(RESULT_OK,sett);
        finish();
    }
    public void cancel (View ImageButton) {
        Intent sett = new Intent();
        setResult(RESULT_OK,sett);
        finish();
    }
}