package com.kripton.flyaway;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllBookingLocation extends AppCompatActivity {
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    FusedLocationProviderClient fusedLocationProviderClient;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_booking_location);
        Intent pin = getIntent();
        tabLayout = findViewById(R.id.vaccine_tab);
        viewPager2 = findViewById(R.id.vaccine_viewpager);
        String code = pin.getExtras().getString("pin");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(AllBookingLocation.this);
        if((ActivityCompat.checkSelfPermission(AllBookingLocation.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)&&
                (ActivityCompat.checkSelfPermission(AllBookingLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED))
        {
            getLocation();
        }
        if(!code.equals("-1")) {

            new MyThreadClass().execute(code);
            }

        }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        LocationManager locationManager =  (LocationManager)  getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location != null)
                    {

                    }
                }
            });
        }
    }

    private class MyThreadClass extends  AsyncTask<String,Void,List<recycleViewModel>>
        {
            ArrayList<recycleViewModel> adapterClassList = new ArrayList<>();
            ArrayList<vaccinModelClass> list = new ArrayList<>();
            HashMap<Integer,String> map = new HashMap<>();
            String ret;
            ProgressDialog progressDialog = new ProgressDialog(AllBookingLocation.this);
            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                progressDialog.setIndeterminate(false);
                progressDialog.setMessage("Serching Detials");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected List<recycleViewModel> doInBackground(String... strings) {
                for(int i=0;i<=5;i++) {
                    list = new ArrayList<>();
                    LocalDate today = LocalDate.now();
                    LocalDate tomoro = today.plusDays(i);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    map.put(i,formatter.format(tomoro));
                    String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=" + strings[0] + "&date=" + formatter.format(tomoro);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .get().url(url)
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                        ret=response.body().string();
                        JSONObject object = new JSONObject(ret);
                        JSONArray array = object.getJSONArray("sessions");
                        if(array.length()>0) {
                            for (int j = 0; j < array.length(); j++) {
                                JSONObject obj = array.getJSONObject(j);
                                String add1 = obj.getString("name");
                                String add2 = obj.getString("address")+" "+ obj.getString("district_name")+" " + obj.getString("state_name") +" "+ obj.getString("pincode");
                                String d1 = obj.getString("available_capacity_dose1");
                                String d2 = obj.getString("available_capacity_dose2");
                                String age = obj.getString("min_age_limit");
                                String vaccine = obj.getString("vaccine");
                                String fee = obj.getString("fee");
                                list.add(new vaccinModelClass(add1, add2, d1, d2, vaccine, age,fee));
                            }
                        }
                        else {
                            list.add(new vaccinModelClass("No Vaccine","No Vaccine","0","0","Nan","-1","0"));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    vaccineAdapterClass adapterClass = new vaccineAdapterClass(list);
                    adapterClassList.add(new recycleViewModel(adapterClass));
                }
                return adapterClassList;
            }

            @Override
            protected void onPostExecute(List<recycleViewModel> recycleViewModels) {

                super.onPostExecute(recycleViewModels);
                recycleViewAdapter adapter = new recycleViewAdapter(AllBookingLocation.this, recycleViewModels);

                viewPager2.setAdapter(adapter);
                new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> {
                    tab.setText(map.get(position));
                })).attach();
                progressDialog.cancel();
                progressDialog.hide();
            }
        }
}