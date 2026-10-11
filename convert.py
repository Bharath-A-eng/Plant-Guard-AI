import tensorflow as tf
import keras

print("TensorFlow:", tf.__version__)
print("Keras:", keras.__version__)

# Load model
model = keras.models.load_model("plantguard_best_model.keras")
print("✅ Model loaded!")

# Convert
converter = tf.lite.TFLiteConverter.from_keras_model(model)

converter.target_spec.supported_ops = [
    tf.lite.OpsSet.TFLITE_BUILTINS
]

converter.optimizations = []

tflite_model = converter.convert()

with open("model_float16_quant.tflite", "wb") as f:
    f.write(tflite_model)

print("🎉 Conversion Successful!")
print("Saved as model_float16_quant.tflite")