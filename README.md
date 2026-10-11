# Plant Guard AI

Plant Guard AI is an Android application that uses machine learning to identify potential plant diseases from leaf images. It integrates a TensorFlow Lite model into a Java-based Android application to provide plant disease predictions on supported images.

The project explores the integration of mobile application development and machine learning to make plant health information more accessible.

## Features

- Plant disease classification using a TensorFlow Lite model
- Leaf image scanning and prediction
- Plant and disease information
- Scan history with local data storage
- Login and registration screens
- Supported plant categories
- Settings and multilingual interface resources

## Technology Stack

- **Language:** Java
- **Platform:** Android
- **UI:** XML
- **Machine Learning:** TensorFlow Lite
- **Local Storage:** SQLite
- **Build System:** Gradle

## Project Structure

```text
Plant-Guard-AI/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── assets/
│   │   │   │   ├── model_float16_quant.tflite
│   │   │   │   ├── labels.txt
│   │   │   │   └── class_indices.json
│   │   │   ├── java/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   ├── androidTest/
│   │   └── test/
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Getting Started

### Prerequisites

- Android Studio
- Android SDK required by the project
- A compatible JDK
- An Android device or emulator

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/Bharath-A-eng/Plant-Guard-AI.git
   ```

2. Open the cloned project in Android Studio.
3. Allow Gradle to sync and download the required dependencies.
4. Configure any required external services if applicable.
5. Run the application on an Android device or emulator.

## Machine Learning Model

The application uses a TensorFlow Lite model stored in `app/src/main/assets/`. Supporting files provide class labels and class-index mappings for interpreting predictions.

Prediction quality depends on the model, the supported classes, image quality, and preprocessing. Results should be treated as predictions rather than definitive agricultural diagnoses.

## Future Improvements

- Improve prediction accuracy across different lighting conditions.
- Expand the supported plant and disease categories.
- Add reliable disease prevention and treatment information.
- Improve error handling for unclear or unsupported images.
- Add automated tests and build validation.

## Developer

**Bharath**  
B.E. Computer Science and Engineering  
RYMEC, Ballari

## Disclaimer

Plant Guard AI is intended for educational and informational purposes. Its predictions may be inaccurate and should not replace professional agricultural advice.

## License

No license has been specified. Reuse and redistribution permissions should be clarified before others use or distribute the project.
