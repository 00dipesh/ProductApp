package com.practise.productapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WebServices {

    @GET("products")
    Call<ItemModel> ProductList();

    @GET("products/{id}")
    Call<ProductModel> getProductDetails(@Path("id") int productId);
}
