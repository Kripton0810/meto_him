package com.kripton.flyaway;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class testforpin extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testforpin);
        RecyclerView rec;
        Intent pin = getIntent();
        String code = pin.getExtras().getString("pin");
        LocalDate today = LocalDate.now();
        LocalDate tomoro = today.plusDays(1);
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<recycleViewModel> adapterClassList = new ArrayList<>();
        List<vaccinModelClass> list = new ArrayList<>();
        rec = findViewById(R.id.rec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rec.setLayoutManager(layoutManager);
        String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=" + code + "&date=" + formatter.format(today);
        Log.d("url",url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).get()
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {

                    String ret = response.body().string();
                    Log.d("return",ret);
                    JSONObject object = new JSONObject(ret);
                    JSONArray array = object.getJSONArray("sessions");
                    Log.d("size",String.valueOf(array.length()));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        String add1 = obj.getString("name");
                        String add2 = obj.getString("block_name") + obj.getString("district_name") + obj.getString("state_name") + obj.getString("pincode");
                        String d1 = obj.getString("available_capacity_dose1");
                        String d2 = obj.getString("available_capacity_dose2");
                        String age = obj.getString("min_age_limit");
                        String vaccine = obj.getString("vaccine");
                        String all = add1+add2+d1+d2+age+vaccine+"\n";
                        Log.d("all",all);
                        list.add(new vaccinModelClass(add1, add2, d1, d2, vaccine, age,"0"));
                    }
                    testforpin.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            vaccineAdapterClass adapterClass = new vaccineAdapterClass(list);
                            rec.setAdapter(adapterClass);
                            adapterClass.notifyDataSetChanged();

                        }
                    });


                } catch (Exception e) {

                }
            }
        });
    }
}