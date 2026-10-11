package com.rymec.plantguardai;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class PlantDiseaseClassifier {

    private Interpreter interpreter;
    private Context context;

    private final String[] labels = {

            "Apple___Apple_scab",
            "Apple___Black_rot",
            "Apple___Cedar_apple_rust",
            "Apple___healthy",

            "Blueberry___healthy",

            "Cherry_(including_sour)___Powdery_mildew",
            "Cherry_(including_sour)___healthy",

            "Corn_(maize)___Cercospora_leaf_spot Gray_leaf_spot",
            "Corn_(maize)___Common_rust_",
            "Corn_(maize)___Northern_Leaf_Blight",
            "Corn_(maize)___healthy",

            "Grape___Black_rot",
            "Grape___Esca_(Black_Measles)",
            "Grape___Leaf_blight_(Isariopsis_Leaf_Spot)",
            "Grape___healthy",

            "Orange___Haunglongbing_(Citrus_greening)",

            "Peach___Bacterial_spot",
            "Peach___healthy",

            "Pepper,_bell___Bacterial_spot",
            "Pepper,_bell___healthy",

            "Potato___Early_blight",
            "Potato___Late_blight",
            "Potato___healthy",

            "Raspberry___healthy",

            "Soybean___healthy",

            "Squash___Powdery_mildew",

            "Strawberry___Leaf_scorch",
            "Strawberry___healthy",

            "Tomato___Bacterial_spot",
            "Tomato___Early_blight",
            "Tomato___Late_blight",
            "Tomato___Leaf_Mold",
            "Tomato___Septoria_leaf_spot",
            "Tomato___Spider_mites Two-spotted_spider_mite",
            "Tomato___Target_Spot",
            "Tomato___Tomato_Yellow_Leaf_Curl_Virus",
            "Tomato___Tomato_mosaic_virus",
            "Tomato___healthy"
    };

    public PlantDiseaseClassifier(Context context) {
        this.context = context;

        try {

            interpreter = new Interpreter(loadModelFile());

            Log.d("MODEL", "Interpreter created successfully!");

            Log.d("MODEL_INFO", "Input Shape: " +
                    java.util.Arrays.toString(interpreter.getInputTensor(0).shape()));

            Log.d("MODEL_INFO", "Output Shape: " +
                    java.util.Arrays.toString(interpreter.getOutputTensor(0).shape()));

            Log.d("MODEL_INFO", "Input Type: " +
                    interpreter.getInputTensor(0).dataType());

            Log.d("MODEL_INFO", "Output Type: " +
                    interpreter.getOutputTensor(0).dataType());

        } catch (Exception e) {

            Log.e("MODEL_ERROR", "Failed to load TensorFlow Lite model", e);
            interpreter = null;

        }
    }

    private MappedByteBuffer loadModelFile() throws IOException {

        AssetFileDescriptor fileDescriptor =
                context.getAssets().openFd("model_float16_quant.tflite");

        FileInputStream inputStream =
                new FileInputStream(fileDescriptor.getFileDescriptor());

        FileChannel fileChannel = inputStream.getChannel();

        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();

        return fileChannel.map(
                FileChannel.MapMode.READ_ONLY,
                startOffset,
                declaredLength
        );
    }

    public PredictionResult classify(Bitmap bitmap) {

        if (interpreter == null) {
            throw new RuntimeException("Interpreter is NULL. Model failed to load.");
        }

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                bitmap,
                224,
                224,
                true
        );

        resizedBitmap = resizedBitmap.copy(Bitmap.Config.ARGB_8888, true);

        ByteBuffer inputBuffer =
                ByteBuffer.allocateDirect(4 * 224 * 224 * 3);

        inputBuffer.order(ByteOrder.nativeOrder());

        int[] pixels = new int[224 * 224];

        resizedBitmap.getPixels(
                pixels,
                0,
                224,
                0,
                0,
                224,
                224
        );

        for (int pixel : pixels) {

            float r = ((pixel >> 16) & 0xFF) / 255.0f;
            float g = ((pixel >> 8) & 0xFF) / 255.0f;
            float b = (pixel & 0xFF) / 255.0f;

            inputBuffer.putFloat(r);
            inputBuffer.putFloat(g);
            inputBuffer.putFloat(b);
        }

        float[][] output = new float[1][38];

        interpreter.run(inputBuffer, output);

        Log.d("AI_OUTPUT", java.util.Arrays.toString(output[0]));

        int bestIndex = 0;
        float bestConfidence = output[0][0];

        for (int i = 1; i < output[0].length; i++) {

            if (output[0][i] > bestConfidence) {

                bestConfidence = output[0][i];
                bestIndex = i;

            }
        }

        Log.d("BEST_RESULT", "Index = " + bestIndex);
        Log.d("BEST_RESULT", "Label = " + labels[bestIndex]);
        Log.d("BEST_RESULT", "Confidence = " + bestConfidence);

        return new PredictionResult(
                labels[bestIndex],
                bestConfidence * 100
        );
    }

    public static class PredictionResult {

        public String disease;
        public float confidence;

        public PredictionResult(String disease, float confidence) {
            this.disease = disease;
            this.confidence = confidence;
        }
    }
}