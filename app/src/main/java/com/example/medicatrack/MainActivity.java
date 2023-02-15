package com.example.medicatrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.medicatrack.databinding.ActivityMainBinding;
import com.example.medicatrack.model.Medicamento;
import com.example.medicatrack.model.Registro;
import com.example.medicatrack.model.enums.RegistroEstado;
import com.example.medicatrack.repo.MedicamentoRepository;
import com.example.medicatrack.repo.RegistroRepository;
import com.example.medicatrack.viewmodels.MedicamentoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.medicatrack.creacion.CreacionActivity;
import com.example.medicatrack.repo.persist.database.Database;

import java.time.ZoneId;
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


        MedicamentoViewModel viewModel = new ViewModelProvider(this).get(MedicamentoViewModel.class);

/*        MedicamentoRepository repo1 = MedicamentoRepository.getInstance(this);
        Medicamento[] medi = new Medicamento[1];
        repo1.getAll((result, values) -> {medi[0] = values.get(0);});
        Registro regi = new Registro();
        regi.setFecha(ZonedDateTime.now());
        regi.setEstado(RegistroEstado.PENDIENTE);
        regi.setMedicamento(medi[0]);
        RegistroRepository repo2 = RegistroRepository.getInstance(this);
        repo2.insert(regi,result -> {});*/

        viewModel.activarFab.observe(this, aBoolean ->
        {
            if(aBoolean) binding.fab.setVisibility(FloatingActionButton.VISIBLE);
            else binding.fab.setVisibility(FloatingActionButton.GONE);
        });

        viewModel.navegarInfo.observe(this,aBoolean ->
        {
            if(aBoolean) {
                navController.navigate(R.id.action_FirstFragment_to_medicamentoInfoFragment);
                viewModel.navegarInfo.postValue(false);
            }
        });


        // Obtener resultado de la otra actividad
        ActivityResultLauncher<Intent> creacionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Medicamento a = data.getExtras().getParcelable("Medicamento");
                            viewModel.nuevoMedicamento.setValue(a);
                        }
                    }
                });

        // Boton flotante, lleva a la actividad de Creacion
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreacionActivity.class);
            creacionLauncher.launch(intent);
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

    @Override
    protected void onDestroy() {
        // Cerrar la conexion de la base de datos
        Database.getInstance(getApplicationContext()).close();
        super.onDestroy();
    }
}