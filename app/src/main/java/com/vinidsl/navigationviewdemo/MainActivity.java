/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vinidsl.navigationviewdemo;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    String valor_global="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setupDrawerToggle();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setItemIconTintList(null);

        //navigationView.setItemBackgroundResource(1);
        //navigationView.setItemTextColor(R.color.prima );

        if (navigationView != null) {
            setupDrawerContent(navigationView);

        }

        // get
        valor_global = ((GlobalClass) this.getApplication()).getSomeVariable();

        Pantalla_Princi fe_int = new Pantalla_Princi();
        MuestraFragment(fe_int);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //finish();
        //startActivity(getIntent());
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerToggle(){
        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        //navigationView.getMenu().findItem(R.id.nav_home).setChecked(false);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            /*case R.id.aditional1:
                                Snackbar.make(navigationView, "Item adicional 1 seleccionado", Snackbar.LENGTH_LONG).show();
                                return true;
                            case R.id.aditional2:
                                Snackbar.make(navigationView, "Item adicional 2 seleccionado", Snackbar.LENGTH_LONG).show();
                                return true;*/
                            case R.id.nav_home:
                                Ferias_Int ferias_int = new Ferias_Int();
                                MuestraFragment(ferias_int);
                                //Creamos el Intent
                               // Intent intent =
                                   //     new Intent(MainActivity.this, ClasePrueba.class);

                                //Creamos la información a pasar entre actividades
                                //Bundle b = new Bundle();

                                //Añadimos la información al intent
                                //intent.putExtras(b);

                                //Iniciamos la nueva actividad
                                //startActivity(intent);
                                //Snackbar.make(navigationView, "Item principal seleccionado", Snackbar.LENGTH_LONG).show();
                                break;
                            case R.id.nav_calendar:
                                Ferias_Nac fragment = new Ferias_Nac();
                                MuestraFragment(fragment);
                                //Snackbar.make(navigationView, "Item calendario seleccionado", Snackbar.LENGTH_LONG).show();
                                break;
                            case R.id.nav_music:
                                Noticias noticias = new Noticias();
                                MuestraFragment(noticias);
                                //Snackbar.make(navigationView, "Item música seleccionado", Snackbar.LENGTH_LONG).show();
                                break;
                            case R.id.nav_messages:
                                //Registro fragRegistro = new Registro();
                                Login fragRegistro = new Login();
                                MuestraFragment(fragRegistro);
                                //Perfil fragRegistro = new Perfil();
                                //MuestraFragment(fragRegistro);
                                break;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

    }
    public void MuestraFragment(Fragment fragment){
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();
    }

    public void cambiarMenu(){

    }
}
