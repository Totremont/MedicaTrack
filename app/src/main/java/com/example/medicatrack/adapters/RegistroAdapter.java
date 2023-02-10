package com.example.medicatrack.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicatrack.R;
import com.example.medicatrack.databinding.RegistroViewlistBinding;
import com.example.medicatrack.model.Medicamento;
import com.example.medicatrack.model.Registro;
import com.example.medicatrack.utilities.FechaFormat;
import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.util.ArrayList;

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder>
{

    private final ArrayList<Medicamento> medicamentos = new ArrayList<>();
    private final ArrayList<Registro> registros = new ArrayList<>();  //Ultimo registro de cada medicamento


    @NonNull
    @Override
    public RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new RegistroViewHolder(RegistroViewlistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroViewHolder holder, int position)
    {
        holder.bind(medicamentos.get(position));
    }

    @Override
    public int getItemCount()
    {
        return medicamentos.size();
    }

    public void setData(ArrayList<Medicamento> medicamentos)
    {
        this.medicamentos.clear();
        //this.registros.clear();
        this.medicamentos.addAll(medicamentos);
        //this.registros.addAll(registros);

        MedicamentoDifference diferenciador = new MedicamentoDifference(medicamentos,this.medicamentos);
        DiffUtil.DiffResult resultado = DiffUtil.calculateDiff(diferenciador);
        resultado.dispatchUpdatesTo(this);
    }

    //Clase Viewholder - se encarga de mostrar los datos en la UI
    public static class RegistroViewHolder extends RecyclerView.ViewHolder
    {
        private RegistroViewlistBinding binding;

        public RegistroViewHolder(@NonNull RegistroViewlistBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Medicamento medicamento)
        {
            binding.tiempoTextView.setText(FechaFormat.formattedHora(medicamento.getHora()));
            binding.nombreTextView.setText(medicamento.getNombre());
            //binding.dosisTextView.setText(medicamento.getConcentracion());
            //binding.iconMedicamento

            binding.tomarButton.setOnClickListener(view ->
            {
                binding.buttonsLayout.setVisibility(LinearLayoutCompat.GONE);
                binding.arrow.setImageResource(R.drawable.icon_arrow_down);
                binding.iconTomado.setImageResource(R.drawable.icon_check_checked);
                binding.iconNoTomado.setImageResource(R.drawable.icon_close_unchecked);
                binding.iconPendiente.setImageResource(R.drawable.icon_pending_unchecked);
                binding.estadoTextView.setText("Tomado");
                binding.tomarButton.setVisibility(MaterialButton.GONE);
                binding.noTomarButton.setVisibility(MaterialButton.VISIBLE);
                binding.pendienteButton.setVisibility(MaterialButton.VISIBLE);
            });

            binding.noTomarButton.setOnClickListener(view ->
            {
                binding.buttonsLayout.setVisibility(LinearLayoutCompat.GONE);
                binding.arrow.setImageResource(R.drawable.icon_arrow_down);
                binding.iconTomado.setImageResource(R.drawable.icon_check_unchecked);
                binding.iconNoTomado.setImageResource(R.drawable.icon_close_checked);
                binding.iconPendiente.setImageResource(R.drawable.icon_pending_unchecked);
                binding.estadoTextView.setText("No tomado");
                binding.tomarButton.setVisibility(MaterialButton.VISIBLE);
                binding.noTomarButton.setVisibility(MaterialButton.GONE);
                binding.pendienteButton.setVisibility(MaterialButton.VISIBLE);
            });
            binding.pendienteButton.setOnClickListener(view ->
            {
                binding.buttonsLayout.setVisibility(LinearLayoutCompat.GONE);
                binding.arrow.setImageResource(R.drawable.icon_arrow_down);
                binding.iconTomado.setImageResource(R.drawable.icon_check_unchecked);
                binding.iconNoTomado.setImageResource(R.drawable.icon_close_unchecked);
                binding.iconPendiente.setImageResource(R.drawable.icon_pending_checked);
                binding.estadoTextView.setText("Pendiente");
                binding.tomarButton.setVisibility(MaterialButton.VISIBLE);
                binding.noTomarButton.setVisibility(MaterialButton.VISIBLE);
                binding.pendienteButton.setVisibility(MaterialButton.GONE);
            });

            binding.getRoot().setOnClickListener(view ->
            {
                if(binding.buttonsLayout.getVisibility() == MaterialButton.GONE)
                {
                    binding.buttonsLayout.setVisibility(MaterialButton.VISIBLE);
                    binding.arrow.setImageResource(R.drawable.icon_arrow_up);
                }
                else {
                    binding.buttonsLayout.setVisibility(MaterialButton.GONE);
                    binding.arrow.setImageResource(R.drawable.icon_arrow_down);
                }
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
