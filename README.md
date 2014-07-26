iBeaconRecord
=============

iBeaconRecord is a simple iBeacon recorder application to record iBeacon information for research purpose. It is built based on April-brother Android SDK and tested on AprilBeacon 241 and Nexus 4. Generally speaking, to run this application (or any ibeacon related softwares), your mobile phone should be equipped with Bluetooth 4.0 and Android 4.3.

Download current package [*Here*](https://github.com/Chrisplus/iBeaconRecord/blob/master/release_package/BeaconRecorder.apk?raw=true)

### How to use

![screenshot](https://raw.githubusercontent.com/Chrisplus/iBeaconRecord/master/image/cap.png)

* Open iBeaconRecord.
* Click right top button to start recording
* Click right top button to stop recording
* Pressing back or home button leads to stop recording too


### Data Formart & Log Location
The log files locate at /sdcard/Beacon_Log/. They are named by timestamp.csv. The data formart is:

Timestamp, UUID, RSSI, Distance

where the distance is accessed using April-Brother SDK. Please using [*JD*](http://jd.benow.ca/) to check its implementation details.

