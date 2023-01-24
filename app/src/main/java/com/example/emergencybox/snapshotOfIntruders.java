package com.example.emergencybox;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.emergencybox.databinding.ActivitySnapshotOfIntrudersBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class snapshotOfIntruders extends AppCompatActivity {
    private int i = 0;
    private List<String> arrayList;
//    private List<String> phoneNumber;
    private Bitmap combineImage;
    private int j = 0;
    OutputStream outputStream;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    private ActivitySnapshotOfIntrudersBinding snapshotOfIntrudersBinding;
    private Bitmap[] bitmaps;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneNumber="+918970375841";
        snapshotOfIntrudersBinding = ActivitySnapshotOfIntrudersBinding.inflate(getLayoutInflater());
        setContentView(snapshotOfIntrudersBinding.getRoot());
        arrayList = new ArrayList<>();
        bitmaps = new Bitmap[5];
        openingCamerax();
        setListeners();
    }

    public void setListeners() {
        snapshotOfIntrudersBinding.snapshotSend.setOnClickListener(v -> {
            takeSnapshot();

        });
        snapshotOfIntrudersBinding.showSavedFile.setOnClickListener(v -> {
            combineImage = mergeMultiple();
            snapshotOfIntrudersBinding.image.setImageBitmap(combineImage);
//   saveImage(combineImage);
    sendMmsSms();
        });

    }

    public void takeSnapshot() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (i < 5) {
                    capturePhoto();
                    i++;
                    handler.postDelayed(this, 10);
                    Toast.makeText(snapshotOfIntruders.this, "shot of photo" + i, Toast.LENGTH_SHORT).show();
                }
            }
        };
        handler.post(runnable);

    }

    public void openingCamerax() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(getApplicationContext());
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCamerax(cameraProvider);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());
    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this.getApplicationContext());
    }

    private void startCamerax(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();
        //Camera selector use case
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        Toast.makeText(getApplicationContext(), "camerax is running", Toast.LENGTH_SHORT).show();
        //Image Capture case

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageCapture);

    }

    private void capturePhoto() {
        long timestamp = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(getApplicationContext().getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        , contentValues).build(),
                getExecutor(), new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        try {
                            arrayList.add(Objects.requireNonNull(outputFileResults.getSavedUri()).toString());
                            snapshotOfIntrudersBinding.image.setImageURI(outputFileResults.getSavedUri());
                            bitmaps[j++] = MediaStore.Images.Media.getBitmap(getContentResolver(), outputFileResults.getSavedUri());

//                            Toast.makeText(getApplicationContext(), "Photo has been saved successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ImageCaptureException exception) {
                        try {
                            Toast.makeText(getApplicationContext(), "Error saving Photo " + exception.getMessage(), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    public void showingAllFiles() {
        for (int i = 0; i < arrayList.size(); i++)
            Toast.makeText(this, "showing files " + arrayList.get(i), Toast.LENGTH_SHORT).show();
    }

    public  void sendMmsSms() {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setData(Uri.parse("mmsto:" + "7232913419"));
        intent.putExtra(Intent.EXTRA_STREAM, combineImage);
        intent.putExtra(intent.EXTRA_TEXT, "Hi, This is me,I need your help because i am in emergency.");

        if (intent.resolveActivity(this.getPackageManager()) != null) {
            //instead of "this", the "activity" was used previously, if there is any error, I have to remove that things.
      try{
            this.startActivity(intent);
          Toast.makeText(this, "message sent successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        }

    }


    private Bitmap mergeMultiple() {
        Bitmap result = Bitmap.createBitmap(bitmaps[0].getWidth() * 2, bitmaps[0].getHeight() * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        for (int j = 0; j < bitmaps.length; j++) {
            canvas.drawBitmap(bitmaps[j], bitmaps[j].getWidth() * (j % 2), bitmaps[j].getHeight() * (j / 2), paint);
        }
        return result;
    }

    public static int sizeOf(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }

    }
    public void saveImage(Bitmap combineImage){
        File filepath= Environment.getExternalStorageDirectory();
        File dir=new File(filepath.getAbsolutePath()+"/demo/");
        dir.mkdir();
        File file=new File(dir,System.currentTimeMillis()+".jpg");
        try{
            outputStream=new FileOutputStream(file);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        combineImage.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        Toast.makeText(this, "Image saved to Internal storage", Toast.LENGTH_SHORT).show();
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
