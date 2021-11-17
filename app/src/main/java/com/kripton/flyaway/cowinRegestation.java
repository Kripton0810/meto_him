package com.kripton.flyaway;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class cowinRegestation extends AppCompatActivity {
    HashMap<String,String> stateid = new HashMap<>();
    List<String> list = new ArrayList<String>();
    List<String> d = new ArrayList<>();
    TextView tv;
    HashMap<String,String> dismap = new HashMap<>();
    static String stateslist;
    TextView dist;
    TextView state;
    Dialog dialog;
    DatePicker picker;
    ProgressDialog p;
    Button dp,pin_search;
    String str = "";
    EditText pin_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cowin_regestation);
        pin_code = findViewById(R.id.pin_code);
        pin_search = findViewById(R.id.pin_search);
        dp = findViewById(R.id.setdate);
        picker = findViewById(R.id.datepicker);
        p = new ProgressDialog(cowinRegestation.this);
        p.setMessage("Statring Data Extraction");
        p.setCancelable(false);
        p.setIndeterminate(false);
        p.show();
        dist = findViewById(R.id.distic);
        tv = findViewById(R.id.titel);
        state = findViewById(R.id.states);
        pin_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cowinRegestation.this,AllBookingLocation.class);
                intent.putExtra("pin",pin_code.getText().toString());
                startActivity(intent);
            }
        });
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get()
                .url("https://cdn-api.co-vin.in/api/v2/admin/location/states")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                cowinRegestation.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        p.cancel();
                        p.hide();

                        try {
                            JSONObject obj = new JSONObject(response.body().string());
                            JSONArray arr = obj.getJSONArray("states");
                            for(int i=0;i<arr.length();i++)
                            {
                                JSONObject object = arr.getJSONObject(i);
                                list.add(object.getString("state_name"));
                                stateid.put(object.getString("state_name"),object.getString("state_id"));
                                str = str + object.getString("state_name")+"\n";
                            }
                            state.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog = new Dialog(cowinRegestation.this);
                                    dialog.setContentView(R.layout.dialog_content_view);
                                    dialog.getWindow().setLayout(750,1000);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.show();
                                    EditText text = dialog.findViewById(R.id.edit_text);
                                    ListView listView = dialog.findViewById(R.id.list_view);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(cowinRegestation.this, android.R.layout.simple_list_item_1,list);
                                    listView.setAdapter(adapter);
                                    text.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            adapter.getFilter().filter(s);
                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }
                                    });
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            state.setText(adapter.getItem(position));
                                            String state_id = stateid.get(adapter.getItem(position));
                                            setDistric(state_id);
                                            dialog.dismiss();
                                        }
                                    });

                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        dp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                    picker.setVisibility(View.VISIBLE);
                    picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            String da = dayOfMonth+"-"+monthOfYear+"-"+year;
                            picker.setVisibility(View.GONE);
                            dp.setText(da);
                        }
                    });
            }
        });

    }
    public void setDistric(String state_id)
    {
        d.clear();
        Dialog distDilog;
        dismap.clear();
        distDilog = new Dialog(cowinRegestation.this);
        distDilog.setContentView(R.layout.distric_content_view);
        distDilog.getWindow().setLayout(1000,1300);
        distDilog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Toast.makeText(cowinRegestation.this,state_id,Toast.LENGTH_SHORT).show();
        p.setMessage("Serching Districs!! wait.....");
        p.setCancelable(false);
        p.setIndeterminate(false);
        p.show();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get()
                .url("https://cdn-api.co-vin.in/api/v2/admin/location/districts/"+state_id)
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                cowinRegestation.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject dis = new JSONObject(response.body().string());
                            JSONArray disa = dis.getJSONArray("districts");
                            for (int i = 0;i<disa.length();i++)
                            {
                                JSONObject object = disa.getJSONObject(i);
                                d.add(object.getString("district_name"));
                                dismap.put(object.getString("district_name"),object.getString("district_id"));
                            }
                            p.cancel();
                            p.hide();
                            dist.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    distDilog.show();
                                    ListView lv = distDilog.findViewById(R.id.distic_list_view);
                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(cowinRegestation.this, android.R.layout.simple_list_item_1,d);
                                    lv.setAdapter(arrayAdapter);
                                    EditText editText = distDilog.findViewById(R.id.distic_edit_text);
                                    editText.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            arrayAdapter.getFilter().filter(s);

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }
                                    });
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                               String disid = dismap.get(arrayAdapter.getItem(position));
                                            dist.setText(arrayAdapter.getItem(position));
                                            distDilog.dismiss();

                                        }
                                    });

                                }
                            });
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }
}