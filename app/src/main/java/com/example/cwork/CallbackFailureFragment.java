package com.example.cwork;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cwork.productview.ProductGridFragment;

public class CallbackFailureFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
    private String s;

    // TODO: Rename and change types of parameters
    private String mParam1;
//    private String mParam2;

    public CallbackFailureFragment() {
        // Required empty public constructor
    }

    public CallbackFailureFragment(String str) {
        s = str;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallbackFailureFragment newInstance(String param1) {
        CallbackFailureFragment fragment = new CallbackFailureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_callback_failure, container, false);

        TextView tv = view.findViewById(R.id.reload);
        tv.setText(s);

        Button reload = view.findViewById(R.id.btn_reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost)getActivity()).navigateTo(new ProductGridFragment(), false);
            }
        });

        return view;
    }
}
