package com.practise.productapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {

     TextView Txt_productName, Txt_productDescription, Txt_productPrice,Txt_discount,Txt_stock,Txt_brand,Txt_category,Txt_Rating;
    ViewPager2 viewPager;
    private ImageSliderAdapter sliderAdapter;
    RatingBar ratingBar;
    int productId;
    ImageView backbutton;
    FrameLayout frameLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Txt_productName = findViewById(R.id.Txt_Product);
        Txt_productDescription = findViewById(R.id.textViewDescription);
        Txt_productPrice = findViewById(R.id.textViewPrice);
        Txt_discount = findViewById(R.id.textViewDiscount);
        Txt_stock = findViewById(R.id.textViewStock);
        Txt_category = findViewById(R.id.textViewCategory);
        Txt_brand = findViewById(R.id.textViewBrand);
        viewPager = findViewById(R.id.Pro_image);
        Txt_Rating = findViewById(R.id.textViewRating);
        backbutton = findViewById(R.id.iv_back);
        progressBar = findViewById(R.id.Loder);
        frameLayout = findViewById(R.id.blur);




        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(ProductDetailsActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        ratingBar = findViewById(R.id.ratingBar);


         productId = getIntent().getIntExtra("productId", 0);

        ProductDetail();
    }

    public void ProductDetail(){
        progressBar.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.VISIBLE);
        Call<ProductModel> modelCall =Constant.getWebService().getProductDetails(productId);
        modelCall.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.GONE);
                    ProductModel product = response.body();

                    Txt_productName.setText(product.getTitle());
                    Txt_productDescription.setText(product.getDescription());
                    Txt_productPrice.setText("Rs." + product.getPrice());
                    Txt_discount.setText(String.valueOf(product.getDiscountPercentage() + "%OFF"));
                    Txt_stock.setText(String.valueOf(product.getStock()));
                    Txt_category.setText(product.getCategory());
                    Txt_brand.setText(product.getBrand());
                   // ratingBar.setRating(product.getRating());
                    Txt_Rating.setText(String.valueOf(product.getRating()+"  rating"));
                    List<String> imageUrls = product.getImages();
                    sliderAdapter = new ImageSliderAdapter(imageUrls);
                    viewPager.setAdapter(sliderAdapter);
                } else {
                    Toast.makeText(ProductDetailsActivity.this, "No data available", Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Please Check Your Internet Connection..!!!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
            }
        });

    }
}