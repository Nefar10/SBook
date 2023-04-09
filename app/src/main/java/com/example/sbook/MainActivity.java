package com.example.sbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
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
    //Настройки
    Boolean set_big_text = false;
    Boolean set_hints = true;
    int set_color = 0xE6E100;
    int set_opacity = 50;
    //Элементы активити
    private LinearLayout mySLayout;
    private NestedScrollView mySView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySView = findViewById(R.id.mainScrollView);                                                //Во время создания активити находим наш scrollview
        mySLayout = new LinearLayout(this);                                                  //Создаем linerlayout
        mySView.addView(mySLayout);                                                                 //Добавляем его в наш scrollview
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("notes",myNotes);                                               //Запоминаем все заметки
        outState.putBoolean("set_big_text",set_big_text);
        outState.putBoolean("set_hints",set_hints);
        outState.putInt("set_color",set_color);
        outState.putInt("set_opacity",set_opacity);
    }
    //Генерация блока с заметкой
    protected void createTViews (int id) {
        mySLayout.setOrientation(LinearLayout.VERTICAL);
        TextView noteView = new TextView(this);                                              //Создаем в контексте scrollview новый блок заметки
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,                                             // ширина элемента
                LinearLayout.LayoutParams.WRAP_CONTENT                                              // высота элемента
        );
        params.setMargins(20, 20, 20, 20);                                     //устанавливаем отступы
        noteView.setLayoutParams(params);                                                           //применяем отступы
        noteView.setId(id);                                                                         //устанавливаем id
        noteView.setText(myNotes.get(id - 1));                                                      //добавляем в блок текст заметки
        if (set_big_text) {noteView.setTextSize(20);}
        else {noteView.setTextSize(20);}                                                                   //устанавалием размер шрифта
        noteView.setMinHeight(140);                                                                 //устанавалием минимальную высоту блока
        noteView.setMaxHeight(300);                                                                 //устанавливаемм максимальную высоту блока
        noteView.setTextColor(0xFF000000);                                                          //устанаваливаем цвет текста заметки
        noteView.setGravity(Gravity.CENTER_VERTICAL);                                               //выравниваем текст заметки по центру вертикально
        //noteView.setBackgroundResource(R.drawable.rectangle_2);                                     //придаем блоку форму
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.rectangle_2);
        ColorFilter colorFilter = new PorterDuffColorFilter(0x1000000 * (set_opacity + 100)  + set_color, PorterDuff.Mode.SRC_IN);
        drawable.setColorFilter(colorFilter);
        noteView.setBackground(drawable);
        noteView.setOnClickListener(new View.OnClickListener() {                                    //добавляем обработчик нажатия
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "Ну давай!\nНашипи ещё что-нибудь!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);         //формируем и выводим мотивирующий тост
                if( v != null) v.setGravity(Gravity.CENTER);
                toast.show();
                Intent add_note = new Intent(MainActivity.this, AddNoteActivity.class);//создаем форму для редактирования заметки
                add_note.putExtra("id", noteView.getId());                                    //отправляем в форму id заметки
                add_note.putExtra("txt", noteView.getText());                                 //отправляем в форму текст заметки
                startActivityForResult(add_note, 1);                                     //открываем форму редактирования заметки
            }
        });
        mySLayout.addView(noteView);                                                                //добавляем подготовленный блок в scrollview
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        set_big_text = savedInstanceState.getBoolean("set_big_text");
        set_hints = savedInstanceState.getBoolean("set_hints");
        set_color = savedInstanceState.getInt("set_color");
        set_opacity = savedInstanceState.getInt("set_opacity");
        myNotes = savedInstanceState.getStringArrayList("notes");                               //восстанавливаем сохраненные ранее заметки
        for (int i = 1; i <= myNotes.size(); i++) {                                                 //пересоздаем scrollview с блоками заметок
            createTViews(i);
        }
    }
    public void addClick (View ImageButton) {
        Toast toast = Toast.makeText(getApplicationContext(), "Ну давай!\nНашипи ещё что-нибудь!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);                 //формируем и выводим мотивирующий тост
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
        Intent add_note = new Intent(this,  AddNoteActivity.class);                    //создаем форму для редактирования заметки
        add_note.putExtra("id", myNotes.size()+1);                                      //отправляем в форму id заметки
        add_note.putExtra("txt", "");                                                   //отправляем в форму текст заметки
        startActivityForResult(add_note, 1);                                             //открываем форму редактирования заметки
    }
    //обработка результата возвращенного из другого активити
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent note) {
        super.onActivityResult(requestCode,resultCode, note);
        if (note == null) {                                                                         //если не вернулись данные завершаем процедуру
            return;
        }
        if (requestCode == 1) {
            int id = note.getIntExtra("id", -1);                                    //забираем из данных id заметки
            if (id > -1 & id <= myNotes.size()) {                                                   //если заметка ранее существовала
                myNotes.set(id - 1, note.getStringExtra("result"));                           //обновляем ее содержимое отредактированными данными
                TextView tVNotes = findViewById(id);                                                //находим блок этой заметки
                tVNotes.setText(myNotes.get(id - 1));                                               //изменяем текст в блоке
                if (myNotes.get(id - 1).length() == 0) {                                            //если наш текст пустой
                    myNotes.remove(id - 1);                                                   //удаляем заметку
                    mySLayout.removeView(tVNotes);                                                  //удаляем блок
                    if (id <= myNotes.size()) {                                                     //если заметка не была последней в списке
                        for (int i = id; i <= myNotes.size(); i++) {                                //пробегаем по всем заметкам после нее
                            tVNotes = findViewById(i + 1);                                          //находим блоки
                            tVNotes.setId(i);                                                       //уменьшаем их id на единицу
                        }
                    }
                }
                return;
            }
            if (id > myNotes.size()) {                                                              //если у нас новая заметка
                myNotes.add(note.getStringExtra("result"));                                   //добавляем ее в заметки
                createTViews(id);                                                                   //создаем для нее блок
            }
        }
        if (requestCode == 2) {

            set_big_text = note.getBooleanExtra("set_big_text", false);
            set_hints = note.getBooleanExtra("set_hints",true);
            set_color = note.getIntExtra("set_color",0xE6E100);
            set_opacity = note.getIntExtra("set_opacity",50);
            Log.d("info", "bigtext: "+String.valueOf(set_big_text));
            Log.d("info", "hints: "+String.valueOf(set_hints));
            return;
        }
    }
    public void intro (View ImageButton) {
        Toast toast = Toast.makeText(getApplicationContext(), "Привет!\nЯ шшшмея Барбара." +
                "\nМогу запомнить бешшшеное количество заметок!\nПроверишшшь?", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    public void settings (View Imagebutton) {
        Intent sett = new Intent(this,  SettingsActivity.class);
        sett.putExtra("set_big_text", set_big_text);
        sett.putExtra("set_hints",set_hints);
        sett.putExtra("set_color",set_color);
        sett.putExtra("set_opacity",set_opacity);
        startActivityForResult(sett, 2);
    }
}