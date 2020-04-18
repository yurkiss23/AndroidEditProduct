package com.example.cwork.productview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.cwork.BaseActivity;
import com.example.cwork.NavigationHost;
import com.example.cwork.R;
import com.example.cwork.network.ImageRequester;
import com.example.cwork.utils.CommonUtils;
import com.example.cwork.utils.FileUtil;
import com.example.cwork.productview.network.ProductDTOService;
import com.example.cwork.productview.dto.ProductDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductEditActivity extends BaseActivity {
    public static final int PICKFILE_RESULT_CODE = 1;

    private EditText editTextTitle;
    private EditText editTextPrice;
    private NetworkImageView editImage;
    private Button buttonEdit;
    private Button buttonCancel;
    private int id;

    private ImageRequester imageRequester;
    private Button btnSelectImage;
    private String chooseImageBase64;
    private ProductDTO productDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);
        imageRequester = ImageRequester.getInstance();

        setupViews();
        initProduct();
        setButtonCancelListener();
        setButtonEditListener();
        setBtnSelectImageListener();
    }

    private void setupViews() {
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextPrice = findViewById(R.id.editTextPrice);
        editImage = findViewById(R.id.chooseImage);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonCancel = findViewById(R.id.buttonCancel);
        btnSelectImage = findViewById(R.id.btnSelectImage);
    }

    private void initProduct() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra(ConstantIds.PRODUCT_INTENT_ID, 0);
        }
        //      Toast.makeText(getApplicationContext(),String.valueOf(id),Toast.LENGTH_LONG).show();
        ProductDTOService.getInstance()
                .getJSONApi()
                .getEditProduct(id)
                .enqueue(new Callback<ProductDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<ProductDTO> call, @NonNull Response<ProductDTO> response) {
                        CommonUtils.hideLoading();
                        if (response.isSuccessful()) {
                            productDTO = response.body();
                            editTextTitle.setText(productDTO.getTitle());
                            editTextPrice.setText(productDTO.getPrice());
                            editImage = findViewById(R.id.chooseImage);
                            imageRequester.setImageFromUrl(editImage, productDTO.getUrl());

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ProductDTO> call, @NonNull Throwable t) {
                        CommonUtils.hideLoading();
                        Log.e("ERROR", "*************ERORR request***********");
                        t.printStackTrace();

                    }
                });
    }

    private void setButtonCancelListener() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void setButtonEditListener() {
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString().trim();
                String price = editTextPrice.getText().toString().trim();
                String base64 = chooseImageBase64;
                productDTO.setTitle(title);
                productDTO.setPrice(price);
                productDTO.setImageBase64(base64);


                ProductDTOService.getInstance()
                        .getJSONApi()
                        .editProduct(productDTO)
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

                                CommonUtils.hideLoading();
                                if (response.isSuccessful()) {
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                //CommonUtils.hideLoading();
                                Log.e("ERROR", "*************ERORR request***********");
                                t.printStackTrace();
                                CommonUtils.hideLoading();
                            }
                        });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    Uri fileUri = data.getData();
                    try {
                        File imgFile = FileUtil.from(getApplicationContext(), fileUri);
                        byte[] buffer = new byte[(int) imgFile.length() + 100];
                        int length = new FileInputStream(imgFile).read(buffer);
                        chooseImageBase64 = Base64.encodeToString(buffer, 0, length, Base64.NO_WRAP);
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        editImage.setImageBitmap(myBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void setBtnSelectImageListener() {
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*");
                chooseFile = Intent.createChooser(chooseFile, "Оберіть фото");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });
    }

    @Override
    public boolean isConnect(Response response) {
        return false;
    }
}
