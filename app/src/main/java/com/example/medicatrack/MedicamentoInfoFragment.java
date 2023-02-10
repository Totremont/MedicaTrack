package com.example.medicatrack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import com.example.medicatrack.adapters.MainContentAdapter;
import com.example.medicatrack.databinding.MedicamentoInfoBinding;
import com.example.medicatrack.model.Medicamento;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MedicamentoInfoFragment extends Fragment {

    private MedicamentoInfoBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = MedicamentoInfoBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
