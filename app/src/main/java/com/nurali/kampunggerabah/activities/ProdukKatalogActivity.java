package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.databinding.ActivityAdminDaftarUmkmBinding;
import com.nurali.kampunggerabah.databinding.ActivityProdukDetailBinding;
import com.nurali.kampunggerabah.databinding.ActivityProdukKatalogBinding;
import com.nurali.kampunggerabah.fragments.KatalogProdukAdapter;

public class ProdukKatalogActivity extends AppCompatActivity {

    ActivityProdukKatalogBinding binding;

    ApiInterface apiInterface;

    KatalogProdukAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProdukKatalogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        FragmentManager fm = getSupportFragmentManager();
        adapter = new KatalogProdukAdapter(fm, getLifecycle());
        binding.viewPageTab.setAdapter(adapter);

        binding.tabV.addTab(binding.tabV.newTab().setText("Cobek"));
        binding.tabV.addTab(binding.tabV.newTab().setText("Vas Bunga"));
        binding.tabV.addTab(binding.tabV.newTab().setText("Kendi"));
        binding.tabV.addTab(binding.tabV.newTab().setText("Asbak"));
        binding.tabV.addTab(binding.tabV.newTab().setText("Lampion"));
        binding.tabV.addTab(binding.tabV.newTab().setText("Celengan"));

        binding.tabV.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPageTab.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.viewPageTab.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabV.selectTab(binding.tabV.getTabAt(position));
            }
        });
    }
}