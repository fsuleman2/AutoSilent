package com.example.quickksilent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Dialog;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {
    private static final String TAG = "Menu";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private ListView lv;
    private ArrayAdapter<String> listAdapter ;
    DatabaseHelper db ;
    FloatingActionButton mb;
    FloatingActionButton createTime;
    @Override
    protected void onCreate(Bundle menuState) {
        // TODO Auto-generated method stub
        super.onCreate(menuState);
        setContentView(R.layout.activity_menu);

        lv = (ListView)findViewById(R.id.listview);
        db = new DatabaseHelper(Menu.this);
        db.open();
        ArrayList<String> names = new ArrayList<String>();
        names = db.tgetname();
        db.close();
        listAdapter = new ArrayAdapter<String>(Menu.this,android.R.layout.simple_list_item_1,names);
        lv.setAdapter(listAdapter);
        createTime=findViewById(R.id.bcreate);
        createTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent openCreateTime = new Intent(Menu.this,CreateTime.class);
                startActivity(openCreateTime);
                finish();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View item, int position,long id) {
                // TODO Auto-generated method stub
                String name = listAdapter.getItem(position);
                db.open();
                String data = db.tgetdata(name);
                db.close();
                String[] temp = data.split("/");
                Bundle time = new Bundle();
                time.putString("name",name);
                time.putInt("start",Integer.parseInt(temp[0]));
                time.putInt("end", Integer.parseInt(temp[1]));
                time.putString("rep", temp[2]);
                Intent openEditTime = new Intent(Menu.this,EditTime.class);
                openEditTime.putExtras(time);
                startActivity(openEditTime);
                finish();
            }
        });

    }




    public void onBackPressed() {
        // TODO Auto-generated method stub
        finish();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
}
