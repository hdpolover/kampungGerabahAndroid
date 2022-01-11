package com.nurali.kampunggerabah.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class KatalogProdukAdapter extends FragmentStateAdapter {
    public KatalogProdukAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return  new VasBungaProdukFragment();
            case 2:
                return  new KendiProdukFragment();
            case 3:
                return  new AsbakProdukFragment();
            case 4:
                return  new LampionProdukFragment();
            case 5:
                return  new CelenganProdukFragment();
        }

        return new CobekProdukFragment();
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}