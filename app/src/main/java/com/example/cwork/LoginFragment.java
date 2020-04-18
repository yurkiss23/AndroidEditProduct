package com.example.cwork;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cwork.account.AccountService;
import com.example.cwork.account.JwtServiceHolder;
import com.example.cwork.account.LoginDTO;
import com.example.cwork.account.LoginDTOBadRequest;
import com.example.cwork.account.TokenDTO;
import com.example.cwork.productview.ProductGridFragment;
import com.example.cwork.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


///**
// * A simple {@link Fragment} subclass.
// * Use the {@link LoginFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class LoginFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

//    public LoginFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment LoginFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static LoginFragment newInstance(String param1, String param2) {
//        LoginFragment fragment = new LoginFragment();
//        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button loginButton = view.findViewById(R.id.btn_login);
        Button btnRegister = view.findViewById(R.id.btn_register);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);
        final TextInputLayout emailTextInput = view.findViewById(R.id.email_text_input);
        final TextInputEditText emailEditText = view.findViewById(R.id.email_edit_text);
        TextView errorMessage = view.findViewById(R.id.error_message);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError("Пароль має бути 8 символів!");
                } else {
                    passwordTextInput.setError(null);
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    LoginDTO loginDTO = new LoginDTO(email, password);
                    errorMessage.setText("");
                    AccountService.getInstance()
                            .getJSONApi()
                            .loginRequest(loginDTO)
                            .enqueue(new Callback<TokenDTO>() {
                                @Override
                                public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                                    if (response.isSuccessful()){
                                        TokenDTO token = response.body();
                                        Log.i("-----------", token.toString());
                                        ((JwtServiceHolder) getActivity())
                                                .saveJWTToken(token.getToken()); // Navigate to the register Fragment
                                        ((NavigationHost) getActivity())
                                                .navigateTo(new ProductGridFragment(), false); // Navigate to the products Fragment
                                    }else {
                                        Log.e("------!!!-----", "bad request");
                                        try {
                                            String json = response.errorBody().string();
                                            Gson gson = new Gson();
                                            LoginDTOBadRequest resultBad = gson.fromJson(json, LoginDTOBadRequest.class);
                                            //Log.d(TAG,"++++++++++++++++++++++++++++++++"+response.errorBody().string());
                                            errorMessage.setText(resultBad.getInvalid());
//                                            errorMessage.setText(json);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<TokenDTO> call, Throwable t) {

                                }
                            });
//                    CommonUtils.showLoading(getActivity());
//                    uploadData();
//                    ((NavigationHost)getActivity()).navigateTo(new ProductGridFragment(), false);
                }
            }
        });

        // Clear the error once more than 8 characters are typed.
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(null); //Clear the error
                    //return true;
                }
                return false;
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost)getActivity()).navigateTo(new RegisterFragment(), false);
            }
        });

        return view;
    }

    private boolean isPasswordValid(@NonNull Editable text) { return text != null && text.length() >= 8; }

    public String uploadData(){
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    CommonUtils.hideLoading();
                    ((NavigationHost)getActivity()).navigateTo(new SportNewsFragment(), false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        return null;
    }
}
