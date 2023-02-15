package com.example.medicatrack;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
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
import com.example.medicatrack.model.enums.Frecuencia;
import com.example.medicatrack.repo.MedicamentoRepository;
import com.example.medicatrack.viewmodels.MedicamentoViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MedicamentosFragment extends Fragment
{

    private FragmentMedicamentosBinding binding;
    private MedicamentoAdapter adapter;
    private final ArrayList<Medicamento> medicamentos = new ArrayList<>();
    private final ArrayList<Medicamento> medicamentosFiltrados = new ArrayList<>();

    private MedicamentoViewModel viewModel;


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
        viewModel = new ViewModelProvider(requireActivity()).get(MedicamentoViewModel.class);

        medicamentos.clear();
        medicamentosFiltrados.clear();

        repo.getAll((result, values) ->
        {
            if(result) medicamentos.addAll(values);
        });

        adapter = new MedicamentoAdapter(viewModel);

        adapter.setData(medicamentos);

        if(medicamentos.isEmpty()) {
            binding.layoutVacio.setVisibility(LinearLayoutCompat.VISIBLE);
            binding.recyclerView.setVisibility(RecyclerView.GONE);
        }
        else {
            binding.layoutVacio.setVisibility(LinearLayoutCompat.GONE);
            binding.recyclerView.setVisibility(RecyclerView.VISIBLE);
        }

        binding.recyclerView.setAdapter(adapter);


        binding.chipTodos.setOnClickListener(view1 ->
        {
            if(medicamentos.isEmpty()) {
                binding.layoutVacio.setVisibility(LinearLayoutCompat.VISIBLE);
                binding.recyclerView.setVisibility(RecyclerView.GONE);
            }
            else {
                binding.layoutVacio.setVisibility(LinearLayoutCompat.GONE);
                binding.recyclerView.setVisibility(RecyclerView.VISIBLE);
            }
            adapter.setData(medicamentos);
        });

        binding.chipDiaEspecifico.setOnClickListener(view1 ->
        {
            setMedicamentos(Frecuencia.DIAS_ESPECIFICOS,null);
        });

        binding.chipRegular.setOnClickListener(view1 ->
        {
            setMedicamentos(Frecuencia.INTERVALO_REGULAR,Frecuencia.TODOS_DIAS);
        });

        binding.chipNecesidad.setOnClickListener(view1 ->
        {
            setMedicamentos(Frecuencia.NECESIDAD,null);
        });

        viewModel.nuevoMedicamento.observe(requireActivity(),nuevo ->
        {
            if(!this.medicamentos.contains(nuevo)) {
                this.medicamentos.add(nuevo);
                if (binding.chipTodos.isChecked())
                {
                    binding.layoutVacio.setVisibility(LinearLayoutCompat.GONE);
                    binding.recyclerView.setVisibility(RecyclerView.VISIBLE);
                    adapter.setData(medicamentos);
                }
                else if (binding.chipDiaEspecifico.isChecked())
                    setMedicamentos(Frecuencia.DIAS_ESPECIFICOS, null);
                else if (binding.chipRegular.isChecked())
                    setMedicamentos(Frecuencia.INTERVALO_REGULAR, Frecuencia.TODOS_DIAS);
                else if (binding.chipNecesidad.isChecked())
                    setMedicamentos(Frecuencia.NECESIDAD, null);
            }
        });


    }

    public void setMedicamentos(Frecuencia frecuencia, Frecuencia frecuencia2)
    {
        medicamentosFiltrados.clear();
        if(!medicamentos.isEmpty()) {
            medicamentosFiltrados.addAll(medicamentos.stream().filter(it ->
            {
                if(frecuencia2 == null) return it.getFrecuencia().equals(frecuencia);
                else return (it.getFrecuencia().equals(frecuencia) || it.getFrecuencia().equals(frecuencia2));
            }).collect(Collectors.toList()));
            adapter.setData(medicamentosFiltrados);
        }
        if(medicamentosFiltrados.isEmpty()) {
            binding.recyclerView.setVisibility(RecyclerView.GONE);
            binding.layoutVacio.setVisibility(LinearLayoutCompat.VISIBLE);
        }
        else {
            binding.layoutVacio.setVisibility(LinearLayoutCompat.GONE);
            binding.recyclerView.setVisibility(RecyclerView.VISIBLE);
        }
    }
}