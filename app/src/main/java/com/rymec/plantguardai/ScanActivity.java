package com.rymec.plantguardai;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScanActivity extends AppCompatActivity {

    // =========================================================
    // UI
    // =========================================================

    private ImageView imgLeaf;
    private TextView txtImageStatus;

    private Button btnChooseImage;
    private Button btnDetect;
    private Button btnCamera;


    // =========================================================
    // CAMERA PERMISSION
    // =========================================================

    private static final int CAMERA_PERMISSION_CODE = 100;


    // =========================================================
    // AI CLASSIFIER
    // =========================================================

    private PlantDiseaseClassifier classifier;

    private ProgressDialog progressDialog;


    // =========================================================
    // SELECTED IMAGE
    // =========================================================

    /*
     * IMPORTANT:
     *
     * We keep the actual Bitmap here.
     *
     * Gallery -> Bitmap
     * Camera  -> Bitmap
     *
     * Analyze Plant always uses this Bitmap.
     *
     * This makes the app much more reliable because
     * analysis does not depend on the Gallery URI.
     */
    private Bitmap selectedBitmap;

    /*
     * We keep the URI mainly for reference.
     * Analysis does NOT depend on this URI.
     */
    private Uri selectedImageUri;


    // =========================================================
    // GALLERY PICKER
    // =========================================================

    private final ActivityResultLauncher<String[]> imagePicker =
            registerForActivityResult(
                    new ActivityResultContracts.OpenDocument(),
                    uri -> {

                        // User cancelled the picker
                        if (uri == null) {
                            return;
                        }

                        try {

                            // -----------------------------------------
                            // Save URI
                            // -----------------------------------------

                            selectedImageUri = uri;


                            // -----------------------------------------
                            // Try to keep permission
                            // -----------------------------------------

                            try {

                                getContentResolver().takePersistableUriPermission(
                                        uri,
                                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                                );

                            } catch (Exception ignored) {

                                /*
                                 * Some Gallery providers don't support
                                 * persistable permissions.
                                 *
                                 * This is okay because we immediately
                                 * convert the image into a Bitmap.
                                 */
                            }


                            // -----------------------------------------
                            // Load selected image
                            // -----------------------------------------

                            Bitmap bitmap =
                                    loadBitmapFromUri(uri);


                            if (bitmap == null) {

                                selectedBitmap = null;
                                selectedImageUri = null;

                                Toast.makeText(
                                        ScanActivity.this,
                                        getString(
                                                R.string.failed_load_image
                                        ),
                                        Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }


                            // -----------------------------------------
                            // Store Bitmap
                            // -----------------------------------------

                            selectedBitmap = bitmap;


                            // -----------------------------------------
                            // SHOW IMAGE
                            // -----------------------------------------

                            imgLeaf.setImageBitmap(selectedBitmap);


                            // -----------------------------------------
                            // UPDATE STATUS TEXT
                            // -----------------------------------------

                            txtImageStatus.setText(
                                    getString(
                                            R.string.image_selected
                                    )
                            );


                        } catch (Exception e) {

                            e.printStackTrace();

                            selectedBitmap = null;
                            selectedImageUri = null;

                            Toast.makeText(
                                    ScanActivity.this,
                                    getString(
                                            R.string.failed_load_image
                                    ),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );


    // =========================================================
    // CAMERA
    // =========================================================

    private final ActivityResultLauncher<Void> cameraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.TakePicturePreview(),
                    bitmap -> {

                        // Camera cancelled
                        if (bitmap == null) {
                            return;
                        }

                        try {

                            // -----------------------------------------
                            // Store Bitmap
                            // -----------------------------------------

                            selectedBitmap = bitmap;


                            // -----------------------------------------
                            // URI will be created when saving
                            // -----------------------------------------

                            selectedImageUri = null;


                            // -----------------------------------------
                            // SHOW CAMERA IMAGE
                            // -----------------------------------------

                            imgLeaf.setImageBitmap(
                                    selectedBitmap
                            );


                            // -----------------------------------------
                            // UPDATE STATUS
                            // -----------------------------------------

                            txtImageStatus.setText(
                                    getString(
                                            R.string.image_selected
                                    )
                            );


                        } catch (Exception e) {

                            e.printStackTrace();

                            selectedBitmap = null;
                            selectedImageUri = null;

                            Toast.makeText(
                                    ScanActivity.this,
                                    getString(
                                            R.string.failed_load_image
                                    ),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );


    // =========================================================
    // ON CREATE
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
         * Load the user's selected language BEFORE
         * setting the content view.
         *
         * This keeps English/Kannada localization working.
         */
        LocaleHelper.loadLocale(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan);


        // =====================================================
        // INITIALIZE AI CLASSIFIER
        // =====================================================

        classifier =
                new PlantDiseaseClassifier(this);


        // =====================================================
        // FIND UI VIEWS
        // =====================================================

        imgLeaf =
                findViewById(R.id.imgLeaf);

        txtImageStatus =
                findViewById(R.id.txtImageStatus);

        btnChooseImage =
                findViewById(R.id.btnChooseImage);

        btnDetect =
                findViewById(R.id.btnDetect);

        btnCamera =
                findViewById(R.id.btnCamera);


        // =====================================================
        // INITIAL IMAGE STATE
        // =====================================================

        selectedBitmap = null;
        selectedImageUri = null;


        txtImageStatus.setText(
                getString(
                        R.string.no_image_selected
                )
        );


        // =====================================================
        // CAMERA BUTTON
        // =====================================================

        btnCamera.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(
                    ScanActivity.this,
                    Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED) {

                // Permission already granted
                cameraLauncher.launch(null);

            } else {

                // Ask for camera permission
                ActivityCompat.requestPermissions(
                        ScanActivity.this,
                        new String[]{
                                Manifest.permission.CAMERA
                        },
                        CAMERA_PERMISSION_CODE
                );
            }
        });


        // =====================================================
        // GALLERY BUTTON
        // =====================================================

        btnChooseImage.setOnClickListener(v -> {

            /*
             * Open Android Gallery / document picker.
             */
            imagePicker.launch(
                    new String[]{
                            "image/*"
                    }
            );
        });


        // =====================================================
        // ANALYZE PLANT BUTTON
        // =====================================================

        btnDetect.setOnClickListener(v -> {

            analyzeSelectedPlant();

        });
    }


    // =========================================================
    // ANALYZE SELECTED PLANT
    // =========================================================

    private void analyzeSelectedPlant() {

        // =====================================================
        // CHECK IMAGE
        // =====================================================

        if (selectedBitmap == null) {

            Toast.makeText(
                    this,
                    getString(
                            R.string.choose_image_first
                    ),
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // =====================================================
        // SHOW PROGRESS DIALOG
        // =====================================================

        progressDialog =
                new ProgressDialog(this);

        progressDialog.setTitle(
                getString(
                        R.string.ai_detection_title
                )
        );

        progressDialog.setMessage(
                getString(
                        R.string.analyzing_plant
                )
        );

        progressDialog.setCancelable(false);

        progressDialog.show();


        // =====================================================
        // COPY BITMAP REFERENCE
        // =====================================================

        /*
         * Take a local reference before starting the thread.
         *
         * This guarantees that the background thread analyzes
         * the image the user selected.
         */
        final Bitmap bitmapToAnalyze =
                selectedBitmap;


        // =====================================================
        // RUN AI IN BACKGROUND
        // =====================================================

        new Thread(() -> {

            try {

                // =================================================
                // AI CLASSIFICATION
                // =================================================

                PlantDiseaseClassifier.PredictionResult result =
                        classifier.classify(
                                bitmapToAnalyze
                        );


                // =================================================
                // RECOMMENDATION
                // =================================================

                String recommendation =
                        DiseaseInfo.getInfo(
                                result.disease
                        );


                // =================================================
                // PLANT NAME
                // =================================================

                String plant =
                        result.disease.split(
                                "___"
                        )[0];


                // =================================================
                // SAVE IMAGE FOR HISTORY
                // =================================================

                /*
                 * IMPORTANT:
                 *
                 * We now ALWAYS save the Bitmap.
                 *
                 * This works for:
                 *
                 * Gallery images
                 * Camera images
                 *
                 * No URI problem.
                 */
                String savedImagePath =
                        saveBitmapToHistory(
                                bitmapToAnalyze
                        );


                // =================================================
                // SAVE HISTORY
                // =================================================

                saveHistory(
                        savedImagePath,
                        plant,
                        result.disease,
                        result.confidence
                );


                // =================================================
                // SHOW RESULT ON UI THREAD
                // =================================================

                runOnUiThread(() -> {

                    // ---------------------------------------------
                    // Close progress dialog
                    // ---------------------------------------------

                    if (progressDialog != null
                            && progressDialog.isShowing()) {

                        progressDialog.dismiss();
                    }


                    // ---------------------------------------------
                    // Confidence
                    // ---------------------------------------------

                    String confidenceText =
                            String.format(
                                    Locale.getDefault(),
                                    "%.2f%%",
                                    result.confidence
                            );


                    // ---------------------------------------------
                    // Result dialog
                    // ---------------------------------------------

                    new AlertDialog.Builder(
                            ScanActivity.this
                    )

                            .setTitle(
                                    getString(
                                            R.string.prediction_title
                                    )
                            )

                            .setMessage(

                                    getString(
                                            R.string.plant_label
                                    )
                                            + " "
                                            + plant

                                            + "\n\n"

                                            + getString(
                                            R.string.disease_label
                                    )
                                            + " "
                                            + result.disease

                                            + "\n\n"

                                            + getString(
                                            R.string.confidence_label
                                    )
                                            + " "
                                            + confidenceText

                                            + "\n\n"

                                            + getString(
                                            R.string.recommendation_label
                                    )

                                            + "\n"

                                            + recommendation
                            )

                            .setPositiveButton(
                                    getString(
                                            R.string.ok
                                    ),
                                    null
                            )

                            .show();

                });


            } catch (Exception e) {

                e.printStackTrace();


                // =================================================
                // ERROR ON UI THREAD
                // =================================================

                runOnUiThread(() -> {

                    if (progressDialog != null
                            && progressDialog.isShowing()) {

                        progressDialog.dismiss();
                    }


                    Toast.makeText(
                            ScanActivity.this,
                            getString(
                                    R.string.failed_load_image
                            ),
                            Toast.LENGTH_SHORT
                    ).show();

                });
            }

        }).start();
    }


    // =========================================================
    // LOAD BITMAP FROM GALLERY URI
    // =========================================================

    private Bitmap loadBitmapFromUri(Uri uri)
            throws Exception {

        if (uri == null) {
            return null;
        }


        // =====================================================
        // ANDROID 9+
        // =====================================================

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.P) {

            ImageDecoder.Source source =
                    ImageDecoder.createSource(
                            getContentResolver(),
                            uri
                    );


            return ImageDecoder.decodeBitmap(
                    source,
                    (decoder, info, source1) -> {

                        /*
                         * Software allocator gives us a normal
                         * editable/usable Bitmap for the classifier.
                         */
                        decoder.setAllocator(
                                ImageDecoder.ALLOCATOR_SOFTWARE
                        );
                    }
            );


        } else {

            // =================================================
            // OLDER ANDROID
            // =================================================

            return MediaStore.Images.Media.getBitmap(
                    getContentResolver(),
                    uri
            );
        }
    }


    // =========================================================
    // SAVE BITMAP TO HISTORY
    // =========================================================

    private String saveBitmapToHistory(
            Bitmap bitmap) {

        if (bitmap == null) {
            return "";
        }


        FileOutputStream outputStream =
                null;


        try {

            // =================================================
            // HISTORY DIRECTORY
            // =================================================

            File directory =
                    new File(
                            getFilesDir(),
                            "history"
                    );


            if (!directory.exists()) {

                boolean created =
                        directory.mkdirs();

                if (!created
                        && !directory.exists()) {

                    return "";
                }
            }


            // =================================================
            // FILE NAME
            // =================================================

            File imageFile =
                    new File(
                            directory,
                            "IMG_"
                                    + System.currentTimeMillis()
                                    + ".jpg"
                    );


            // =================================================
            // WRITE IMAGE
            // =================================================

            outputStream =
                    new FileOutputStream(
                            imageFile
                    );


            bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    95,
                    outputStream
            );


            outputStream.flush();


            return imageFile.getAbsolutePath();


        } catch (Exception e) {

            e.printStackTrace();

            return "";


        } finally {

            try {

                if (outputStream != null) {
                    outputStream.close();
                }

            } catch (Exception ignored) {
            }
        }
    }


    // =========================================================
    // SAVE HISTORY DATABASE RECORD
    // =========================================================

    private void saveHistory(
            String image,
            String plant,
            String disease,
            float confidence) {

        HistoryDatabaseHelper dbHelper =
                new HistoryDatabaseHelper(this);


        SQLiteDatabase db =
                null;


        try {

            db =
                    dbHelper.getWritableDatabase();


            ContentValues values =
                    new ContentValues();


            // =================================================
            // DATE
            // =================================================

            String date =
                    new SimpleDateFormat(
                            "dd/MM/yyyy",
                            Locale.getDefault()
                    ).format(
                            new Date()
                    );


            // =================================================
            // TIME
            // =================================================

            String time =
                    new SimpleDateFormat(
                            "hh:mm a",
                            Locale.ENGLISH
                    ).format(
                            new Date()
                    );


            // =================================================
            // DATABASE VALUES
            // =================================================

            values.put(
                    HistoryDatabaseHelper.COL_IMAGE,
                    image
            );


            values.put(
                    HistoryDatabaseHelper.COL_PLANT,
                    plant
            );


            values.put(
                    HistoryDatabaseHelper.COL_DISEASE,
                    disease
            );


            values.put(
                    HistoryDatabaseHelper.COL_CONFIDENCE,
                    confidence
            );


            values.put(
                    HistoryDatabaseHelper.COL_DATE,
                    date
            );


            values.put(
                    HistoryDatabaseHelper.COL_TIME,
                    time
            );


            // =================================================
            // INSERT
            // =================================================

            db.insert(
                    HistoryDatabaseHelper.TABLE_HISTORY,
                    null,
                    values
            );


        } catch (Exception e) {

            e.printStackTrace();


        } finally {

            if (db != null
                    && db.isOpen()) {

                db.close();
            }
        }
    }


    // =========================================================
    // CAMERA PERMISSION RESULT
    // =========================================================

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );


        // =====================================================
        // CAMERA PERMISSION
        // =====================================================

        if (requestCode ==
                CAMERA_PERMISSION_CODE) {


            if (grantResults.length > 0
                    && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {

                // Permission granted
                cameraLauncher.launch(null);


            } else {

                // Permission denied
                Toast.makeText(
                        this,
                        getString(
                                R.string.camera_permission_denied
                        ),
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}