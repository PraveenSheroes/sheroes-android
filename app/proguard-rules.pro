# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepattributes *Annotation*,EnclosingMethod,JavascriptInterface
-keepattributes SourceFile,LineNumberTable

-keep public class * extends android.app.Activity
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class com.mobileapptracker.** { public *; }
-keep public class com.google.android.gms.ads.identifier.** { *; }
-keep class com.mmt.yipstay_consumer.utils.glide.** { *; }
# ButterKnife 7

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# RxJava 0.21

-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontwarn sun.misc.Unsafe

# Document

-keep public class org.jsoup.** {
public *;
}
# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

# Retrofit 1.X

-keep class okhttp3.** { *; }
-keep class retrofit2.** { *; }
-keep interface okhttp3.** { *; }

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**
-dontwarn rx.**

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Facebook

-keep class com.facebook.** { *; }
-keepattributes Signature
# Updated as of Stetho 1.1.1
#
# Note: Doesn't include Javascript console lines. See https://github.com/facebook/stetho/tree/master/stetho-js-rhino#proguard
-keep class com.facebook.stetho.** { *; }

-keep class **.model.**{*;}
-keep class android.support.design.widget.** { *; }
-keep class com.crashlytics.** { *; }
-keep class com.freshdesk.hotline.** { *; }
-keep class appliedlife.pvtltd.SHEROES.** { *; }
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
#apptimize
-keepclassmembers class * extends com.apptimize.ApptimizeTest {
    <methods>;
}
-keep class com.apptimize.** { *; }


-keepattributes InnerClasses

-keep class **.R
-keep class **.R$* {
<fields>;
}
-keep class com.flurry.** { *; }
-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet, int);
}

#Google Play Services related Exception
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keep interface android.support.design.widget.** { *; }
-dontwarn com.google.android.gms.location.**
-dontwarn com.google.android.gms.gcm.**
-dontwarn com.google.android.gms.iid.**
-dontwarn okio.**

-keep class com.google.android.gms.gcm.** { *; }
-keep class com.google.android.gms.iid.** { *; }
-keep class com.google.android.gms.location.** { *; }
-keep class com.facebook.drawee.**{*;}
-dontwarn com.facebook.drawee.

-dontwarn com.google.android.gms.location.**
-dontwarn com.google.android.gms.gcm.**
-dontwarn com.google.android.gms.iid.**

#Moengage
-dontwarn com.google.android.gms.location.**
-dontwarn com.google.android.gms.gcm.**
-dontwarn com.google.android.gms.iid.**

-keep class com.google.android.gms.gcm.** { *; }
-keep class com.google.android.gms.iid.** { *; }
-keep class com.google.android.gms.location.** { *; }

-keep class com.moe.pushlibrary.activities.** { *; }
-keep class com.moe.pushlibrary.MoEHelper
-keep class com.moengage.locationlibrary.GeofenceIntentService
-keep class com.moe.pushlibrary.InstallReceiver
-keep class com.moengage.push.MoEPushWorker
-keep class com.moe.pushlibrary.providers.MoEProvider
-keep class com.moengage.receiver.MoEInstanceIDListener
-keep class com.moengage.worker.MoEGCMListenerService
-keep class com.moe.pushlibrary.models.** { *;}
-keep class com.moengage.core.GeoTask
-keep class com.moengage.location.GeoManager
-keep class com.moengage.inapp.InAppManager
-keep class com.moengage.push.PushManager
-keep class com.moengage.inapp.InAppController

-keep class com.moengage.pushbase.activities.PushTracker
-keep class com.moengage.pushbase.activities.SnoozeTracker
-keep class com.moengage.pushbase.push.MoEPushWorker
-keep class com.moe.pushlibrary.MoEWorker
-keep class com.moe.pushlibrary.AppUpdateReceiver
-keep class com.moengage.core.MoEAlarmReceiver


-dontwarn com.moengage.location.GeoManager
-dontwarn com.moengage.core.GeoTask
-dontwarn com.moengage.receiver.*
-dontwarn com.moengage.worker.*
-dontwarn com.moengage.inapp.ViewEngine

-keep class com.delight.**  { *; }
#endregion

-keep class java.awt.** { *; }
-keep class com.google.firebase.iid.** { *; }
-keep class com.google.firebase.messaging.FirebaseMessagingService { *; }
-keep class com.google.android.gms.measurement.AppMeasurement { *; }
-keep class com.google.android.gms.common.internal.safeparcel.** { *; }
-keep class org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement { *; }
-keep class java.nio.file.**
-keep class java.lang.invoke.**

-dontwarn com.moengage.firebase.**
-dontwarn com.google.firebase.**

-dontwarn com.flurry.**
-dontwarn okio.**
-dontwarn oauth.signpost.signature.**
-dontwarn com.nhaarman.**
-dontwarn com.squareup.**
-dontwarn com.androidquery.auth.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.CheckReturnValue
-dontwarn android.support.design.**
-dontwarn com.crashlytics.**
-dontwarn io.intercom.**
-dontwarn com.freshdesk.hotline.**
-dontwarn com.google.android.gms.cast.**
-keepclassmembers class * extends com.apptimize.ApptimizeTest {
    <methods>;
}

#apptimize
-keep class com.apptimize.** { *; }

-keep class android.support.v4.view.ViewPager
-keepclassmembers class android.support.v4.view.ViewPager$LayoutParams { *; }

-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl

-keep class com.google.android.gms.analytics.Tracker { *; }
-keep class com.google.analytics.tracking.android.Tracker { *; }
-keep class com.flurry.android.FlurryAgent { *; }
-keep class android.support.v7.widget.SearchView { *; }
-keep public class * extends android.support.design.widget.CoordinatorLayout$Behavior {
    public <init>(android.content.Context, android.util.AttributeSet);
}
