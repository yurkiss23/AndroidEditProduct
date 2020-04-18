package com.example.cwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewSimpleActivity extends AppCompatActivity {

    String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_simple);

        // находим список
        ListView lvMain = (ListView) findViewById(R.id.lvMain);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.table:
                Toast.makeText(this, "зробити фото", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, TableActivity.class);
                startActivity(intent);
                return true;
            case R.id.home:
                Toast.makeText(this, "Пошук", Toast.LENGTH_LONG).show();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.listviewitem:
                Toast.makeText(this, "Пошук", Toast.LENGTH_LONG).show();
                intent = new Intent(this, ListViewActivity.class);
                startActivity(intent);
                return true;
            case R.id.listviewsimpleitem:
                Toast.makeText(this, "Пошук", Toast.LENGTH_LONG).show();
                intent = new Intent(this, ListViewSimpleActivity.class);
                startActivity(intent);
                return true;
            case R.id.edit:
                Toast.makeText(this, "Редагування", Toast.LENGTH_LONG).show();
                return true;
            case R.id.edit_copy:
                Toast.makeText(this, "Копіювати", Toast.LENGTH_LONG).show();
                return true;
            case R.id.edit_past:
                Toast.makeText(this, "Вставити", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
