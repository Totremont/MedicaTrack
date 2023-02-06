package com.example.medicatrack.creacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.medicatrack.R;
import com.example.medicatrack.creacion.viewmodels.CreacionViewModel;
import com.example.medicatrack.databinding.ActivityCreacionBinding;
import com.example.medicatrack.model.Medicamento;
import com.example.medicatrack.model.enums.Frecuencia;

import java.time.ZonedDateTime;

public class CreacionActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCreacionBinding binding;
    private CreacionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarCreacion);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_creacion);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Manejo de cambio de valor en el nombre, forma y concentracion
        viewModel = new ViewModelProvider(this).get(CreacionViewModel.class);
        // Cambio del nombre
        viewModel.getNombreMed().observe(this, nombre -> {
            binding.toolbarCreacion.setTitle(nombre==null?"Medicamento":nombre);
        });
        // Cambio de la concentracion
        viewModel.getConcentracion().observe(this, concentracion -> {
            crearSubtitulo(viewModel.getForma().getValue(), concentracion);
        });
        // Cambio de la forma
        viewModel.getForma().observe(this, forma -> {
            crearSubtitulo(forma, viewModel.getConcentracion().getValue());
        });
    }

    private void crearSubtitulo(String forma, Float concentracion){
        String subtitulo = "";
        if(forma == null && concentracion != null) subtitulo = concentracion.toString();
        if(forma != null && concentracion == null) subtitulo = forma;
        if(forma != null && concentracion != null) subtitulo = forma + ", " + concentracion.toString();
        binding.toolbarCreacion.setSubtitle(subtitulo);
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