package com.nurali.kampunggerabah.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nurali.kampunggerabah.R;
import com.nurali.kampunggerabah.adapters.ProdukAdapter;
import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.responses.ProdukResponse;
import com.nurali.kampunggerabah.preferences.AppPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KendiProdukFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rv;
    View view;
    ApiInterface apiInterface;

    String kategori = "kendi";
    LinearLayout noData;

    public KendiProdukFragment() {
        // Required empty public constructor
    }

    public static KendiProdukFragment newInstance(String param1, String param2) {
        KendiProdukFragment fragment = new KendiProdukFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_container_kendi, container, false);

        rv  = view.findViewById(R.id.rv);
        apiInterface = ApiClient.getClient();
        noData = view.findViewById(R.id.noDataLayout);

        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setHasFixedSize(true);

        tampilProduk(kategori);

        return view;
    }

    private void tampilProduk(String kategori) {
        if (AppPreference.getUser(getContext()).peran.equals("customer")) {
            apiInterface.getKategoriProdukCustomer(kategori).enqueue(new Callback<ProdukResponse>() {
                @Override
                public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                    if (response.body().status) {
                        List<ProdukResponse.ProdukModel> list = new ArrayList<>();

                        list.addAll(response.body().data);

                        rv.setAdapter(new ProdukAdapter(list, getContext()));

                        if (list.isEmpty()) {
                            noData.setVisibility(View.VISIBLE);
                        } else {
                            noData.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProdukResponse> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            apiInterface.getKategoriProduk(kategori).enqueue(new Callback<ProdukResponse>() {
                @Override
                public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                    if (response.body().status) {
                        List<ProdukResponse.ProdukModel> list = new ArrayList<>();

                        list.addAll(response.body().data);

                        rv.setAdapter(new ProdukAdapter(list, getContext()));

                        if (list.isEmpty()) {
                            noData.setVisibility(View.VISIBLE);
                        } else {
                            noData.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProdukResponse> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}