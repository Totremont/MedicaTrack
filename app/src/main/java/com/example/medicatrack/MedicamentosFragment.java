package com.example.medicatrack;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicatrack.adapters.MedicamentoAdapter;
import com.example.medicatrack.adapters.RegistroAdapter;
import com.example.medicatrack.databinding.FragmentMedicamentosBinding;
import com.example.medicatrack.model.Medicamento;
import com.example.medicatrack.repo.MedicamentoRepository;
import com.example.medicatrack.viewmodels.MedicamentoViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MedicamentosFragment extends Fragment
{

    private FragmentMedicamentosBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMedicamentosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
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

        MedicamentoViewModel viewModel = new ViewModelProvider(requireActivity()).get(MedicamentoViewModel.class);

        MedicamentoAdapter adapter = new MedicamentoAdapter(viewModel);
        adapter.setData(medicamentos);
        binding.recyclerView.setAdapter(adapter);
    }
}