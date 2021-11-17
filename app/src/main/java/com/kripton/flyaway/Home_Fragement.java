package com.kripton.flyaway;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.*;

public class Home_Fragement extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Home_Fragement() {
    }
    public static Home_Fragement newInstance(String param1, String param2) {
        Home_Fragement fragment = new Home_Fragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private TextView n_ew,death,recover,_3,_5,_7,_2,_9;
    private ShimmerFrameLayout frameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home__fragement, container, false);
        n_ew = view.findViewById(R.id.totalcaese);
        death = view.findViewById(R.id.totaldeath);
        recover = view.findViewById(R.id.totalrecovery);
        frameLayout = view.findViewById(R.id.bannersnip);
        _3 = view.findViewById(R.id.textView3);
        _5 = view.findViewById(R.id.textView5);
        _7 = view.findViewById(R.id.textView7);
        _2 = view.findViewById(R.id.textView2);
        _9 = view.findViewById(R.id.textView9);
        _2.setVisibility(View.INVISIBLE);
        _3.setVisibility(View.INVISIBLE);
        _5.setVisibility(View.INVISIBLE);
        _7.setVisibility(View.INVISIBLE);
        _9.setVisibility(View.INVISIBLE);
        Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                .setBaseColor(Color.parseColor("#0037A6"))
                .setBaseAlpha(1)
                .setHighlightColor(Color.parseColor("#e7e7e7"))
                .setHighlightAlpha(1)
                .setDropoff(50)
                .build();
        ShimmerDrawable drawable = new ShimmerDrawable();
        drawable.setShimmer(shimmer);

        frameLayout.startShimmer();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://covid-19-data.p.rapidapi.com/country?name=India").
                get().
                addHeader("x-rapidapi-key", "381e0dbffcmshc09ea9a79184fd7p1d4e68jsn57d21fcf6f3d")
                .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String newcasee = jsonObject.getString("confirmed");
                    String deat = jsonObject.getString("deaths");
                    String rec = jsonObject.getString("recovered");
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"Running",Toast.LENGTH_SHORT).show();
                            n_ew.setText(newcasee);
                            death.setText(deat);
                            recover.setText(rec);
                            _2.setVisibility(View.VISIBLE);
                            _3.setVisibility(View.VISIBLE);
                            _5.setVisibility(View.VISIBLE);
                            _7.setVisibility(View.VISIBLE);
                            _9.setVisibility(View.VISIBLE);
                            frameLayout.stopShimmer();
                            frameLayout.setVisibility(View.GONE);
                        }
                    });
                } catch (Exception e) {
                    Log.d("er",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}