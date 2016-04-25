package com.project.markodobrinic1gmailcom.kupnjam.model.helper;

import android.widget.Filter;

import com.project.markodobrinic1gmailcom.kupnjam.model.adapter.ProductAdapter;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marko.dobrinic1@gmail.com on 25.4.2016..
 */
public class ProductFilter extends Filter {

    private final ProductAdapter productAdapter;

    private final List<Product> originalProducts;
    private final List<Product> filteredProducts;

    public ProductFilter(ProductAdapter adapter, List<Product> original, List<Product> filtered) {
        this.productAdapter = adapter;
        this.originalProducts = new ArrayList<>(original);
        this.filteredProducts = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredProducts.clear();
        final FilterResults results = new FilterResults();

        if(constraint == null || constraint.length() == 0){
            filteredProducts.addAll(originalProducts);
        }else{
            final String filterPattern = constraint.toString().toLowerCase().trim();

            for(final Product product : originalProducts){
                if(product.getName().toLowerCase().contains(filterPattern)){
                    filteredProducts.add(product);
                }
            }
        }

        results.values = filteredProducts;
        results.count = filteredProducts.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        productAdapter.mProducts = originalProducts;
        if(results.count > 0){
            productAdapter.mFiltered.clear();
            productAdapter.mFiltered.addAll((ArrayList<Product>) results.values);
            productAdapter.notifyDataSetChanged();
        }else{
            productAdapter.mFiltered.clear();
            productAdapter.mFiltered.addAll(productAdapter.mProducts);
            productAdapter.notifyDataSetChanged();
        }
    }
}
