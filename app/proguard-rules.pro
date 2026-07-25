# Retrofit
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.<Method> <methods>;
}

# Gson
-keep class com.google.gson.** { *; }
-keep interface com.google.gson.** { *; }
-keepclasseswithmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Room
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }

# TensorFlow Lite
-keep class org.tensorflow.** { *; }
-keep interface org.tensorflow.** { *; }

# Model classes
-keep class com.example.chessvisionpro.model.** { *; }
-keep class com.example.chessvisionpro.viewmodel.** { *; }
-keep class com.example.chessvisionpro.ui.** { *; }
-keep class com.example.chessvisionpro.api.** { *; }
-keep class com.example.chessvisionpro.vision.** { *; }

# Kotlin coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}

# Minimize logging in release builds
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
