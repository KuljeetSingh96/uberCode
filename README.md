# Uber Code Assignement
Uber Code Sample
Android Mobile Application

* Design pattern used is MVVMP(Data Binding + Live Data). 

* This project also contains basic utility classes.

* For network calls we have used Retrofit in this project which is annotation based network libraryto prepare requests. 

* For better optimisation we have used Rx Java/Android function for all API calls.

# This Project Structure follow  a clean architecture.

It uses of following libraries:

* [Retrofit2] (https://square.github.io/retrofit/) for REST API.

* [RX java] (https://github.com/ReactiveX/RxJava) for background process and Retrofit integration.

* [Picasso] (http://square.github.io/picasso/) for image loading.

# Here is what the app gradle look likes.
```
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'

android {
    compileSdkVersion rootProject.ext.compileSdkVersion
    def versionMajor = 1
    def versionMinor = 0
    def versionPatch = 1
    defaultConfig {
        applicationId "com.testtarget"
        minSdkVersion rootProject.ext.minSdkVersion
        targetSdkVersion rootProject.ext.targetSdkVersion
        versionCode 1
        versionName "${versionMajor}.${versionMinor}.${versionPatch}"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        targetCompatibility 1.8
        sourceCompatibility 1.8
    }
    dataBinding {
        enabled = true
    }
}
androidExtensions {
    experimental = true
}
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    //google
    implementation "com.android.support:appcompat-v7:$rootProject.supportLibraryVersion"
    implementation "com.android.support.constraint:constraint-layout:$rootProject.constraintLayoutVersion"
    implementation "com.android.support:recyclerview-v7:$rootProject.supportLibraryVersion"
    //ProcessLifecycleOwner
    implementation "android.arch.lifecycle:extensions:$rootProject.archLifecycleVersion"
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    //rx
    implementation "io.reactivex.rxjava2:rxandroid:$rootProject.rxAndroidVersion"
    implementation "io.reactivex.rxjava2:rxjava:$rootProject.rxJavaVersion"
    //networking
    implementation "com.squareup.retrofit2:retrofit:$rootProject.retrofitVersion"
    implementation "com.squareup.retrofit2:converter-gson:$rootProject.retrofitVersion"
    implementation "com.squareup.retrofit2:adapter-rxjava2:$rootProject.retrofitVersion"
    implementation "com.squareup.okhttp3:logging-interceptor:$rootProject.loggerVersion"
    //image
    implementation "com.squareup.picasso:picasso:$rootProject.picassoVersion"

    //instrument libraries
    androidTestImplementation "com.android.support.test:runner:$rootProject.runnerVersion"
    androidTestImplementation "com.android.support.test:rules:$rootProject.runnerVersion"
    androidTestImplementation "com.android.support.test.espresso:espresso-core:$rootProject.espressoVersion"
    androidTestImplementation("com.android.support.test.espresso:espresso-intents:$rootProject.espressoVersion")
    androidTestImplementation("com.android.support.test.espresso:espresso-contrib:$rootProject.espressoVersion") {
        // Necessary to avoid version conflicts
        exclude group: 'com.android.support', module: 'appcompat'
        exclude group: 'com.android.support', module: 'support-v4'
        exclude group: 'com.android.support', module: 'support-annotations'
        exclude module: 'recyclerview-v7'
    }
    //unit test libraries
    testImplementation 'org.mockito:mockito-core:2.18.3'
    testImplementation 'junit:junit:4.12'
    testImplementation "android.arch.core:core-testing:1.1.1"
}
repositories {
    mavenCentral()
}
```

# Start from

minSdkVersion 15

# Author

Kuljeet Singh Bhadwal
