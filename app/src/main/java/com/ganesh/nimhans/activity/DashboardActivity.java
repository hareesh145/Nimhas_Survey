package com.ganesh.nimhans.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivityDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    MyNimhans myGameApp;
    Activity activity;
    BottomNavigationView bottomNav;
    private ActivityDashboardBinding binding;
    TextView notifyCount;
    LinearLayout linearLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);

        linearLayout = binding.layoutUser;
//        myGameApp = (MyGameApp) activity.getApplicationContext();
//        notifyCount = (TextView) findViewById(R.id.notifyCount);
//
////        notifyCount.setText("0");
//
//        bottomNav = binding.bottomNav;
//        loadFragment(new HomeItemFragment(), FragTag.FRAG_HOME_ITEM);
//        bottomNav.setOnItemSelectedListener(this);
//
//        setSupportActionBar(binding.toolbar);
////        toolbar?.navigationIcon = ContextCompat.getDrawable(this,R.drawable.menuicon)
////        toolbar?.setNavigationOnClickListener { Toast.makeText(applicationContext,"Navigation icon was clicked",Toast.LENGTH_SHORT).show() }
//        TextView username = (TextView) findViewById(R.id.userName);
//        username.setText(PreferenceConnector.readString(activity, PreferenceConnector.USERNAME, ""));
//        binding.account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(myGameApp.getmContext(), "Account icon was clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//        binding.notificationIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadFragment(new NotificationFragment(), FragTag.FRAG_NOTIFY);
//            }
//        });
//        binding.layViewBelowToolbar.layAddCash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadFragment(new WalletDepositFragment(), FragTag.FRAG_WALLET_DEPOSIT);
//            }
//        });
//        binding.layViewBelowToolbar.layWhatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openWhatsApp();
//            }
//        });
    }

    public void onClickCreateUser(View v) {
        startActivity(new Intent(activity, CreateUserActivity.class));
    }
    public void onClickViewUser(View v) {
        startActivity(new Intent(activity, ViewUserActivity.class));
    }

    public void onClickMenuView(View v) {
        if(linearLayout.getVisibility() == View.VISIBLE)
            linearLayout.setVisibility(View.INVISIBLE);
        else
            linearLayout.setVisibility(View.VISIBLE);
    }
}