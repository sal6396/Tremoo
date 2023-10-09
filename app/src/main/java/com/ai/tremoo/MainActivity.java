package com.ai.tremoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.ai.tremoo.Fragment.Home_Fragment;
import com.ai.tremoo.Fragment.Payments_Fragment;
import com.ai.tremoo.Fragment.Profile_Fragment;
import com.ai.tremoo.Fragment.Project_Desk_Fragment;
import com.ai.tremoo.Fragment.Projects_Fragment;
import com.ai.tremoo.Fragment.Setting_Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout =findViewById(R.id.drawerLayout);
        navigationView =findViewById(R.id.nav);

        if (savedInstanceState == null) {
            replaceFragment(new Home_Fragment(), false);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.dashboard)
                {
                    fragment(new Home_Fragment());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Toast.makeText(MainActivity.this, "Dashboard", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.profile)
                {
                    fragment(new Profile_Fragment());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.projectDesk)
                {
                    fragment(new Project_Desk_Fragment());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Toast.makeText(MainActivity.this, "Projects", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.myProject)
                {
                    fragment(new Projects_Fragment());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Toast.makeText(MainActivity.this, "My Projects", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.payment)
                {
                    fragment(new Payments_Fragment());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Toast.makeText(MainActivity.this, "Payments", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.setting)
                {
                    fragment(new Setting_Fragment());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.logOut)
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    SharedPrefManager.getInstance(getApplicationContext()).logout();

                }

                return true; // Return true to indicate the item has been handled
            }
        });

    }

    private void fragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    // Call this method to replace fragments in your activity
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Get the current fragment on top of the back stack
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frameLayout);

        // Check if the current fragment is Profile_Fragment
        if (currentFragment instanceof Profile_Fragment) {
            // Replace Profile_Fragment with Home_Fragment when back button is pressed
            replaceFragment(new Home_Fragment(), false);
        } else {
            super.onBackPressed();
        }
    }

    public void openDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.openDrawer(GravityCompat.START);
    }
}
