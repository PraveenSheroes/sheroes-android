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

-dontwarn com.flurry.**
-dontwarn okio.**
-dontwarn oauth.signpost.signature.**
-dontwarn com.nhaarman.**
-dontwarn com.squareup.**
-dontwarn com.google.android.gms.**
-dontwarn com.androidquery.auth.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.CheckReturnValue
-dontwarn android.support.design.**
-dontwarn com.crashlytics.**
-dontwarn io.intercom.**
-dontwarn com.freshdesk.hotline.**

-keepclassmembers class * extends com.apptimize.ApptimizeTest {
    <methods>;
}

#apptimize
-keep class com.apptimize.** { *; }

-keep class android.support.v4.view.ViewPager
-keepclassmembers class android.support.v4.view.ViewPager$LayoutParams { *; }


-keep class com.google.android.gms.analytics.Tracker { *; }
-keep class com.google.analytics.tracking.android.Tracker { *; }
-keep class com.flurry.android.FlurryAgent { *; }

-keep public class * extends android.support.design.widget.CoordinatorLayout$Behavior {
    public <init>(android.content.Context, android.util.AttributeSet);
}
