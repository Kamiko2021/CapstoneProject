package com.capstone.atyourservicecapstone2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfilePage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

		DrawerLayout drawerLayout = findViewById(R.id.box);

		findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				drawerLayout.openDrawer(GravityCompat.START);
			}
		});

    }
}

