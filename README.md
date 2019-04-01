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

<img src="https://raw.githubusercontent.com/mdnaseemashraf/Memento2/master/Screenshots/Screenshot_20190220-193009.png" width="120" height="200">  <img src="https://github.com/mdnaseemashraf/Memento2/blob/master/Screenshots/Screenshot_20190220-193033.png" width="120" height="200">  <img src="https://github.com/mdnaseemashraf/Memento2/blob/master/Screenshots/Screenshot_20190220-192908.png" width="120" height="200">

## Testing

Unit tests were ignored here. This build includes 8 **Instrumentation Tests**(that need to be run on a device) provided in the instrumentedTests directory of the app. Some of the tests perform and check basic operations like:
1. Click Add Task FAB and check for BottomSheet Dialog.
2. Check for an existing Task in RecyclerView.
3. Add a Task, save it and then check if it exists.
4. Add a Task, save it and then edit it.
5. Check all CRUD and other queries on Room Database.
6. Perform swipe gesture to remove Task from RecyclerView.

This build was further tested in [**Firebase Test Lab**](https://firebase.google.com/docs/test-lab/).

Updated with [**CircleCI**](https://circleci.com/) + Firebase Test Lab to test app. Current status: [![CircleCI](https://circleci.com/gh/mdnaseemashraf/Memento2.svg?style=svg)](https://circleci.com/gh/mdnaseemashraf/Memento2)

Updated with [**Codacy**](https://www.codacy.com/) to show static code review. Current grading: [![Codacy Badge](https://api.codacy.com/project/badge/Grade/54ed7f4291664832aa2bd8672c203104)](https://www.codacy.com/app/mdnaseemashraf/Memento2?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mdnaseemashraf/Memento2&amp;utm_campaign=Badge_Grade)

## Firebase Robo Crawl Output

![Robo Call Graph](https://github.com/mdnaseemashraf/Memento2/blob/master/Screenshots/Firebase%20Robo%20Test%20Crawl%20Graph.png?raw=true)

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
