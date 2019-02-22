# Memento2

A To Do List Android App rebuilt with Material Design, Room and AndroidX. This app is available for use on **Google Play**, [here](https://play.google.com/store/apps/details?id=com.naseem.naseemashraf.memento2&hl=en).
This is a complete rewrite of [Memento Android App](https://github.com/mdnaseemashraf/Memento) built for old Froyo Android OS (2.2).

## Features

* Used [**Jetpack**](https://developer.android.com/jetpack/) components to rebuild app.
* Material Design UI overhaul using FAB, [BottomSheet Dialog](https://developer.android.com/reference/android/support/design/widget/BottomSheetDialog) and RecyclerView.
* **Room** implementation replacing previous SQLite implementation for database.
* [God Class Anti-Pattern](https://en.wikipedia.org/wiki/God_object) from previous Memento version removed.
* Added Instrumented Tests from AndroidX.
* [**AndroidX**](https://developer.android.com/jetpack/androidx) support library used for all components.

## Screen Caps

![Task Activity Screen Shot](https://raw.githubusercontent.com/mdnaseemashraf/Memento2/master/Screenshots/Screenshot_20190220-193009.png)
![Task Activity in Use Screen Shot](https://github.com/mdnaseemashraf/Memento2/blob/master/Screenshots/Screenshot_20190220-193033.png)
![Adding Task Screen Shot](https://github.com/mdnaseemashraf/Memento2/blob/master/Screenshots/Screenshot_20190220-192908.png)

## Testing

Unit tests were ignored here. This build includes 8 **Instrumentation Tests**(that need to be run on a device) provided in the instrumentedTests directory of the app. Some of the tests perform and check basic operations like:
1. Click Add Task FAB and check for BottomSheet Dialog.
2. Check for an existing Task in RecyclerView.
3. Add a Task, save it and then check if it exists.
4. Add a Task, save it and then edit it.
5. Check all CRUD and other queries on Room Database.
6. Perform swipe gesture to remove Task from RecyclerView.

This build was further tested in [**Firebase Test Lab**](https://firebase.google.com/docs/test-lab/).

## Firebase Robo Crawl Output

![Robo Crawl Graph](https://00e9e64bac7e4d26035d38ed34a63b678cddf9ef066dcc460b-apidata.googleusercontent.com/download/storage/v1/b/test-lab-icbu0tcpa91qu-mbmbq7iquh6i2/o/web-build_2019-02-20T13:38:16.849Z_2ztk%2Fwalleye-28-en_US-portrait%2Fartifacts%2Foutput%2Fsitemap.png?qk=AD5uMEsED4-qpfq9j4lTdfOVli-EP98Znote3jNeBYtK2FB9YU7c2PIV0cPjygh-E5jBiqISssHv6ckuut8nnzyRT6-9pwYn5wpsLWI5k8ZaXTPufS6nb2Fl0U6opxjmBgNaPCcPkBtV62h5gTAk1GkSrRiEfGdb5F7Uixwa7HTnXXrMTv-xiJFyw8xrDcspvoSQyi5o5OUj6pMx9cm8U_YlIVQjjG3MqoI-NWW7LcLSb-L2pe7f-1N4KkXy4Njh3jarpeFPalagR3Q9prHdR6ZsYJye3k1hyxMcYf3c_axObQJKHtbMTE5y6KVrCuidGXRQp5fPvVDEKFPLPcL8bIbbqKZQitUJigChbtfbeX-HK0pDnD1S1re84zqH4Sswtw-8UnRZC8rFqC3dIjG2sGoCwGrVO5c4FHlYIHswuhsMHz5vD1aeebGrhzPI3ebyzvNzHciH_iEvU-37NZxw0o_cl2EcySE31NoCTkh0EKE6t1h5zBsMTCk7dShrLrJKKxRHNIpkfsNIm6JnPMWH1Mi2WYtB3I5VPG976spSAFiYJTZ5Jx1jVQn6TZEOhEGCPEOwRjNbG3ql5bZcxIcpwrWv1qEMJGFh-a2h7OIsFvYB4jxc5M7ulCu92AfIzvyabrVRVnnl1rMDjNmWpBdJprKnAsBhQn5Y_HoWue11nT3A-560kipeH65F6yNdKdfsNXzx0awTJnS0BrtMHxd8ZNEoitjstzqmSjbL_00JGpP3h2SnJAARCpfmkHL3KrjmF47-xx7k6tdOdzSb4pa-T8as1pQv5gdek2VmSMjk-94GBqI3ftyhEaCnE7QItsVARnl_sMRhng5Rq1o1kjbaCXFcJjCV17x6jJA-b3ppXNmRrSpAPe0yAeab1vTgbF47kibpM1HBtrRlbVpLj3svF_tsLAeVl58H8ZMDSDBp91omBPTzCBFSjBw)

Stat|Value
----|------
App start time (Time to initial display)|604ms

Graphics stats | Value
---------------|------
Missed VSync|0%
High input latency|8%
Slow UI thread|2%
Slow draw commands|0%
Slow bitmap uploads|0%

## Firebase Instrumentation Test Results

Stat|Value
----|-----
App start time (Time to initial display) | 534ms

Graphics stats|Value
--------------|-----
Missed VSync|3%
High input latency|24%
Slow UI thread|8%
Slow draw commands|0%
Slow bitmap uploads|0%


## Known Issues

The RecyclerView being used from AndroidX support library is in alpha, which does make the app a bit unstable when swiping off elements and then adding new tasks which might not appear or be saved to app.
Read more about AndroidX Release Notes [here](https://developer.android.com/jetpack/androidx/releases/) (Dated: February 21, 2019).
