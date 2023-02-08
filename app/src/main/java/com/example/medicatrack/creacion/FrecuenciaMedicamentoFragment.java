package com.example.medicatrack.creacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicatrack.creacion.viewmodels.CreacionViewModel;
import com.example.medicatrack.databinding.FragmentFrecuenciaMedicamentoBinding;

public class FrecuenciaMedicamentoFragment extends Fragment {

    private FragmentFrecuenciaMedicamentoBinding binding;
    private CreacionViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(CreacionViewModel.class);
        // Dado que, de manera muy rara, cuando se llega a este fragmento, el titulo vuelve a "Medicamento", lo seteo nuevamente al valor actual del nonbre
        viewModel.setNombreMed(viewModel.getNombreMed().getValue());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFrecuenciaMedicamentoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}