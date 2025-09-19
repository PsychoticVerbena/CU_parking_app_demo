# CU Parking App Demo

CU Parking App Demo is a simple Android application simulating Clemson University parking. It allows users to view parking lot availability, manage a parking session, and access helpful links related to Clemson parking.

## **Features**

**Parking Lot Overview:** Displays a list of parking lots with current availability (mock data).

**Lot Details:** View individual parking lots and their available spots.

**MySpace:** Reserve a parking spot with a countdown timer for your session. Supports a custom duration (1–6 hours) and shows remaining time on the home page.

## **Navigation Links:**

Open Clemson University parking in Google Maps.

Access the Clemson Parking Services website for more info.

**Persistent Session:** The timer keeps running even if the user navigates away from the app.

**Session Management:** End a session manually with a button.

## **Installation**

Clone the repository:
Open in Android Studio.

Sync Gradle and build the project.

Run on an emulator or physical Android device (minimum SDK 21).

## **Technologies**

**Language:** Java

**Framework:** Android SDK

**UI:** Material Design Components

**Architecture:** Singleton pattern for session management

## **Usage**

Launch the app to view parking lots and their availability.

Tap a lot to see details and available spots.

Navigate to MySpace to reserve a spot and start a countdown timer.

Use the navigation drawer for quick access to maps or parking services.

## Future Improvements

Integrate real-time sensor data for actual parking availability.

Connect with a campus map API for interactive lot visualization.

Implement notifications when parking session is nearing expiration.

Allow session extensions and free parking after hours.
