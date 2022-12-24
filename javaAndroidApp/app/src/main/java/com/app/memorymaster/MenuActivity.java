package com.app.memorymaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.app.memorymaster.databinding.ActivityMainBinding;
import com.app.memorymaster.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {
    ActivityMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigationView2.setOnItemSelectedListener(
                item -> {
                    switch (item.getItemId()){

                        case R.id.Single:
                            replaceFragment(new HomeFragment());
                            break;
                        case R.id.Multi:
                            startActivity(new Intent(MenuActivity.this, RoomsActivity.class));
                            break;
                        case R.id.profile:
                            replaceFragment(new ProfileFragment());
                            break;

                    }
                    return  true;
                }
        );

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout3,fragment);
        fragmentTransaction.commit();

    }
}