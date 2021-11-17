package com.kripton.flyaway;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;

public  class  MainActivity  extends AppCompatActivity
{
    ActionBarDrawerToggle toggle;
    DrawerLayout layout;
    NavigationView navbar;
    Toolbar toolbar;
    static int CURRENT_FRAGEMENT;
    final int HOME_FRAGEMENT=1;
    final int FLIGHT_FRAGEMENT=2;
    final int TRAIN_FRAGEMENT=3;
    final int HOTEL_FRAGEMENT=4;
    final int REWARD_FRAGEMENT=5;
    final int COVID_FRAGEMENT=6;
    final int LOGOUT=7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        layout = findViewById(R.id.drawable);
        navbar = findViewById(R.id.navbar);
        toggle = new ActionBarDrawerToggle(this, layout, toolbar, R.string.Open, R.string.Close);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        setFragement(new Home_Fragement(),HOME_FRAGEMENT);

        CURRENT_FRAGEMENT = HOME_FRAGEMENT;
        navbar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                switch(id)
                {
                    case R.id.my_home:
                        setFragement(new Home_Fragement(),HOME_FRAGEMENT);
                    break;
                    case R.id.flight:
                        setFragement(new flightBooking(),FLIGHT_FRAGEMENT);
                        break;
                    case R.id.corona:
                        Toast.makeText(getBaseContext(),"Clikced",Toast.LENGTH_SHORT).show();
                        setFragement(new covidFragement(),COVID_FRAGEMENT);
                        break;
                }
                layout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout =findViewById(R.id.drawable);
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            if (CURRENT_FRAGEMENT != HOME_FRAGEMENT) {
                setFragement(new Home_Fragement(), HOME_FRAGEMENT);
                navbar.getMenu().getItem(0).setCheckable(true);
            } else {
                super.onBackPressed();
            }
        }
    }

    private void setFragement(Fragment fragement, int current) {
        if(CURRENT_FRAGEMENT!=current) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, fragement);
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            CURRENT_FRAGEMENT=current;
            transaction.commit();
        }
    }

}