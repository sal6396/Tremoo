package com.ai.tremoo.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.ai.tremoo.R;

import java.io.ByteArrayOutputStream;

import retrofit2.Retrofit;

public class Submit_Data_Fragment extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    private static final int VIDEO_CAPTURE_REQUEST = 2000;
    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private static final int PERMISSION_REQUEST_AUDIO = 2;
    private static final int IMAGE_UPLOAD_REQUEST_CODE = 123; // Unique request code for image upload

    private Retrofit retrofit;
    private ApiService apiService;

    private EditText editText;
    private Button btnImage, btnVideo, btnAudio;
    private RadioGroup radioGroup;
    private RadioButton radioButtonImage, radioButtonVideo, radioButtonAudio, radioButtonText;
    private ImageView imageView;
    private Button dataSubmission;
    private Bitmap imageBitmap;
    private Uri videoUri;
    ImageButton backSumission;

    private ImageView backButton;
    private RelativeLayout toolbar;
    private TextView titleTextView;

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
        btnImage = rootView.findViewById(R.id.capture_Image);
        btnVideo = rootView.findViewById(R.id.record_Video);
        btnAudio = rootView.findViewById(R.id.record_Audio);
        imageView = rootView.findViewById(R.id.imageView);
        dataSubmission = rootView.findViewById(R.id.dataSubmission);
//        backSumission = rootView.findViewById(R.id.backSubmission);

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
                    uploadVideo();
                } else {
                    Toast.makeText(getActivity(), "No media selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        backSumission.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Create an instance of Submit_Data_Fragment
//                Project_Details_Fragment projectDetailsFragment = new Project_Details_Fragment();
//
//                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//                transaction.replace(R.id.frameLayout, projectDetailsFragment); // Use the instance
//                transaction.addToBackStack(null); // Optional: Adds to back stack
//                transaction.commit();
//            }
//        });

        setupRadioGroupListener();

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

        return rootView;
    }



//    private void uploadImage() {
//        String url = Constants.URL_GET_PROJECTS; // Replace with your server URL
//
//        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(
//                Request.Method.POST,
//                url,
//                new Response.Listener<NetworkResponse>() {
//                    @Override
//                    public void onResponse(NetworkResponse response) {
//                        String jsonResponse = new String(response.data);
//                        Log.d("UploadResponse", jsonResponse);
//                        Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("UploadError", error.toString());
//                        Toast.makeText(getActivity(), "Error uploading image", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "Bearer YOUR_AUTH_TOKEN"); // Replace with your token
//                return headers;
//            }
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(multipartRequest);
//    }

    private void uploadVideo() {
        // Implement video upload logic similar to image upload
        // Replace Constants.URL_POST_VIDEO with your server's URL for video upload
        // You'll need to use the videoUri to upload the video file to the server
    }

    private byte[] imageBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    private void setupRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonImage) {
                editText.setVisibility(View.GONE);
                btnImage.setVisibility(View.VISIBLE);
                btnVideo.setVisibility(View.GONE);
                btnAudio.setVisibility(View.GONE);
            } else if (checkedId == R.id.radioButtonVideo) {
                editText.setVisibility(View.GONE);
                btnImage.setVisibility(View.GONE);
                btnVideo.setVisibility(View.VISIBLE);
                btnAudio.setVisibility(View.GONE);
            } else if (checkedId == R.id.radioButtonAudio) {
                editText.setVisibility(View.GONE);
                btnImage.setVisibility(View.GONE);
                btnVideo.setVisibility(View.GONE);
                btnAudio.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radioButtonText) {
                editText.setVisibility(View.VISIBLE);
                btnImage.setVisibility(View.GONE);
                btnVideo.setVisibility(View.GONE);
                btnAudio.setVisibility(View.GONE);
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (photo != null) {
                Bitmap compressedBitmap = compressBitmap(photo, 100);
                imageView.setImageBitmap(compressedBitmap);
                imageBitmap = compressedBitmap;
            }
        } else if (requestCode == VIDEO_CAPTURE_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            videoUri = data.getData();
            if (videoUri != null) {
                // Handle the captured video URI here (e.g., store it for future use).
            }
        }
    }

//    private void fetchProjectDetails(String projectID) {
//        // Create a JsonObjectRequest to get the project data from the server
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                Constants.URL_GET_PROJECTS + "?projectId=" + projectID,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("VolleyResponse", "Response JSON: " + response.toString());
//
//                        try {
//                            // Parse the JSON array of projects
//                            JSONArray projectsArray = response.getJSONArray("data");
//
//                            // Initialize a variable to store project type
//                            String projectType = "";
//
//                            // Split the "type" string by commas
//                            String[] types = projectType.split(",");
//
//                            // Iterate through the types and set radio button visibility
//                            for (String type : types) {
//                                type = type.trim(); // Remove leading/trailing spaces
//
//                                // Assuming you have radio buttons with IDs radioButtonImage, radioButtonVideo, etc.
//                                // Show/hide radio buttons based on the type
//                                if (type.equalsIgnoreCase("Image")) {
//                                    radioButtonImage.setVisibility(View.VISIBLE);
//                                } else if (type.equalsIgnoreCase("Video")) {
//                                    radioButtonVideo.setVisibility(View.VISIBLE);
//                                } else if (type.equalsIgnoreCase("Audio")) {
//                                    radioButtonAudio.setVisibility(View.VISIBLE);
//                                } else if (type.equalsIgnoreCase("Text")) {
//                                    radioButtonText.setVisibility(View.VISIBLE);
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle errors, display an error message or take appropriate action
//                        Log.e("Volley Error", "Error fetching data: " + error.getMessage());
//                        // Display an error message to the user if needed
//                        requireActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(requireContext(), "Error fetching project details.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//        );
//
//        // Add the request to the Volley request queue
////        RequestHandler.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest);
//    }
//
//    private class VolleyMultipartRequest extends Request<NetworkResponse> {
//        private final Response.Listener<NetworkResponse> mListener;
//        private final Response.ErrorListener mErrorListener;
//
//        public VolleyMultipartRequest(int method, String url,
//                                      Response.Listener<NetworkResponse> listener,
//                                      Response.ErrorListener errorListener) {
//            super(method, url, errorListener);
//            this.mListener = listener;
//            this.mErrorListener = errorListener;
//        }
//
//        @Override
//        protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
//            return Response.success(response, getCacheEntry());
//        }
//
//        @Override
//        protected void deliverResponse(NetworkResponse response) {
//            mListener.onResponse(response);
//        }
//    }

    private Bitmap compressBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().length);
    }

    private class DataPart {
        private final String fileName;
        private final byte[] data;
        private final String type;

        public DataPart(String name, byte[] data, String mimeType) {
            fileName = name;
            this.data = data;
            type = mimeType;
        }
    }

    private void onBackPressed() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new Project_Details_Fragment())
                .commit();
    }
}
