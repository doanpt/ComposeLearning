# Digital Manual App for Android Automotive OS

## Overview
This repository contains the source code for a digital manual application designed specifically for Android Automotive OS. The app aims to provide users with a convenient way to access and reference user manuals for automobiles directly on their Head Unit screens, eliminating the need to rely on physical guidebooks.

## Features
- **Offline Mode**: Users can download manuals for offline access after their initial download.
- **Cloud Synchronization**: The app syncs manual updates and new content from the cloud to ensure users have the latest information.
- **Local Storage**: Manuals are stored locally on the device for quick access and offline usage.
- **Jetpack Libraries**: Utilizes modern Jetpack libraries for efficient development and improved performance.

## Requirements
- Android Automotive OS compatible device
- Internet connection for initial setup and cloud synchronization
- Sufficient local storage space for downloaded manuals

## Installation and Usage
1. Clone the repository to your development environment.
2. Open the project in Android Studio.
3. Build the application using the following command:
   ```bash
   ./gradlew assembleDebug
    
    The output will be saved in app/build/outputs.
4. To run coverage, use the following command:
    ```bash
    ./gradlew jacocoTestReport

    The output will be saved in build/reports/jacoco/index.html.

5. Run the application on an Android Automotive OS emulator or compatible device.
6. Follow on-screen instructions for initial setup and manual download.

## Contact
For questions or inquiries, feel free to reach out to [stefanos.stamogiorgos@nttdata.com](mailto:stefanos.stamogiorgos@nttdata.com).