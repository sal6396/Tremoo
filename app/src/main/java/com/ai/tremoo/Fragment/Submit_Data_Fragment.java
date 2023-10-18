package com.ai.tremoo.Fragment;

import static com.ai.tremoo.RetrofitClient.retrofitClient;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ai.tremoo.ApiService;
import com.ai.tremoo.Audio_Activity;
import com.ai.tremoo.Models.SubmissionData;
import com.ai.tremoo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.connection.RealCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Submit_Data_Fragment extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    private static final int VIDEO_CAPTURE_REQUEST = 2000;
    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private static final int AUDIO_CAPTURE_REQUEST = 1;
    private static final int TEXT_INPUT_REQUEST = 1;
    private static final int PERMISSION_REQUEST_AUDIO = 2;
    private static final int IMAGE_UPLOAD_REQUEST_CODE = 123; // Unique request code for image upload

    private EditText editText, submissionTitle, description;
    private LinearLayout descripContainer;
    private Button btnImage, btnVideo, btnAudio;
    private RadioGroup radioGroup;
    private RadioButton radioButtonImage, radioButtonVideo, radioButtonAudio, radioButtonText;
    private ImageView imageView;
    private Button dataSubmission;
    private Bitmap imageBitmap;
    private Uri videoUri;

    private ImageView backButton;
    private RelativeLayout toolbar;
    private TextView titleTextView;
    private String projectType;
    private RealCall RetrofitClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_submit__data_, container, false);


        radioGroup = rootView.findViewById(R.id.radioGroup);
        radioButtonImage = rootView.findViewById(R.id.radioButtonImage);
        radioButtonVideo = rootView.findViewById(R.id.radioButtonVideo);
        radioButtonAudio = rootView.findViewById(R.id.radioButtonAudio);
        radioButtonText = rootView.findViewById(R.id.radioButtonText);
        editText = rootView.findViewById(R.id.editText);
        submissionTitle = rootView.findViewById(R.id.submissionTitle);
        description = rootView.findViewById(R.id.submissionDescription);
        descripContainer = rootView.findViewById(R.id.discriptionContainer);
        btnImage = rootView.findViewById(R.id.capture_Image);
        btnVideo = rootView.findViewById(R.id.record_Video);
        btnAudio = rootView.findViewById(R.id.record_Audio);
        dataSubmission = rootView.findViewById(R.id.dataSubmission);
//        backSumission = rootView.findViewById(R.id.backSubmission);



        Bundle bundle = getArguments();
        if (bundle != null) {
            projectType = bundle.getString("type", ""); // Get projectType from the bundle

            // Split the projectType string by comma to get individual types
            String[] types = projectType.split(",");

            // Loop through the types and dynamically show the corresponding radio buttons
            // Loop through the types and dynamically show the corresponding radio buttons
            for (String type : types) {
                type = type.trim(); // Remove leading/trailing spaces

                if ("Image".equals(type)) {
                    radioButtonImage.setVisibility(View.VISIBLE);
                } else if ("Video".equals(type)) {
                    radioButtonVideo.setVisibility(View.VISIBLE);
                } else if ("Audio".equals(type)) {
                    radioButtonAudio.setVisibility(View.VISIBLE);
                } else if ("Text".equals(type)) {
                    radioButtonText.setVisibility(View.VISIBLE);
                }
            }

            // Log the value of the projectType received as an argument
            Log.d("Submit_Data_Fragment", "Received projectType argument: " + projectType);
        } else {
            // If projectType is not present in the bundle, get it from SharedPreferences or set a default value
            SharedPreferences prefs = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            projectType = prefs.getString("projectType", "Image"); // Set a default value like "Image"

            // Show default radio buttons based on the default projectType
            if ("Image".equals(projectType)) {
                radioButtonImage.setVisibility(View.VISIBLE);
                radioButtonVideo.setVisibility(View.GONE);
                radioButtonAudio.setVisibility(View.GONE);
                radioButtonText.setVisibility(View.GONE);
            } else if ("Video".equals(projectType)) {
                radioButtonImage.setVisibility(View.GONE);
                radioButtonVideo.setVisibility(View.VISIBLE);
                radioButtonAudio.setVisibility(View.GONE);
                radioButtonText.setVisibility(View.GONE);
            } else if ("Audio".equals(projectType)) {
                radioButtonImage.setVisibility(View.GONE);
                radioButtonVideo.setVisibility(View.GONE);
                radioButtonAudio.setVisibility(View.VISIBLE);
                radioButtonText.setVisibility(View.GONE);
            } else if ("Text".equals(projectType)) {
                radioButtonImage.setVisibility(View.GONE);
                radioButtonVideo.setVisibility(View.GONE);
                radioButtonAudio.setVisibility(View.GONE);
                radioButtonText.setVisibility(View.VISIBLE);
            }

        }




        toolbar = rootView.findViewById(R.id.toolbar);
        titleTextView = rootView.findViewById(R.id.title);
        backButton = rootView.findViewById(R.id.back);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Handle back button click here
            }
        });
        String dynamicTitle = "Data SUBMISSION"; // Replace this with your dynamic title
        titleTextView.setText(dynamicTitle);

        dataSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBitmap != null) {
//                    uploadImage();
                } else if (videoUri != null) {

                } else {
                    Toast.makeText(getActivity(), "No media selected", Toast.LENGTH_SHORT).show();
                }
            }
        });



        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
            }
        });
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCameraPermission();
            }
        });
        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAudioPermission();
            }
        });

        // Handle radio button selection change
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId==R.id.radioButtonImage) {
                    btnImage.setVisibility(View.VISIBLE);
                    descripContainer.setVisibility(View.VISIBLE);
                    btnVideo.setVisibility(View.GONE);
                    btnAudio.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);
                } else if (checkedId==R.id.radioButtonVideo) {
                    btnImage.setVisibility(View.GONE);
                    btnVideo.setVisibility(View.VISIBLE);
                    descripContainer.setVisibility(View.VISIBLE);
                    btnAudio.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);
                } else if (checkedId==R.id.radioButtonAudio) {
                    btnImage.setVisibility(View.GONE);
                    btnVideo.setVisibility(View.GONE);
                    btnAudio.setVisibility(View.VISIBLE);
                    descripContainer.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                } else if (checkedId==R.id.radioButtonText) {
                    btnImage.setVisibility(View.GONE);
                    btnVideo.setVisibility(View.GONE);
                    btnAudio.setVisibility(View.GONE);
                    descripContainer.setVisibility(View.GONE);
                    editText.setVisibility(View.VISIBLE);
                }
            }
        });


        return rootView;
    }

    private byte[] imageBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }


    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        } else {
            captureImage();
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA)) {
            // Show an explanation to the user why we need camera permissions (optional).
            // You can display a dialog or a message explaining why the permission is required.
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            captureVideo();
        }
    }

    private void checkAudioPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_AUDIO);
        } else {
            startAudioActivity();
        }
    }

    private void startAudioActivity() {
        startActivity(new Intent(getActivity(), Audio_Activity.class));
    }

    private void captureImage() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void captureVideo() {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(videoIntent, VIDEO_CAPTURE_REQUEST);
    }



    private void uploadMedia(String mediaType, File mediaFile) {
        // Create request body for media file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mediaFile);
        MultipartBody.Part mediaPart = MultipartBody.Part.createFormData("media", mediaFile.getName(), requestFile);

        // Create request body for other parameters
        RequestBody mediaTypeBody = RequestBody.create(MediaType.parse("text/plain"), mediaType);
        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), submissionTitle.getText().toString());
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description.getText().toString());



        // Get the ApiService instance from RetrofitClient
        ApiService apiService = retrofitClient.getApiService();
        Call<SubmissionData> call = apiService.uploadMedia(mediaPart, mediaTypeBody, titleBody, descriptionBody);
        call.enqueue(new Callback<SubmissionData>() {
            @Override
            public void onResponse(Call<SubmissionData> call, Response<SubmissionData> response) {
                if (response.isSuccessful()) {
                    // Handle successful upload
                    Toast.makeText(getActivity(), "Media uploaded successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle upload failure
                    Toast.makeText(getActivity(), "Error uploading media", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubmissionData> call, Throwable t) {
                // Handle upload failure
                Toast.makeText(getActivity(), "Error uploading media: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == CAMERA_REQUEST && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (photo != null) {
                // Compress bitmap if necessary
                File mediaFile = compressBitmapToFile(photo);
                if (mediaFile != null) {
                    uploadMedia("image", mediaFile);
                } else {
                    Toast.makeText(getActivity(), "Error compressing image", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Error capturing image", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == VIDEO_CAPTURE_REQUEST && data != null) {
            Uri videoUri = data.getData();
            if (videoUri != null) {
                // Handle videoUri and upload the video file
            }
        } else if (requestCode == AUDIO_CAPTURE_REQUEST && data != null) {
            Uri audioUri = data.getData();
            if (audioUri != null) {
                // Handle audioUri and upload the audio file
            }
        } else if (requestCode == TEXT_INPUT_REQUEST && data != null) {
            String text = data.getStringExtra("text");
            if (text != null && !text.isEmpty()) {
                // Upload text to the server
                uploadText(text);
            }
        }
    }

    private void uploadText(String text) {
        // Implement logic to upload text to the server
        // Use Retrofit or any other networking library to make the API call
        // Handle the response accordingly
    }


    private File compressBitmapToFile(Bitmap bitmap) {
        try {
            File filesDir = getActivity().getFilesDir();
            File imageFile = new File(filesDir, "compressed_image.jpg");

            FileOutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out); // Adjust the quality as needed
            out.close();

            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle the exception according to your application's logic
        }
    }



    private Bitmap compressBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().length);
    }



    private void onBackPressed() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new Project_Details_Fragment())
                .commit();
    }
}
