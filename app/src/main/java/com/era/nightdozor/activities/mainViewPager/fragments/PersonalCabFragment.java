package com.era.nightdozor.activities.mainViewPager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.era.nightdozor.R;
import com.era.nightdozor.databinding.FragmentPersonalCabBinding;

public class PersonalCabFragment extends Fragment {

    private FragmentPersonalCabBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal_cab, container, false);



        return binding.getRoot();
    }
}
