package com.example.tremoo.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tremoo.R;

public class Account_Fragment extends Fragment {
TextView tv;
    Button btnprofile, btnpayment, btnmember;
    Button lastClickedButton; // Keep track of the last clicked button

    private void changeButtonColor(Button button, boolean isUnderlined) {
        // Change the background color of the button
        int backgroundColor = isUnderlined ? R.color.textcolor : R.color.green;
        button.setBackgroundColor(getResources().getColor(backgroundColor));

        // Change the text style to underline if required
        String buttonText = button.getText().toString();
        SpannableString spannableString = new SpannableString(buttonText);
        if (isUnderlined) {
            spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannableString.removeSpan(UnderlineSpan.class);
        }
        button.setText(spannableString);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_account_, container, false);

        btnprofile = rootview.findViewById(R.id.btnProfile);
        btnpayment = rootview.findViewById(R.id.btnPayments);
        btnmember = rootview.findViewById(R.id.btnMembership);



        // Set initial state of buttons
        changeButtonColor(btnprofile, true);
        changeButtonColor(btnpayment, false);
        changeButtonColor(btnmember, false);


        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, Profile_Fragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

                if (lastClickedButton != null) {
                    // Reset the state of the last clicked button
                    changeButtonColor(lastClickedButton, false);
                }

                // Change the state of the current button
                changeButtonColor(btnprofile, true);
                lastClickedButton = btnprofile;
            }
        });
        // Programmatically trigger a click on the "Profile" button to show it as clicked when the fragment is opened
        btnprofile.performClick();



        btnpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, Payments_Fragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

                if (lastClickedButton != null) {
                    // Reset the state of the last clicked button
                    changeButtonColor(lastClickedButton, false);
                }

                // Change the state of the current button
                changeButtonColor(btnpayment, true);
                lastClickedButton = btnpayment;
            }
        });



        btnmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, Membership_Fragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

                if (lastClickedButton != null) {
                    // Reset the state of the last clicked button
                    changeButtonColor(lastClickedButton, false);
                }

                // Change the state of the current button
                changeButtonColor(btnmember, true);
                lastClickedButton = btnmember;
            }
        });

        return rootview;
    }

}