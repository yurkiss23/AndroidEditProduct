package com.example.cwork.productview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cwork.R;
import com.example.cwork.network.ImageRequester;
import com.example.cwork.network.ProductEntry;
import com.example.cwork.productview.click_listeners.OnDeleteListener;
import com.example.cwork.productview.click_listeners.OnEditListener;

import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */
public class ProductCardRecyclerViewAdapter extends RecyclerView.Adapter<ProductCardViewHolder> {

    private List<ProductEntry> productList;
    private ImageRequester imageRequester;
    private OnDeleteListener deleteListener;
    private OnEditListener editListener;
    private Context context;

    ProductCardRecyclerViewAdapter(List<ProductEntry> productList,
                                   OnDeleteListener deleteListener,
                                   OnEditListener editListener,
                                   Context context) {
        this.productList = productList;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
        this.context = context;
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,
                parent, false);
        return new ProductCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {
        if (productList != null && position < productList.size()) {
            ProductEntry product = productList.get(position);
            holder.productTitle.setText(product.title);
            holder.productPrice.setText(product.price);
            imageRequester.setImageFromUrl(holder.productImage, product.url);

            holder.getView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteListener.deleteItem(productList.get(position));
                    return true;
                }
            });

            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editListener.editItem(productList.get(position).id);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
