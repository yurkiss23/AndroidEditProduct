package com.example.cwork;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cwork.utils.CommonUtils;
import com.example.cwork.utils.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SportNewsFragment extends Fragment implements SportAdapter.Callback {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    SportAdapter mSportAdapter;

    LinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mRecyclerView = (RecyclerView) mRecyclerView.findViewById(R.id.mRecyclerView);
//
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
////        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
//        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sport_news, container, false);
        ButterKnife.bind(getActivity());

        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        setUp();
        return view;
    }

    private void setUp() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mSportAdapter = new SportAdapter(new ArrayList<>());

        prepareDemoContent();
    }

    private void prepareDemoContent() {
        CommonUtils.showLoading(getActivity());
        new Handler().postDelayed(() -> {
            //prepare data and show loading
            CommonUtils.hideLoading();
            ArrayList<Sport> mSports = new ArrayList<>();
            String[] sportsList = getResources().getStringArray(R.array.sports_titles);
            String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
            String[] sportsImage = getResources().getStringArray(R.array.sports_images);
            for (int i = 0; i < sportsList.length; i++) {
                mSports.add(new Sport(sportsImage[i], sportsInfo[i], "News", sportsList[i]));
            }
            mSportAdapter.addItems(mSports);
            mRecyclerView.setAdapter(mSportAdapter);
        }, 2000);
    }

    @Override
    public void onEmptyViewRetryClick() { prepareDemoContent(); }
}
