package com.rivera.jeffer.domotica;

//@autor: Jefferson Rivera

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Switch mySwitch;
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebase.setAndroidContext(this);

        Intent intent = new Intent(this, FireService.class);
        startService(intent);

        mySwitch = (Switch)findViewById(R.id.mySwitch);

        firebase = new Firebase("https://android2016.firebaseio.com/luces/sala");

        firebase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean estado = (Boolean) dataSnapshot.getValue();

                mySwitch.setChecked(estado);

                if(estado){
                    mySwitch.setText("Luz Sala Encendida");

                }else{
                    mySwitch.setText("Luz Sala Apagada");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mySwitch.setText("Luz Sala Encendida");
                    firebase.setValue(true);

                } else {
                    mySwitch.setText("Luz Sala Apagado");
                    firebase.setValue(false);
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Desarrollado Por:");
            builder.setMessage("Jefferson Rivera \nriverajefer@gmail.com\nBogotá Colombia");
            builder.setPositiveButton("OK",null);
            builder.create();
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
