package com.example.medicatrack;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.example.medicatrack.viewmodels.MedicamentoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.medicatrack.creacion.CreacionActivity;
import com.example.medicatrack.receiver.RegistroReceiver;

import com.example.medicatrack.repo.persist.database.Database;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.getBoolean("recibir_not", true)){ // Si no existe (defValue == true) o tiene valor true
            createNotificationChannel();
            editor.putBoolean("recibir_not",true);
        }



        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        MedicamentoViewModel viewModel = new ViewModelProvider(this).get(MedicamentoViewModel.class);

        viewModel.activarFab.observe(this, aBoolean ->
        {
            if(aBoolean) binding.fab.setVisibility(FloatingActionButton.VISIBLE);
            else binding.fab.setVisibility(FloatingActionButton.GONE);
        });

        viewModel.navegarInfo.observe(this,aBoolean ->
        {
            if(aBoolean) {
                navController.navigate(R.id.action_global_medicamentoInfoFragment);
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

                            //viewModel.nuevoMedicamento.setValue(a);

                        }
                    }
                });

        // Boton flotante, lleva a la actividad de Creacion
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreacionActivity.class);
            creacionLauncher.launch(intent);
        });


        // Se inicia la actividad producto de la notificacion
        if(getIntent().getAction().equals(RegistroReceiver.REGISTRAR))
        {
            Medicamento med = getIntent().getParcelableExtra("Medicamento");
        }

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
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_global_settingsFragment);
            return true;
        }
        if(id == R.id.action_map){

            // Buscar por farmacias cercanas
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=Farmacias cercanas");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            String CHANNEL_ID = getString(R.string.channel_id);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}