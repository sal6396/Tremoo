package com.ai.tremoo;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ai.tremoo.Fragment.Account_Fragment;
import com.ai.tremoo.Fragment.Home_Fragment;
import com.ai.tremoo.Fragment.Projects_Fragment;
import com.ai.tremoo.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;
    private int lastClickedItemId = R.id.home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new Home_Fragment());

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        setupBottomNavigationView();

    }

    private void setupBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId != lastClickedItemId) {
                replaceFragment(getFragmentForItemId(itemId));
                changeButtonColor(lastClickedItemId, false);
                changeButtonColor(itemId, true);
                lastClickedItemId = itemId;
            }

            return true;
        });

        // Set initial button color
        changeButtonColor(lastClickedItemId, true);
    }

    private Fragment getFragmentForItemId(int itemId) {
        if (itemId == R.id.home) {
            return new Home_Fragment();
        } else if (itemId == R.id.project) {
            return new Projects_Fragment();
        } else if (itemId == R.id.account) {
            return new Account_Fragment();
        }
        return null;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Add the current fragment to the back stack
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


    private void changeButtonColor(int itemId, boolean isSelected) {
        // Change the background color of the selected/unselected item
        int selectedColor = getResources().getColor(R.color.textcolor);
        int unselectedColor = getResources().getColor(R.color.green);

        bottomNavigationView.findViewById(itemId).setBackgroundColor(isSelected ? selectedColor : unselectedColor);

        // Change the icon and text color
        int iconColor = getResources().getColor(R.color.white);
        int textColor = getResources().getColor(R.color.white);

        // Set the icon color for the selected item
        bottomNavigationView.getMenu().findItem(itemId).getIcon().setColorFilter(iconColor, PorterDuff.Mode.SRC_IN);

        // Set the item text color using a custom color state list
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked},
                new int[]{-android.R.attr.state_checked},
        };
        int[] colors = new int[]{
                textColor, // Color for checked state
                textColor, // Color for unchecked state (you can adjust this)
        };
        bottomNavigationView.setItemTextColor(new ColorStateList(states, colors));
    }


    @Override
    public void onBackPressed() {
        // Check if the user is currently on the login page
        if (bottomNavigationView.getSelectedItemId() != R.id.home) {
            // Prevent the app from logging out
            super.onBackPressed();
        }
    }

}
