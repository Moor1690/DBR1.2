package com.example.dbr12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dbr12.DataClass.AppDatabase;
import com.example.dbr12.DataClass.TimetableClassDao;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TimetableAdapter adapter;
    EditText editText, editText2;
    Button buttonAdd, buttonDelete;
    public static ArrayList<Timetable> timetables = new ArrayList<Timetable>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setInitialData();
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);


        RecyclerView recyclerView = findViewById(R.id.list);

        AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "timetable").build(); //создание бд
        new STAsyncTask().execute(db);



        adapter = new TimetableAdapter(this, timetables); //создание адаптера


        recyclerView.setAdapter(adapter); //установка адаптера

        //////////////////////////////////////////////////




        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.buttonAdd:
                        System.out.println("buttonAdd:");
                        new CreateAsyncTask().execute(db);
                        break;
                    case R.id.buttonDelete:
                        System.out.println("buttonDelete:");
                        new DeleteAsyncTask().execute(db);

                        break;
                }
            }
        };

        buttonDelete.setOnClickListener(onClickListener);
        buttonAdd.setOnClickListener(onClickListener);

    }




    private class CreateAsyncTask extends AsyncTask<AppDatabase,String,Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(AppDatabase... appDatabases) {

            System.out.println("CreateAsyncTask");

            EditText editText = findViewById(R.id.editText);
            //EditText editText2 = findViewById(R.id.editText2);
            TimetableClassDao timetableClassDao = appDatabases[0].timetableClassDao();
            Timetable timetable = new Timetable(editText.getText().toString()," "," ");
            timetableClassDao.insert(timetable);
            timetables.add(timetable);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<AppDatabase,String,Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }
        @Override
        protected Void doInBackground(AppDatabase... appDatabases) {
            System.out.println("DeleteAsyncTask");



            //int i = Integer.parseInt(editText.getText().toString());
            int i = Integer.parseInt(editText2.getText().toString());

            TimetableClassDao timetableClassDao = appDatabases[0].timetableClassDao();

            Timetable t = timetables.get(i-1);
            timetables.remove(i-1);

            timetableClassDao.delete(t);
            return null;
        }
    }

    private class STAsyncTask extends AsyncTask<AppDatabase,String,Void> {

        @Override
        protected Void doInBackground(AppDatabase... appDatabases) {

            timetables.addAll(appDatabases[0].timetableClassDao().getAll());

            System.out.println("STAsyncTask");


            System.out.println("Timetable.size  " + Timetable.size);
            System.out.println("\t" + appDatabases[0].timetableClassDao().getMax());

            //Timetable.size = timetables.size();
            System.out.println("Timetable.size  " + Timetable.size);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }

    }

    private void setInitialData(){
        //timetables.add(new Timetable("Обоснование и разработка требований к программным системам", "someone", "A-12"));
        //timetables.add(new Timetable("что-то", "кто-то", "где-то"));
        //timetables.add(new Timetable("Встраиваемые системы управления базами данными для мобильных приложений", "someone", "Г-301в"));
    }
}