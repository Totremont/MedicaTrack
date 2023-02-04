package com.example.medicatrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.medicatrack.databinding.ActivityCreacionBinding;
import com.example.medicatrack.model.Medicamento;
import com.example.medicatrack.model.enums.Frecuencia;

import java.time.ZonedDateTime;

public class CreacionActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCreacionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarCreacion);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_creacion);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_creacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // Boton de cancelar
        if (id == R.id.action_cancelar) {
            // Volver a la actividad principal
            setResult(Activity.RESULT_CANCELED);
            finish();
            /*
            Cuando se cree el medicamento, y se retorne a la actividad principal, hay que hacer esto

            Medicamento med = new Medicamento(1);
            med.setHora(ZonedDateTime.now());
            med.setDias("1");
            med.setForma("Redondo");
            med.setNombre("Ibupirac");
            med.setFrecuencia(Frecuencia.NECESIDAD);
            med.setConcentracion(1.2f);
            med.setColor("Rojo");
            med.setHora(ZonedDateTime.now());

            setResult(Activity.RESULT_OK, new Intent().putExtra("Medicamento", med));
            finish();

             */
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_creacion);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}