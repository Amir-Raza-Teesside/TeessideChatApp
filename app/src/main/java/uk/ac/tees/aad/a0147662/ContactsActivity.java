package uk.ac.tees.aad.a0147662;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        getSupportActionBar().show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


       switch (item.getItemId())
       {

           case R.id.tweet:
               Toast.makeText(ContactsActivity.this,"JKHJKHKJK",Toast.LENGTH_LONG).show();
               break;
           case R.id.group:
               Toast.makeText(ContactsActivity.this,"Create Group",Toast.LENGTH_LONG).show();
               break;

       }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.topmenu,menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        return true;

    }
}