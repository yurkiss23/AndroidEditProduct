package com.example.cwork.productview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cwork.NavigationHost;
import com.example.cwork.R;
import com.example.cwork.productview.dto.ProductCreateDTO;
import com.example.cwork.productview.dto.ProductCreateSuccessDTO;
import com.example.cwork.productview.network.ProductDTOService;
import com.example.cwork.utils.CommonUtils;
import com.example.cwork.utils.FileUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    public static final int PICKFILE_RESULT_CODE = 1;
    ImageView chooseImage;
    String chooseImageBase64;
    Button btnSelectImage;
    Button addButton;

    TextInputEditText titleEditText;
    TextInputEditText priceEditText;
    TextView errormessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_create, container, false);

        setupViews(view);
        setButtonSelectImageListener();
        setButtonAddListener();
        return view;
    }

    private void setupViews(View view) {
        btnSelectImage = view.findViewById(R.id.btnSelectImage);
        chooseImage = (ImageView) view.findViewById(R.id.chooseImage);
        addButton = view.findViewById(R.id.add_button);

        titleEditText = view.findViewById(R.id.product_title_edit_text);
        priceEditText = view.findViewById(R.id.price_edit_text);
        errormessage = view.findViewById(R.id.error_message);
    }

    private void setButtonSelectImageListener() {
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*");
                chooseFile = Intent.createChooser(chooseFile, "Оберіть малюнок продукта");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });
    }

    private void setButtonAddListener() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductCreateDTO productCreateDTO=new ProductCreateDTO(titleEditText.getText().toString(),
                        priceEditText.getText().toString(), chooseImageBase64);
                CommonUtils.showLoading(getActivity());
                ProductDTOService.getInstance()
                        .getJSONApi()
                        .CreateRequest(productCreateDTO)
                        .enqueue(new Callback<ProductCreateSuccessDTO>() {
                            @Override
                            public void onResponse(Call<ProductCreateSuccessDTO> call, Response<ProductCreateSuccessDTO> response) {
                                CommonUtils.hideLoading();
                                errormessage.setText("");
                                if(response.isSuccessful())
                                {
                                    ProductCreateSuccessDTO res=response.body();
                                    ((NavigationHost)getActivity()).navigateTo(new ProductGridFragment(),false);
                                }
                                else {
                                    //work error
                                }
                            }

                            @Override
                            public void onFailure(Call<ProductCreateSuccessDTO> call, Throwable t) {
                                CommonUtils.hideLoading();
                            }
                        });
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    Uri fileUri = data.getData();
                    try {
                        File imgFile = FileUtil.from(this.getActivity(), fileUri);
                        byte[] buffer = new byte[(int) imgFile.length() + 100];
                        int length = new FileInputStream(imgFile).read(buffer);
                        chooseImageBase64 = Base64.encodeToString(buffer, 0, length, Base64.NO_WRAP);
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        chooseImage.setImageBitmap(myBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
