package com.example.medicatrack;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.medicatrack.adapters.MedicamentoAdapter;
import com.example.medicatrack.databinding.ActivityMainBinding;
import com.example.medicatrack.model.Medicamento;
import com.example.medicatrack.model.enums.Frecuencia;
import com.example.medicatrack.repo.MedicamentoRepository;
import com.example.medicatrack.viewmodels.MedicamentoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.ZonedDateTime;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

/*        //Datos de prueba
        Medicamento medicamento1 = new Medicamento(1);
        medicamento1.setNombre("Ibuprofeno");
        medicamento1.setHora(ZonedDateTime.now());
        medicamento1.setFrecuencia(Frecuencia.INTERVALO_REGULAR);
        medicamento1.setDias("Lunes, Sabado, Domingo");
        medicamento1.setColor("Rojo");
        medicamento1.setConcentracion(25f);
        medicamento1.setDescripcion("Hola");
        medicamento1.setForma("Capsula");

        Medicamento medicamento2 = new Medicamento(2);
        medicamento2.setNombre("Paracetamol");
        medicamento2.setHora(ZonedDateTime.now());
        medicamento2.setFrecuencia(Frecuencia.NECESIDAD);
        medicamento2.setDias("Lunes, Miercoles, Domingo");
        medicamento2.setColor("Azul");
        medicamento2.setConcentracion(2.5f);
        medicamento2.setDescripcion("Pija");
        medicamento2.setForma("Crema");

        Medicamento medicamento3 = new Medicamento(2);
        medicamento3.setNombre("Ibupirac");
        medicamento3.setHora(ZonedDateTime.now());
        medicamento3.setFrecuencia(Frecuencia.NECESIDAD);
        medicamento3.setDias("Lunes, Miercoles, Domingo");
        medicamento3.setColor("Azul");
        medicamento3.setConcentracion(2.5f);
        medicamento3.setDescripcion("Pija");
        medicamento3.setForma("Crema");*/

/*        MedicamentoRepository repo = MedicamentoRepository.getInstance(this);
        repo.insert(medicamento1,result -> {});
        repo.insert(medicamento2,result -> {});
        repo.insert(medicamento3,result -> {});*/

        MedicamentoViewModel viewModel = new ViewModelProvider(this).get(MedicamentoViewModel.class);
        viewModel.flag.observe(this,integer ->
        {
            if(integer == 3) navController.navigate(R.id.action_FirstFragment_to_medicamentoInfoFragment);
            if(integer > 1) {
                binding.fab.setVisibility(FloatingActionButton.VISIBLE);
            } else binding.fab.setVisibility(FloatingActionButton.GONE);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        binding.fab.setVisibility(FloatingActionButton.GONE);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}