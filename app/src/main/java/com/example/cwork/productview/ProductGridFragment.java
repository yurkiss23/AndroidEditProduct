package com.example.cwork.productview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cwork.CallbackFailureFragment;
import com.example.cwork.NavigationHost;
import com.example.cwork.R;
import com.example.cwork.network.ProductEntry;
import com.example.cwork.productview.click_listeners.OnDeleteListener;
import com.example.cwork.productview.click_listeners.OnEditListener;
import com.example.cwork.productview.dto.ProductDTO;
import com.example.cwork.productview.network.ProductDTOService;
import com.example.cwork.utils.CommonUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductGridFragment extends Fragment implements OnEditListener, OnDeleteListener {

    private static final String TAG = ProductGridFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private Button addButton, editButton;
    private List<ProductEntry> listProductEntry;
    private ProductCardRecyclerViewAdapter productAdapter;

    private final int REQUEST_CODE_EDIT = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_grid, container, false);

        // Set up the RecyclerView
        setupViews(view);
        setRecyclerView();
        setButtonAddListener();
//        setButtonEditListener();
        loadProductEntryList();

        return view;
    }

    private void setupViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        addButton = view.findViewById(R.id.add_button);
        editButton = view.findViewById(R.id.edit_button);

//        listProductEntry=new ArrayList<>();
//        productAdapter = new ProductCardRecyclerViewAdapter(listProductEntry, this, this);
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        listProductEntry=new ArrayList<>();
        productAdapter = new ProductCardRecyclerViewAdapter(listProductEntry, this, this, getContext());

        recyclerView.setAdapter(productAdapter);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
    }

    private void setButtonAddListener() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new ProductCreateFragment(), true);
            }
        });
    }

//    private void setButtonEditListener(){
//        editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "------EDIT-----");
//            }
//        });
//    }

    private void loadProductEntryList() {
        CommonUtils.showLoading(getActivity());
        ProductDTOService.getInstance()
                .getJSONApi()
                .getAllProducts()
                .enqueue(new Callback<List<ProductDTO>>() {
                    @Override
                    public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                        if(response.isSuccessful()) {
                            Log.d(TAG, "-------------Response OK-------------");
//                        switch (response.code()){
//                            case 200:
//                                Log.d(TAG, Integer.toString(response.code()));
//                                List<ProductDTO> list = response.body();
//                                List<ProductEntry> newlist = new ArrayList<ProductEntry>();//ProductEntry.initProductEntryList(getResources());
//                                for (ProductDTO item : list) {
//                                    ProductEntry pe = new ProductEntry(item.getTitle(),item.getUrl(),item.getUrl(), item.getPrice(),"sdfasd");
//                                    newlist.add(pe);
//                                }
//                                ProductCardRecyclerViewAdapter newAdapter = new ProductCardRecyclerViewAdapter(newlist);
//                                recyclerView.swapAdapter(newAdapter, false);
//                                break;
//                            case 403:
//                                Log.d(TAG, Integer.toString(response.code()));
//                                ((NavigationHost)getActivity())
//                                        .navigateTo(new CallbackFailureFragment(
//                                                "Доступ заборонено"), false);
//                                break;
//                            case 404:
//                                Log.d(TAG, Integer.toString(response.code()));
//                                ((NavigationHost)getActivity())
//                                        .navigateTo(new CallbackFailureFragment(
//                                                "Ресурс не знайдено"), false);
//                                break;
//                            case 500:
//                                Log.d(TAG, Integer.toString(response.code()));
//                                ((NavigationHost)getActivity())
//                                        .navigateTo(new CallbackFailureFragment(
//                                                "Внутрішня помилка серверу"), false);
//                                break;
//                            default:
//                                Log.d(TAG, Integer.toString(response.code()));
//                                break;
//                        }
                            if (!((NavigationHost) getActivity()).isConnect(response)) {
                                CommonUtils.hideLoading();
                                return;
                            }
                            listProductEntry.clear();
                            List<ProductDTO> list = response.body();
                            for (ProductDTO item : list) {
                                ProductEntry pe = new ProductEntry(item.getId(), item.getTitle(), item.getUrl(), item.getUrl(), item.getPrice(), "sdfasd");
                                listProductEntry.add(pe);
                            }
                            productAdapter.notifyDataSetChanged();
                        }
                        else {
                            //  Toast.makeText(getContext(), "Проблема при отримані даних",Toast.LENGTH_LONG).show();
                        }
                        CommonUtils.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                        Log.e(TAG, "----------Bad Request----------");
                        ((NavigationHost)getActivity())
                                .navigateTo(new CallbackFailureFragment(
                                        "З'єднання розірвано"), false);
                        CommonUtils.hideLoading();
                    }
                });
    }

    private void deleteConfirm(final ProductEntry productEntry) {
        CommonUtils.showLoading(getContext());
        ProductDTOService.getInstance()
                .getJSONApi()
                .DeleteRequest(productEntry.id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        CommonUtils.hideLoading();

                        if (response.isSuccessful()) {
                            listProductEntry.remove(productEntry);
                            productAdapter.notifyDataSetChanged();
                        } else {
                            //  Log.e(TAG, "_______________________" + response.errorBody().charStream());

                            try {
//                                                String json = response.errorBody().string();
//                                                Gson gson  = new Gson();
//                                                ProductCreateInvalidDTO resultBad = gson.fromJson(json, ProductCreateInvalidDTO.class);
                                //Log.d(TAG,"++++++++++++++++++++++++++++++++"+response.errorBody().string());
                                //errormessage.setText(resultBad.getInvalid());
                            } catch (Exception e) {
                                //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        CommonUtils.hideLoading();
                        Log.e("ERROR", "*************ERORR request***********");
                        t.printStackTrace();

                    }
                });
    }

    @Override
    public void deleteItem(final ProductEntry productEntry) {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Видалення")
                .setMessage("Ви дійсно бажаєте видалити \"" + productEntry.title + "\"?")
                .setNegativeButton("Скасувати", null)
                .setPositiveButton("Видалити", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //listProductEntry.remove(productEntry);
                        //productAdapter.notifyDataSetChanged();
                        deleteConfirm(productEntry);
                    }
                })
                .show();
    }

    @Override
    public void editItem(int id) {
//        Toast.makeText(getActivity(), "Edit", Toast.LENGTH_LONG).show();
//        editButton.setEnabled(true);
        Intent intent = new Intent(getActivity(), ProductEditActivity.class);
        intent.putExtra(ConstantIds.PRODUCT_INTENT_ID, id);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EDIT) {
            if (resultCode == -1) {
                loadProductEntryList();
            }
        }
    }

    public boolean isConnect(Response response){

        switch (response.code()){
            case 200:
                Log.d(TAG, Integer.toString(response.code()));
//                List<ProductDTO> list = response.body();
//                List<ProductEntry> newlist = new ArrayList<ProductEntry>();//ProductEntry.initProductEntryList(getResources());
//                for (ProductDTO item : list) {
//                    ProductEntry pe = new ProductEntry(item.getTitle(),item.getUrl(),item.getUrl(), item.getPrice(),"sdfasd");
//                    newlist.add(pe);
//                }
//                ProductCardRecyclerViewAdapter newAdapter = new ProductCardRecyclerViewAdapter(newlist);
//                recyclerView.swapAdapter(newAdapter, false);
                return true;
            case 403:
                Log.d(TAG, Integer.toString(response.code()));
                ((NavigationHost)getActivity())
                        .navigateTo(new CallbackFailureFragment(
                                "Доступ заборонено"), false);
                break;
            case 404:
                Log.d(TAG, Integer.toString(response.code()));
                ((NavigationHost)getActivity())
                        .navigateTo(new CallbackFailureFragment(
                                "Ресурс не знайдено"), false);
                break;
            case 500:
                Log.d(TAG, Integer.toString(response.code()));
                ((NavigationHost)getActivity())
                        .navigateTo(new CallbackFailureFragment(
                                "Внутрішня помилка серверу"), false);
                break;
            default:
                Log.d(TAG, Integer.toString(response.code()));
                break;
        }

        return false;
    }

}
