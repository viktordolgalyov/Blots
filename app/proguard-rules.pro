-keep class * extends android.app.Activity
-keep class * extends android.app.Application
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider
-keep class * extends androidx.** { *; }
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keepattributes *Annotation*

# Kotlin
-keep class kotlin.Metadata { *; }
-keepclassmembers class *$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.** {
    public synthetic <methods>;
}

# Kotlin coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.** {
    volatile <fields>;
}

# Timber
-keep class timber.log.Timber { *; }
-dontwarn timber.log.Timber

# Material3
-keep class androidx.compose.material3.** { *; }
-dontwarn androidx.compose.material3.**

# MockK
-dontwarn io.mockk.proxy.jvm.**
-keepattributes InnerClasses
