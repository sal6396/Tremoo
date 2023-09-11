package com.example.tremoo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.tremoo.Fragment.Project_Details_Fragment;
import com.example.tremoo.Models.ProjectModel;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Camera_Activity extends AppCompatActivity {

    private static final String EXTRA_IMAGE_PATH = "imagePath";
    public static final int REQUEST_IMAGE_CAPTURE = 1; // Use this request code for image capture
    private static final int PERMISSION_REQUEST_CAMERA = 1;
    Button btnSave;
    ImageView imageView;

    public static void startWithImage(Context context) {
        Intent intent = new Intent(context, Camera_Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnSave = findViewById(R.id.button1);
        imageView = findViewById(R.id.imageView1);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement the code to capture or select an image
                // For example, you can use an Intent to capture an image
                if (ContextCompat.checkSelfPermission(Camera_Activity.this, android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Camera_Activity.this,
                            new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                } else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap imageBitmap = null;
        Log.d("ImageCapture", "Image bitmap is not null: " + false);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Check if extras is not null
            Bundle extras = data.getExtras();
            if (extras != null) {
                // Handle the captured image
                imageBitmap = (Bitmap) extras.get("data");

                // Pass the image data to the parent Activity
                if (getParent() instanceof ImageCaptureListener) {
                    ((ImageCaptureListener) getParent()).onImageCaptured(imageBitmap);
                }
            } else {
                Log.d("ImageCapture", "Bundle extras is null");
            }
        }
    }

    public interface ImageCaptureListener {
        void onImageCaptured(Bitmap imageBitmap);
    }
}
