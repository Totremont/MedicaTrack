package com.example.medicatrack.creacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicatrack.R;
import com.example.medicatrack.creacion.viewmodels.CreacionViewModel;
import com.example.medicatrack.databinding.ActivityCreacionBinding;
import com.example.medicatrack.databinding.FragmentDatosMedicamentoBinding;
import com.example.medicatrack.databinding.FragmentFirstBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class DatosMedicamentoFragment extends Fragment {

    private FragmentDatosMedicamentoBinding binding;
    private CreacionViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(CreacionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDatosMedicamentoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}