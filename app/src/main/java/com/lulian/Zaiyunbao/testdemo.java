package com.lulian.Zaiyunbao;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class testdemo {
    private void showData(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("")
                .build();

        client.dispatcher().setMaxRequests(12);


        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });


    }

}
