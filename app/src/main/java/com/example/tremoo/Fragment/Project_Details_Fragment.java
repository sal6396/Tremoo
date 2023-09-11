package com.example.tremoo.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tremoo.Audio_Activity;
import com.example.tremoo.Camera_Activity;
import com.example.tremoo.Constants;
import com.example.tremoo.Models.ProjectModel;
import com.example.tremoo.R;
import com.example.tremoo.RequestHandler;
import com.example.tremoo.Video_Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project_Details_Fragment extends Fragment implements Camera_Activity.ImageCaptureListener {

    private static final int CAMERA_REQUEST = 1888;
    private static final int VIDEO_CAPTURE_REQUEST = 2000;
    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private static final int PERMISSION_REQUEST_AUDIO = 2;

    // UI elements
    private EditText editText;
    private Button btnImage, btnVideo, btnAudio;
    private RadioGroup radioGroup;
    private RadioButton radioButtonImage, radioButtonVideo, radioButtonAudio, radioButtonText;
    private LinearLayout detailsLayout;
    private TextView itemNameTextView, filePath;
    private TextView puId, projectCategory, projectDescription,  projectExpiryDate, projectActive;
    public static final String EXTRA_IMAGE_PATH = "imagePath"; // Define the extra key
    private ImageView imageView;
    private Button dataSubmission;
    private Bitmap imageBitmap;

    // Data
    private List<ProjectModel> projectList;
    private String[] items = {"Image", "Video", "Audio", "Text"};

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project__details_, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String projectId = bundle.getString("projectId");
            fetchProjectDetails(projectId); // Pass the projectId to fetch the specific project
        }

        radioGroup = rootView.findViewById(R.id.radioGroup);
        radioButtonImage = rootView.findViewById(R.id.radioButtonImage);
        radioButtonVideo = rootView.findViewById(R.id.radioButtonVideo);
        radioButtonAudio = rootView.findViewById(R.id.radioButtonAudio);
        radioButtonText = rootView.findViewById(R.id.radioButtonText);
        detailsLayout = rootView.findViewById(R.id.detailsLayout);
        itemNameTextView = rootView.findViewById(R.id.itemNameTextView);
        editText = rootView.findViewById(R.id.editText);
        btnImage = rootView.findViewById(R.id.capture_Image);
        btnVideo = rootView.findViewById(R.id.record_Video);
        btnAudio = rootView.findViewById(R.id.record_Audio);
        puId = rootView.findViewById(R.id.projectId);
        projectCategory = rootView.findViewById(R.id.categoryName);
        projectDescription = rootView.findViewById(R.id.projectDetails);
        projectExpiryDate = rootView.findViewById(R.id.projectExpiry);
        projectActive = rootView.findViewById(R.id.projectActive);
        imageView = rootView.findViewById(R.id.imageView);
        dataSubmission = rootView.findViewById(R.id.dataSubmission);


        dataSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBitmap != null) {
                    uploadImage();
                } else {
                    Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setupRadioGroupListener();


        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(EXTRA_IMAGE_PATH)) {
            String imagePath = arguments.getString(EXTRA_IMAGE_PATH);

            // Display the image path in the TextView
            filePath.setText("Image Path: " + imagePath);
        }

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

    private void uploadImage() {
        String url = Constants.URL_POST_SUBMISSIONS; // Your server URL

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(
                Request.Method.POST,
                url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String jsonResponse = new String(response.data);
                        Log.d("UploadResponse", jsonResponse);
                        Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("UploadError", error.toString());
                        Toast.makeText(getActivity(), "Error uploading image", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image", new DataPart("image.jpg", imageBitmapToByteArray(imageBitmap), "image/jpeg"));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer YOUR_AUTH_TOKEN"); // Replace with your token
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(multipartRequest);
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
                filePath.setVisibility(View.GONE);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            } else {
                Toast.makeText(requireContext(), "Camera permission denied.", Toast.LENGTH_SHORT).show();
                // Clear the selected RadioButton
                radioGroup.clearCheck();
            }
        } else if (requestCode == PERMISSION_REQUEST_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startAudioActivity();
            } else {
                Toast.makeText(requireContext(), "Audio permission denied.", Toast.LENGTH_SHORT).show();
                // Clear the selected RadioButton
                radioGroup.clearCheck();
            }
        }
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

                // Save the image to the gallery and get the image path
                String imagePath = String.valueOf(saveImageToGallery(compressedBitmap));

                // Display the image path in the TextView
                if (imagePath != null) {
                    filePath.setText("Image Path: " + imagePath);
                }
            }
        } else if (requestCode == VIDEO_CAPTURE_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            Uri videoUri = data.getData();
            if (videoUri != null) {
                Video_Activity.startWithVideo(requireContext(), videoUri);
            }
        }
    }

    private Uri saveImageToGallery(Bitmap imageBitmap) {
        try {
            // Define the file name and path for the image
            String timeStamp = String.valueOf(System.currentTimeMillis());
            String imageFileName = "IMG_" + timeStamp + ".jpg";

            // Get the content resolver
            ContentResolver contentResolver = getContentResolver();

            // Define the image details
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());

            // Get the external storage directory
            File imageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!imageDirectory.exists()) {
                imageDirectory.mkdirs();
            }

            // Create a new image file
            File imageFile = new File(imageDirectory, imageFileName);

            // Open an output stream to write the image data to the file
            OutputStream imageOutputStream = contentResolver.openOutputStream(Uri.fromFile(imageFile));

            // Compress and save the image to the file
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutputStream);

            // Close the output stream
            if (imageOutputStream != null) {
                imageOutputStream.close();
            }

            // Insert the image details into the MediaStore
            Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            // Notify the gallery app to scan for the new image
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));

            return imageUri;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendBroadcast(Intent intent) {

    }

    private ContentResolver getContentResolver() {
        return null;
    }


    private Bitmap compressBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().length);
    }

    private void fetchProjectDetails(String projectID) {
        // Create a JsonObjectRequest to get the project data from the server
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Constants.URL_GET_PROJECTS + "?projectId=" + projectID,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VolleyResponse", "Response JSON: " + response.toString());

                        try {
                            // Parse the JSON array of projects
                            JSONArray projectsArray = response.getJSONArray("data");

                            // Initialize a variable to store project type
                            String projectType = "";

                            // Iterate through the projects to find the one with matching project ID
                            for (int i = 0; i < projectsArray.length(); i++) {
                                JSONObject projectObject = projectsArray.getJSONObject(i);
                                String projectId = projectObject.getString("puid");

                                if (projectId.equals(projectID)) {
                                    // Found the project with matching project ID
                                    String projectDetails = projectObject.getString("details");
                                    String projectStatus = projectObject.getString("status");
                                    JSONObject categoryObject = projectObject.getJSONObject("category");
                                    String categoryName = categoryObject.getString("name");
                                    String projectExpiry = projectObject.getString("expiry_date");

                                    // Set the data to your TextViews directly
                                    puId.setText(projectId);
                                    projectActive.setText(projectStatus);
                                    projectCategory.setText(categoryName);
                                    projectExpiryDate.setText(projectExpiry);
                                    projectDescription.setText(projectDetails);

                                    // Extract the "type" field from the current project
                                    projectType = projectObject.getString("type");
                                }
                            }

                            // Split the "type" string by commas
                            String[] types = projectType.split(",");

                            // Iterate through the types and set radio button visibility
                            for (String type : types) {
                                type = type.trim(); // Remove leading/trailing spaces

                                // Assuming you have radio buttons with IDs radioButtonImage, radioButtonVideo, etc.
                                // Show/hide radio buttons based on the type
                                if (type.equalsIgnoreCase("Image")) {
                                    radioButtonImage.setVisibility(View.VISIBLE);
                                } else if (type.equalsIgnoreCase("Video")) {
                                    radioButtonVideo.setVisibility(View.VISIBLE);
                                } else if (type.equalsIgnoreCase("Audio")) {
                                    radioButtonAudio.setVisibility(View.VISIBLE);
                                } else if (type.equalsIgnoreCase("Text")) {
                                    radioButtonText.setVisibility(View.VISIBLE);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors, display an error message or take appropriate action
                        Log.e("Volley Error", "Error fetching data: " + error.getMessage());
                        // Display an error message to the user if needed
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(requireContext(), "Error fetching project details.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
        );

        // Add the request to the Volley request queue
        RequestHandler.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onImageCaptured(Bitmap imageBitmap) {
        Log.d("ImageCaptureListener", "Image captured and received in parent activity.");
        // Handle the captured image here and display it in the ImageView
        if (imageBitmap != null) {
            imageView.setImageBitmap(imageBitmap);
        }
        this.imageBitmap = imageBitmap; // Store the image for future use (e.g., for uploading).
    }

    private abstract class VolleyMultipartRequest extends Request<NetworkResponse> {
        public VolleyMultipartRequest(int method, String url, Response.Listener<NetworkResponse> networkResponseListener, Response.ErrorListener listener) {
            super(method, url, listener);
        }

        @Override
        protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
            return null;
        }

        @Override
        protected void deliverResponse(NetworkResponse response) {

        }

        protected abstract Map<String, DataPart> getByteData();
    }

    private class DataPart {
        public DataPart(String s, byte[] imageData, String s1) {

        }
    }
}
