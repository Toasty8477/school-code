# WearOS Trade Worker Health Trackinator 3000

## What is this project anyways?
This project is a health tracking app intended for use by trade workers. Trade workers aren't
allowed to use a phone on the job but they are allowed to have a watch on. This app is designed to
be an activity tracking app like [Google Fit]() or [Strava]() that is contained entirely on a WearOS
device, no phone needed. It allows for various types of health tracking as well as configurable alerts
to keep workers safe on the job. The app can also sync with Google Fit on a users phone.

## Yeah that's cool but how do I install it?
Currently to install the app you will have to build it yourself from source. I promise it's not as
scary as it sounds, Android Studio does most of the work for you. To build the app you will need
the following things:
- A computer running Windows, MacOS, Linux, or ChromeOS
- Android Studio 2025.1.3 or newer
- A WearOS device
- A Wi-Fi access point that both your WearOS device and computer can connect to
- If you are using windows you will need to install git

To build the actual app follow these steps:
1. Enable ADB debugging on your watch
   1. Open the **settings** menu
   2. Tap **System > About** or **System > About > Versions**
   3. Find the **Build number** and tap it 7 times.
   4. Enter your pin if you have one
   5. Go back to the **settings** menu
   6. Scroll to the bottom and tap **Developer Options**
   7. Enable the **ADB Debugging** option
2. Open Android Studio and select **Pair Devices Using Wi-Fi** from the run configurations menu
3. Select **Pair Device with pairing code**
4. On your watch go to **Developer Options**, tap **Wireless Debugging**, and turn it on
5. Enter the six digit code into Android Studio
6. In Android Studio select your watch in the run configurations menu
7. Press the **Run** button
8. Wait for the app to build and install to your device

You should now have a working version of the app!

## So... How do I use this thing?
Just put the watch on your wrist, open the app, and tracking should start automatically!

### The Team
- Alex Horton [hortona@msoe.edu](mailto:hortona@msoe.edu)
- Spiro Kusak [kusaks@msoe.edu](mailto:kusaks@msoe.edu)
- Robert Maltes [maltesr@msoe.edu](mailto:maltesr@msoe.edu)

### Credits & Acknowledgements
Google - Instructions on how to enable ADB on WearOS and how to connect a watch to android studio

### Known Issues
- Steps and distance tracking do not work in the emulator as mock data isn't generated.
- Speed is unsupported by MeasureClient and PassiveMonitoringClient and does not update.
- The app does not function correctly when permissions are explicitly denied when prompted.