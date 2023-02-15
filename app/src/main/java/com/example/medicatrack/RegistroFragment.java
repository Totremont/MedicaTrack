package com.example.medicatrack;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.example.medicatrack.adapters.RegistroAdapter;
import com.example.medicatrack.databinding.FragmentRegistroBinding;
import com.example.medicatrack.model.Medicamento;
import com.example.medicatrack.model.Registro;
import com.example.medicatrack.model.enums.RegistroEstado;
import com.example.medicatrack.repo.MedicamentoRepository;
import com.example.medicatrack.repo.RegistroRepository;
import com.example.medicatrack.utilities.FechaFormat;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegistroFragment extends Fragment {

    private FragmentRegistroBinding binding;
    private RegistroRepository registroRepo;
    private MedicamentoRepository medicamentoRepo;

    private final ArrayList<Registro> registros = new ArrayList<>();
    private final ArrayList<Medicamento> medicamentos = new ArrayList<>();
    private RegistroAdapter adapter;

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

        registroRepo = RegistroRepository.getInstance(requireContext());
        medicamentoRepo = MedicamentoRepository.getInstance(requireContext());

        adapter = new RegistroAdapter(requireContext());

        binding.titleText.setText("Registro del día");

        binding.subtitleText.setText("Medicamentos a consumir");

        ZonedDateTime ahora = ZonedDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")).truncatedTo(ChronoUnit.DAYS);

        setNuevosRegistros(ahora,false,false);

        binding.recyclerView.setAdapter(adapter);


        binding.chipAtras.setOnClickListener(view1 ->
        {

            CalendarConstraints.Builder constraints = new CalendarConstraints.Builder();
            constraints.setValidator(DateValidatorPointBackward.before(ahora.minusDays(3).toInstant().toEpochMilli()));
            MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
            materialDateBuilder.setTitleText("Seleccione una fecha");
            materialDateBuilder.setSelection(ahora.minusDays(3).toInstant().toEpochMilli());
            materialDateBuilder.setCalendarConstraints(constraints.build());
            MaterialDatePicker<Long> picker = materialDateBuilder.build();
            picker.addOnPositiveButtonClickListener(selection ->
            {
                ZonedDateTime fechaSeleccionada = ZonedDateTime.ofInstant(Instant.ofEpochMilli(selection),ZoneId.of("America/Argentina/Buenos_Aires"));
                fechaSeleccionada = fechaSeleccionada.plusDays(1); //Por alguna razon la fecha devuelta esta un dia atrasada
                StringBuilder text = new StringBuilder()
                        .append(fechaSeleccionada.getDayOfMonth())
                        .append("/").append(fechaSeleccionada.getMonthValue()).append("/").append(fechaSeleccionada.getYear());
                binding.chipAtras.setText(text.toString());
                binding.chipMasAdelante.setText("Más adelante");
                setNuevosRegistros(fechaSeleccionada,true,false);
                StringBuilder text2 = new StringBuilder().append("Registro del ").append(fechaSeleccionada.getDayOfWeek().name().toLowerCase()).append(" ").append(text);
                binding.titleText.setText(text2.toString());
                binding.subtitleText.setText("Medicamentos consumidos");

            });
            picker.show(getParentFragmentManager(),"Fecha");
        });

        binding.chipMasAdelante.setOnClickListener(view1 ->
        {
            CalendarConstraints.Builder constraints = new CalendarConstraints.Builder();
            constraints.setValidator(DateValidatorPointForward.from(ahora.plusDays(2).toInstant().toEpochMilli()));
            MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
            materialDateBuilder.setTitleText("Seleccione una fecha");
            materialDateBuilder.setSelection(ahora.plusDays(3).toInstant().toEpochMilli());
            materialDateBuilder.setCalendarConstraints(constraints.build());
            MaterialDatePicker<Long> picker = materialDateBuilder.build();
            picker.addOnPositiveButtonClickListener(selection ->
            {
                ZonedDateTime fechaSeleccionada = ZonedDateTime.ofInstant(Instant.ofEpochMilli(selection),ZoneId.of("America/Argentina/Buenos_Aires"));
                fechaSeleccionada = fechaSeleccionada.plusDays(1); //Por alguna razon la fecha devuelta esta un dia atrasada
                StringBuilder text = new StringBuilder()
                        .append(fechaSeleccionada.getDayOfMonth())
                        .append("/").append(fechaSeleccionada.getMonthValue()).append("/").append(fechaSeleccionada.getYear());
                binding.chipMasAdelante.setText(text.toString());
                binding.chipAtras.setText("Más atrás");
                setRegistrosFuturos(fechaSeleccionada);
                StringBuilder text2 = new StringBuilder().append("Registro del ").append(fechaSeleccionada.getDayOfWeek().name().toLowerCase()).append(" ").append(text);
                binding.titleText.setText(text2.toString());
                binding.subtitleText.setText("Medicamentos a consumir");

            });
            picker.show(getParentFragmentManager(),"Fecha");
        });

        binding.chipHoy.setOnClickListener(view1 ->
        {
            binding.titleText.setText("Registro del día");
            binding.subtitleText.setText("Medicamentos a consumir");
            binding.chipMasAdelante.setText("Más adelante");
            binding.chipAtras.setText("Más atrás");

            setNuevosRegistros(ahora,false,false);
        });

        binding.chipAyer.setOnClickListener(view1 ->
        {
            binding.titleText.setText("Registro de ayer");
            binding.subtitleText.setText("Medicamentos consumidos");
            binding.chipMasAdelante.setText("Más adelante");
            binding.chipAtras.setText("Más atrás");

            setNuevosRegistros(ahora.minusDays(1),true,false);
        });

        binding.chipAnteayer.setOnClickListener(view1 ->
        {
            binding.titleText.setText("Registro de anteayer");
            binding.subtitleText.setText("Medicamentos consumidos");
            binding.chipMasAdelante.setText("Más adelante");
            binding.chipAtras.setText("Más atrás");

            setNuevosRegistros(ahora.minusDays(2),true,false);
        });

        binding.chipManiana.setOnClickListener(view1 ->
        {
            binding.titleText.setText("Registro de mañana");
            binding.subtitleText.setText("Medicamentos a consumir");
            binding.chipMasAdelante.setText("Más adelante");
            binding.chipAtras.setText("Más atrás");

            setRegistrosFuturos(ahora.plusDays(1));
        });

        binding.chipPasadoManiana.setOnClickListener(view1 ->
        {
            binding.titleText.setText("Registro de pasado mañana");
            binding.subtitleText.setText("Medicamentos a consumir");
            binding.chipMasAdelante.setText("Más adelante");
            binding.chipAtras.setText("Más atrás");

            setRegistrosFuturos(ahora.plusDays(2));
        });

    }

    public void setNuevosRegistros(ZonedDateTime fecha, boolean esPasado, boolean esFuturo)
    {
        registros.clear();
        registroRepo.getAllFromDate(fecha,(result, values) ->
        {
            if(result) registros.addAll(values);
        });

        medicamentos.clear();
        medicamentos.addAll(registros.stream().map(Registro::getMedicamento).collect(Collectors.toList()));

        adapter.setData(medicamentos, registros,esPasado,esFuturo);

        if(registros.isEmpty()) binding.layoutVacio.setVisibility(LinearLayoutCompat.VISIBLE);
        else binding.layoutVacio.setVisibility(LinearLayoutCompat.GONE);

    }

    public void setRegistrosFuturos(ZonedDateTime fecha) //Muestra registros futuros pero NO LOS GUARDA, de eso se encarga la alarma. Esto es solo para visualizar
    {
        medicamentos.clear();
        List<Medicamento> aux = new ArrayList<>();
        registros.clear();
        medicamentoRepo.getAll((result, values) ->
        {
            if(result) aux.addAll(values);
        });
        for (Medicamento medicamento : aux)
        {
            if(medicamento.getFechaInicio().isAfter(fecha)) continue;
            switch (medicamento.getFrecuencia())
            {
                case INTERVALO_REGULAR:
                    long dias = ChronoUnit.DAYS.between(medicamento.getFechaInicio(),fecha);
                    if(dias % Integer.parseInt(medicamento.getDias()) == 0)
                    {
                        Registro registro = new Registro();
                        registro.setMedicamento(medicamento);
                        registro.setEstado(RegistroEstado.PENDIENTE);
                        registro.setFecha(fecha);
                        registros.add(registro);
                    }
                    break;

                case DIAS_ESPECIFICOS:
                    List<DayOfWeek> diasSemana = FechaFormat.toDiasSemana(medicamento.getDias());
                    if(diasSemana.contains(fecha.getDayOfWeek()))
                    {
                        Registro registro = new Registro();
                        registro.setMedicamento(medicamento);
                        registro.setEstado(RegistroEstado.PENDIENTE);
                        registro.setFecha(fecha);
                        registros.add(registro);
                    }
                    break;

                case TODOS_DIAS:
                    Registro registro = new Registro();
                    registro.setMedicamento(medicamento);
                    registro.setEstado(RegistroEstado.PENDIENTE);
                    registro.setFecha(fecha);
                    registros.add(registro);
                    break;
            }
        }

        medicamentos.addAll(registros.stream().map(Registro::getMedicamento).collect(Collectors.toList()));

        adapter.setData(medicamentos, registros,false,true);

        if(registros.isEmpty()) binding.layoutVacio.setVisibility(LinearLayoutCompat.VISIBLE);
        else binding.layoutVacio.setVisibility(LinearLayoutCompat.GONE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}