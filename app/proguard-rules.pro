# ProGuard rules for PPLOG

# Preserve annotations and generic signatures needed by reflection-based libraries.
-keepattributes Signature, RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations,
                RuntimeVisibleTypeAnnotations, AnnotationDefault, EnclosingMethod, InnerClasses

# Kotlin metadata is required by Koin, kotlinx.serialization and Room at runtime.
-keepattributes KotlinMetadata, *Annotation*

# Keep line-number information for debugging stack traces (optional).
-keepattributes SourceFile,LineNumberTable

# -----------------------------------------------------------------------------
# Compose
# -----------------------------------------------------------------------------
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }
-keep @androidx.compose.runtime.Composable class * { *; }
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}
-keepclassmembers class * {
    @androidx.compose.runtime.ReadOnlyComposable *;
}
-keep class androidx.compose.ui.graphics.** { *; }
-keep class androidx.compose.runtime.** { *; }

# -----------------------------------------------------------------------------
# Navigation
# -----------------------------------------------------------------------------
-keep class com.pplog.app.ui.navigation.** { *; }
-keepclassmembers class com.pplog.app.ui.navigation.Screen {
    java.lang.String route;
}

# -----------------------------------------------------------------------------
# Room
# -----------------------------------------------------------------------------
-keep class androidx.room.** { *; }
-keepclassmembers class androidx.room.** { *; }
-dontwarn androidx.room.**

-keep class com.pplog.app.data.local.PPLOGDatabase { *; }
-keep class com.pplog.app.data.local.Converters { *; }
-keep class com.pplog.app.data.local.dao.** { *; }
-keep class com.pplog.app.data.local.entity.** { *; }
-keepclassmembers class com.pplog.app.data.local.entity.** { *; }

# -----------------------------------------------------------------------------
# Koin / Dependency injection
# -----------------------------------------------------------------------------
-keep class org.koin.** { *; }
-keepclassmembers class org.koin.** { *; }
-dontwarn org.koin.**

-keep class com.pplog.app.di.** { *; }
-keep class com.pplog.app.PPLOGApplication { *; }

# Keep classes that are injected by Koin so constructors/factories are not removed.
-keep class com.pplog.app.data.repository.** { *; }
-keep class com.pplog.app.ui.screens.** { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }

# -----------------------------------------------------------------------------
# Kotlinx Serialization
# -----------------------------------------------------------------------------
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class kotlinx.serialization.** { *; }
-dontwarn kotlinx.serialization.**

-keepclassmembers class * {
    @kotlinx.serialization.Serializable <methods>;
}
-keep @kotlinx.serialization.Serializable class * { *; }
-keepclassmembers @kotlinx.serialization.Serializable class * {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep serializable model classes used by the app even if no @Serializable is currently present.
-keep class com.pplog.app.domain.model.** { *; }

# -----------------------------------------------------------------------------
# Supabase / Ktor
# -----------------------------------------------------------------------------
-keep class io.github.jan.supabase.** { *; }
-keepclassmembers class io.github.jan.supabase.** { *; }
-dontwarn io.github.jan.supabase.**

-keep class io.ktor.** { *; }
-keepclassmembers class io.ktor.** { *; }
-dontwarn io.ktor.**

# -----------------------------------------------------------------------------
# Coil
# -----------------------------------------------------------------------------
-keep class coil.** { *; }
-keepclassmembers class coil.** { *; }
-dontwarn coil.**

# -----------------------------------------------------------------------------
# WorkManager
# -----------------------------------------------------------------------------
-keep class androidx.work.** { *; }
-keepclassmembers class androidx.work.** { *; }
-dontwarn androidx.work.**

-keep class com.pplog.app.data.local.SeedDatabaseWorker { *; }
-keep class com.pplog.app.data.remote.ImageDownloadWorker { *; }

# -----------------------------------------------------------------------------
# AndroidX / general
# -----------------------------------------------------------------------------
-dontwarn org.slf4j.**

-keep class androidx.lifecycle.** { *; }
-keepclassmembers class androidx.lifecycle.** { *; }
-keep class androidx.core.** { *; }
-keep class androidx.activity.** { *; }
-keep class androidx.datastore.** { *; }
