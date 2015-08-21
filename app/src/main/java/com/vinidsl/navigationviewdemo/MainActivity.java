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

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vinidsl.navigationviewdemo.Tasks.GuardarDatosFacturaTask;


public class MainActivity extends AppCompatActivity
        implements DatosFacturaDialog.DatosFacturaListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    String idUsuario;
    String valor_global="";
    private Button sessionButton;
    Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        act = this;

        Cifrado obj=new Cifrado();
        //String valorEnc=obj.encriptar("300|admin@s-t.mx|admin");
        String valorEnc=obj.encriptar("301");
        Log.i("ENC",valorEnc);

        PonentesTask objeto=new PonentesTask(this.getBaseContext());


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
        //valor_global = ((GlobalClass) this.getApplication()).getSomeVariable();

        Pantalla_Princi fe_int = new Pantalla_Princi();
        MuestraFragment(fe_int);

    }


    @Override
    protected void onResume() {
        super.onResume();
        buscaUsuario();
        sessionButton = (Button) findViewById(R.id.navigation_button);
        cambiarMenu();
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
                            case R.id.nav_ferias_int:
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
                            case R.id.nav_ferias_nac:
                                Ferias_Nac fragment = new Ferias_Nac();
                                MuestraFragment(fragment);
                                //Snackbar.make(navigationView, "Item calendario seleccionado", Snackbar.LENGTH_LONG).show();
                                break;
                            case R.id.nav_mis_eventos:
                                /*NoticiasFragment noticias = new NoticiasFragment();
                                MuestraFragment(noticias);*/
                                /*Intent prog = new Intent(act, ProgramaActivity.class);
                                startActivity(prog);*/
                                Eventos fragMisEventos = new Eventos();
                                MuestraFragment(fragMisEventos);
                                //Snackbar.make(navigationView, "Item música seleccionado", Snackbar.LENGTH_LONG).show();
                                break;
                            case R.id.nav_mi_perfil:
                                //Registro fragRegistro = new Registro();
                                PerfilFragment fragRegistro = new PerfilFragment();
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

    public void llamarRutinaSesion(View v) {
        if(sesionActiva()) {
            idUsuario = "0";
            SharedPreferences preferencias =
                    getSharedPreferences(getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.remove(getString(R.string.pref_idusuario));
            editor.commit();
            cambiarMenu();
        } else {
            Login fragRegistro = new Login();
            MuestraFragment(fragRegistro);
        }
        mDrawerLayout.closeDrawers();
    }

    public boolean sesionActiva() {
        return !(idUsuario.isEmpty() || idUsuario.equals("0"));
    }

    public void buscaUsuario() {
        SharedPreferences preferencias =
                getSharedPreferences(getString(R.string.espacio_prefs), Context.MODE_PRIVATE);
        idUsuario = preferencias.getString(getString(R.string.pref_idusuario), "0");
    }

    public void cambiarMenu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(sesionActiva()) {
            sessionButton.setText(getString(R.string.nav_button_logout));
            navigationView.getMenu().setGroupVisible(R.id.items_login, true);
        } else {
            sessionButton.setText(getString(R.string.nav_button_login));
            navigationView.getMenu().setGroupVisible(R.id.items_login, false);
            Pantalla_Princi fe_int = new Pantalla_Princi();
            MuestraFragment(fe_int);
        }
    }

    @Override
    public void onDialogClick(DialogFragment dialog) {

        TextView rfcTV = (TextView) dialog.getDialog().findViewById(R.id.perfil_rfc);
        TextView rsTV = (TextView) dialog.getDialog().findViewById(R.id.perfil_rs);
        TextView calleFacTV = (TextView) dialog.getDialog().findViewById(R.id.perfil_calle_fac);
        TextView numExtTV = (TextView) dialog.getDialog().findViewById(R.id.perfil_num_ext_fac);
        TextView numIntTV = (TextView) dialog.getDialog().findViewById(R.id.perfil_num_int_fac);
        TextView coloniaFacTV = (TextView) dialog.getDialog().findViewById(R.id.perfil_colonia_fac);
        TextView delegacionFacTV = (TextView) dialog.getDialog().findViewById(R.id.perfil_delegacion_fac);
        TextView cpFacTV = (TextView) dialog.getDialog().findViewById(R.id.perfil_codigo_postal_fac);
        TextView estadoTV = (TextView) dialog.getDialog().findViewById(R.id.perfil_estado_fac);

        /*

            id_usuario|rfc|razon_social|calle|noext|noint|colonia|cp

            |municipio|estado

         */

        String idPerfil = "1";
        String parametro;

        String rfc = rfcTV.getText().toString();
        String rs= rsTV.getText().toString();
        String calle = calleFacTV.getText().toString();
        String numExt = numExtTV.getText().toString();
        String numInt = numIntTV.getText().toString();
        String colonia = coloniaFacTV.getText().toString();
        String cp = cpFacTV.getText().toString();
        String municipio = delegacionFacTV.getText().toString();
        String estado = estadoTV.getText().toString();

        parametro = idPerfil + "|" + rfc + "|" + rs + "|" + calle + "|" + numExt + "|" +
                numInt + "|" + colonia + "|" + cp + "|" + municipio + "|" + estado;

        GuardarDatosFacturaTask task = new GuardarDatosFacturaTask(this);
        task.execute(parametro);

    }

}
