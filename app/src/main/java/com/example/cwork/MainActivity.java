package com.example.cwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

//import com.android.volley.Response;
import com.example.cwork.productview.ProductGridFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import okhttp3.Request;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            String token = this.getToken();
            if(token != null && !token.isEmpty())
            {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new ProductGridFragment())
                        .commit();
            }else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new LoginFragment())
                        .commit();
            }

        }

//        new MaterialAlertDialogBuilder(MainActivity.this)
//                .setTitle("Title")
//                .setMessage("Message")
//                .setPositiveButton("Ok", null)
//                .show();
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
                this.navigateTo(new ProductGridFragment(), false);
//                Toast.makeText(this, "зробити фото", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(this, TableActivity.class);
//                startActivity(intent);
                return true;
            case R.id.home:
                Toast.makeText(this, "Пошук", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
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
            case R.id.logout:
                Toast.makeText(this, "Вихід", Toast.LENGTH_LONG).show();
                this.removeToken();
                this.navigateTo(new LoginFragment(), false);
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

//    @Override
//    public void navigateTo(Fragment fragment, boolean addToBackstack) {
//        FragmentTransaction transaction =
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.container, fragment);
//        if (addToBackstack) {
//            transaction.addToBackStack(null);
//        }
//        transaction.commit();
//    }

    @Override
    public boolean isConnect(Response response) {
        switch (response.code()){
            case 200:
                Log.d("isConnect", Integer.toString(response.code()));
                return true;
            case 403:
                Log.d("isConnect", Integer.toString(response.code()));
                this.navigateTo(new CallbackFailureFragment(
                                "Доступ заборонено"), false);
                return false;
            case 404:
                Log.d("isConnect", Integer.toString(response.code()));
                this.navigateTo(new CallbackFailureFragment(
                                "Ресурс не знайдено"), false);
                return false;
            case 500:
                Log.d("isConnect", Integer.toString(response.code()));
                this.navigateTo(new CallbackFailureFragment(
                                "Внутрішня помилка серверу"), false);
                return false;
            default:
                Log.d("isConnect", Integer.toString(response.code()));
                return false;
        }
    }
}
