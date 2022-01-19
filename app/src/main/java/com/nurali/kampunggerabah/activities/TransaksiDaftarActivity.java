package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.databinding.ActivityProdukKatalogBinding;
import com.nurali.kampunggerabah.databinding.ActivityTransaksiDaftarBinding;
import com.nurali.kampunggerabah.fragments.KatalogProdukAdapter;
import com.nurali.kampunggerabah.fragments.transaksi.TransaksiDaftarAdapter;

public class TransaksiDaftarActivity extends AppCompatActivity {

    ActivityTransaksiDaftarBinding binding;

    ApiInterface apiInterface;

    TransaksiDaftarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransaksiDaftarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        FragmentManager fm = getSupportFragmentManager();
        adapter = new TransaksiDaftarAdapter(fm, getLifecycle());
        binding.viewPageTab.setAdapter(adapter);

        binding.tabV.addTab(binding.tabV.newTab().setText("Berlangsung"));
        binding.tabV.addTab(binding.tabV.newTab().setText("Selesai"));

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