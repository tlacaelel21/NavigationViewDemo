package com.vinidsl.navigationviewdemo;

/**
 * Created by root on 31/07/15.
 */
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class ClasePrueba extends Activity {

    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new OnClickListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(ClasePrueba.this, button1);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(ClasePrueba.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

    }

}

