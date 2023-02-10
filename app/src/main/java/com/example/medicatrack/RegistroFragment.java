package com.example.medicatrack;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import com.example.medicatrack.adapters.RegistroAdapter;
import com.example.medicatrack.databinding.FragmentRegistroBinding;
import com.example.medicatrack.model.Medicamento;
import com.example.medicatrack.repo.MedicamentoRepository;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class RegistroFragment extends Fragment {

    private FragmentRegistroBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {

        binding = FragmentRegistroBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        MedicamentoRepository repo = MedicamentoRepository.getInstance(getContext());
        final ArrayList<Medicamento> medicamentos = new ArrayList<>();
        repo.getById(1,(result, value) ->
        {
            if(result) medicamentos.add(value);
        });

        repo.getById(2,(result, value) ->
        {
            if(result) medicamentos.add(value);
        });

        repo.getById(3,(result, value) ->
        {
            if(result) medicamentos.add(value);
        });

        RegistroAdapter adapter = new RegistroAdapter();
        adapter.setData(medicamentos);
        binding.recyclerView.setAdapter(adapter);


        binding.chipAtras.setOnClickListener(view1 ->
        {
            ZonedDateTime ahora = ZonedDateTime.now();
            //Establece desde que fecha se mostrará el calendario
            int year = ahora.getYear();
            int month = ahora.getMonthValue() - 1;
            int day = ahora.getDayOfMonth();
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2)         //i : anio, i1: mes, i2: dia
                {
                    binding.chipAtras.setText("" + i2 + "/" + i1 + "/" + i);
                }
            }, year,month,day);
            datePickerDialog.show();
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}