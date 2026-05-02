package com.iesaguadulce.mirecetariodecocina;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iesaguadulce.mirecetariodecocina.databinding.FragmentViewRecetaBinding;

public class ViewRecetaFragment extends Fragment {

    private FragmentViewRecetaBinding binding;
    private NavController navController;

    public ViewRecetaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentViewRecetaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        binding.btnIngredients.setOnClickListener(v -> {
            //navController.navigate(R.id.action_viewRecetaFragment_to_viewIngredientsRecipeFragment);
        });
        binding.btnHowtomake.setOnClickListener(v -> {
            //navController.navigate(R.id.action_viewRecetaFragment_to_viewHowToMakeFragment);
        });
        binding.btnAddRecipeComment.setOnClickListener(v -> {
            //navController.navigate(R.id.action_viewRecetaFragment_to_dataCommentsRecipeFragment);
        });
        binding.btnRecipeClose.setOnClickListener(v -> {
            //navController.navigate(R.id.action_viewRecetaFragment_to_inicioCookFragment);
        });
    }
}