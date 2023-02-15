package com.example.medicatrack.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicatrack.databinding.MedicamentoViewlistBinding;
import com.example.medicatrack.model.Medicamento;
import com.example.medicatrack.utilities.FechaFormat;
import com.example.medicatrack.utilities.ResourcesUtility;
import com.example.medicatrack.viewmodels.MedicamentoViewModel;

import java.util.ArrayList;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder>
{

    private final ArrayList<Medicamento> medicamentos = new ArrayList<>();
    private MedicamentoViewModel viewModel;

    public MedicamentoAdapter(MedicamentoViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public MedicamentoAdapter.MedicamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new MedicamentoAdapter.MedicamentoViewHolder(MedicamentoViewlistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoAdapter.MedicamentoViewHolder holder, int position)
    {
        holder.bind(medicamentos.get(position),viewModel);
    }

    @Override
    public int getItemCount()
    {
        return medicamentos.size();
    }

    public void setData(ArrayList<Medicamento> medicamentos)
    {
        this.medicamentos.clear();
        this.medicamentos.addAll(medicamentos);

        MedicamentoAdapter.MedicamentoDifference diferenciador = new MedicamentoAdapter.MedicamentoDifference(medicamentos,this.medicamentos);
        DiffUtil.DiffResult resultado = DiffUtil.calculateDiff(diferenciador);
        //resultado.dispatchUpdatesTo(this);
        notifyDataSetChanged();
    }

    //Clase Viewholder - se encarga de mostrar los datos en la UI
    public static class MedicamentoViewHolder extends RecyclerView.ViewHolder
    {
        private MedicamentoViewlistBinding binding;

        public MedicamentoViewHolder(@NonNull MedicamentoViewlistBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Medicamento medicamento, MedicamentoViewModel viewModel)
        {
            binding.nombreTextView.setText(medicamento.getNombre());
            binding.frecuenciaTextView.setText(ResourcesUtility.enumToText(medicamento.getFrecuencia()));
            binding.tipoMedicamentoTextView.setText(medicamento.getForma().name() + " de " +
                    String.format("%.2f", medicamento.getConcentracion()) + " " + medicamento.getUnidad().name().toLowerCase());
            binding.medicamentoImage.setImageResource(ResourcesUtility.getMedicamentoImage(medicamento));
            binding.getRoot().setOnClickListener(view ->
            {
                //Abrir
                viewModel.navegarInfo.setValue(true);
                viewModel.medicamentoSeleccionado = medicamento;
            });

            binding.infoButton.setOnClickListener(view ->
            {
                viewModel.navegarInfo.setValue(true);
                viewModel.medicamentoSeleccionado = medicamento;
            });
        }
    }


    //Obtiene las diferencias entre 2 listas distintas
    public static class MedicamentoDifference extends DiffUtil.Callback
    {

        private final ArrayList<Medicamento> listaNueva = new ArrayList<>();
        private final ArrayList<Medicamento> listaVieja = new ArrayList<>();

        public MedicamentoDifference(ArrayList<Medicamento> listaNueva, ArrayList<Medicamento> listaVieja)
        {
            this.listaNueva.addAll(listaNueva);
            this.listaVieja.addAll(listaVieja);
        }

        @Override
        public int getOldListSize() {
            return listaVieja.size();
        }

        @Override
        public int getNewListSize() {
            return listaNueva.size();
        }

        @Override       //Son las 2 instancias iguales?
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
        {
            Medicamento viejo = listaVieja.get(oldItemPosition);
            Medicamento nuevo = listaNueva.get(newItemPosition);
            return nuevo.equals(viejo);
        }

        @Override       //Tienen las 2 instancias los mismos datos? (visualmente en la UI)
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
        {
            Medicamento viejo = listaVieja.get(oldItemPosition);
            Medicamento nuevo = listaNueva.get(newItemPosition);
            //Cantidad pastillas y registro blablabla
            return FechaFormat.formattedHora(viejo.getHora()).equals(FechaFormat.formattedHora(nuevo.getHora()))
                    && viejo.getNombre().equals(nuevo.getNombre()) && viejo.getConcentracion() == nuevo.getConcentracion();
        }
    }
}
