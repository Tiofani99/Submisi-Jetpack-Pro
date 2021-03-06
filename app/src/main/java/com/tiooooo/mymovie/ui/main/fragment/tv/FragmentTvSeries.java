package com.tiooooo.mymovie.ui.main.fragment.tv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tiooooo.mymovie.R;
import com.tiooooo.mymovie.data.source.TvSeriesResponse;
import com.tiooooo.mymovie.viewmodel.ViewModelFactory;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTvSeries extends Fragment {

    private TvSeriesAdapter adapter;


    @BindView(R.id.rv_tv_series)
    RecyclerView rvTvSeries;
    @BindView(R.id.tv_information)
    TextView tvInformationData;
    @BindView(R.id.shimmerFrameLayout)
    ShimmerFrameLayout shimmerFrameLayout;


    public FragmentTvSeries() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_series, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if(getActivity()!= null){
            initAdapter();
            getTvSeries();
        }

    }

    private void initAdapter() {
        rvTvSeries.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new TvSeriesAdapter(this.getContext());
        adapter.notifyDataSetChanged();
    }

    private void showLoading(Boolean state) {
        if (state) {
            shimmerFrameLayout.setVisibility(View.VISIBLE);
        } else {
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    private void getTvSeries() {
        showLoading(true);
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
        TvSeriesViewModel viewModel = new ViewModelProvider(this, factory).get(TvSeriesViewModel.class);
        viewModel.getTvSeries().observe(getViewLifecycleOwner(),tvSeries -> {
            adapter.setTvSeries((ArrayList<TvSeriesResponse>) tvSeries);
            rvTvSeries.setAdapter(adapter);
        });
        showLoading(false);

    }
}
