package com.vinidsl.navigationviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vinidsl.navigationviewdemo.Tasks.AgendaTask;
import com.vinidsl.navigationviewdemo.Tasks.RecintoTask;

/**
 * Created by JoseRogelio on 09/08/2015.
 */
public class AgendaActivity extends AppCompatActivity {

    private LinearLayout mIndicatorContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null) {

            String idEvento = extras.getString("id_evento");

            AgendaTask task = new AgendaTask(this);
            task.execute(idEvento);
        }

    }

}
