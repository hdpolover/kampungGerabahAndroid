package com.nurali.kampunggerabah.fragments.transaksi;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nurali.kampunggerabah.fragments.AsbakProdukFragment;
import com.nurali.kampunggerabah.fragments.CelenganProdukFragment;
import com.nurali.kampunggerabah.fragments.CobekProdukFragment;
import com.nurali.kampunggerabah.fragments.KendiProdukFragment;
import com.nurali.kampunggerabah.fragments.LampionProdukFragment;
import com.nurali.kampunggerabah.fragments.VasBungaProdukFragment;

public class TransaksiDaftarAdapter extends FragmentStateAdapter {
    public TransaksiDaftarAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return  new TransaksiSelesaiFragment();
        }

        return new TransaksiBerlangsungFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}