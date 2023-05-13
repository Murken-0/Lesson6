package ru.mirea.muravievvr.employeedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = App.getInstance().getDatabase();
        SuperheroDao superheroDao = db.employeeDao();
        Superhero superhero = new Superhero();
        superhero.id = 1;
        superhero.name = "Batman";
        superhero.power = "black color";
        superheroDao.insert(superhero);
        List<Superhero> superheroes = superheroDao.getAll();
        superhero = superheroDao.getById(1);
        superhero.power = "Money";
        superheroDao.update(superhero);
        Log.d(TAG, superhero.name + " " + superhero.power);
    }
}