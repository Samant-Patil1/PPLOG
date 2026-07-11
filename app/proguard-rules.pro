# Minimal, app-specific ProGuard rules for PPLOG.
# Rely on the consumer ProGuard rules embedded in AndroidX, Koin, kotlinx.serialization,
# Ktor, Supabase, Coil and WorkManager.

-keepattributes Signature, RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations,
                RuntimeVisibleTypeAnnotations, AnnotationDefault, EnclosingMethod, InnerClasses
-keepattributes KotlinMetadata, *Annotation*
-keepattributes SourceFile,LineNumberTable

# -----------------------------------------------------------------------------
# Navigation: route definitions used by Compose Navigation.
# -----------------------------------------------------------------------------
-keep class com.pplog.app.ui.navigation.Screen { java.lang.String route; }
-keep class com.pplog.app.ui.navigation.Screen$* { *; }

# -----------------------------------------------------------------------------
# Room: app database, DAOs, entities and type converters.
# -----------------------------------------------------------------------------
-keep class com.pplog.app.data.local.PPLOGDatabase { *; }
-keep class com.pplog.app.data.local.Converters { *; }
-keep class com.pplog.app.data.local.dao.** { *; }
-keep class com.pplog.app.data.local.entity.** { *; }

# -----------------------------------------------------------------------------
# Application & DI.
# -----------------------------------------------------------------------------
-keep class com.pplog.app.PPLOGApplication { *; }
-keep class com.pplog.app.di.** { *; }

# -----------------------------------------------------------------------------
# ViewModels resolved by Koin / the AAC ViewModel machinery.
# -----------------------------------------------------------------------------
-keep class com.pplog.app.ui.screens.**.*ViewModel { *; }

# -----------------------------------------------------------------------------
# kotlinx.serialization models used by the app.
# -----------------------------------------------------------------------------
-keepclassmembers @kotlinx.serialization.Serializable class com.pplog.app.domain.model.** { *; }
-keepclassmembers @kotlinx.serialization.Serializable class com.pplog.app.data.remote.** { *; }

# -----------------------------------------------------------------------------
# Workers referenced by WorkManager via reflection.
# -----------------------------------------------------------------------------
-keep class com.pplog.app.data.local.SeedDatabaseWorker { *; }
-keep class com.pplog.app.data.remote.ImageDownloadWorker { *; }

# Supabase/Ktor pull in SLF4J but no binding is present at runtime.
-dontwarn org.slf4j.impl.StaticLoggerBinder
