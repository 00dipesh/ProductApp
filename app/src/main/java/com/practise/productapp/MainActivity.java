package com.practise.productapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    FrameLayout frameLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =findViewById(R.id.Rv_Product);
        progressBar = findViewById(R.id.Loder);
        frameLayout = findViewById(R.id.blur);
        ProductList();


    }

    private void ProductList() {
        progressBar.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.VISIBLE);
        Call<ItemModel> listCall = Constant.getWebService().ProductList();
        listCall.enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.GONE);
                    ItemModel itemModel = (ItemModel) response.body();
                    if (itemModel != null && itemModel.getProducts() != null && !itemModel.getProducts().isEmpty()) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        itemAdapter = new ItemAdapter(MainActivity.this, itemModel.getProducts());
                        recyclerView.setAdapter(itemAdapter);
                        recyclerView.setHasFixedSize(true);
                    } else {
                        Toast.makeText(MainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        frameLayout.setVisibility(View.GONE);
                    }
                } else {
                    handleError(response.code());
                    progressBar.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ItemModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Please Check Your Internet Connection..!!!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void handleError(int errorCode) {
        switch (errorCode) {
            case 404:
                Toast.makeText(MainActivity.this, "Resource not found", Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(MainActivity.this, "Internal Server Error", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(MainActivity.this, "Error: " + errorCode, Toast.LENGTH_SHORT).show();
                break;
        }
    }

}