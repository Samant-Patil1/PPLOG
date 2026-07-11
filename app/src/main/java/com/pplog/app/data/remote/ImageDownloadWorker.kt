package com.pplog.app.data.remote

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ImageDownloadWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        const val KEY_EXERCISE_ID = "exercise_id"
        const val KEY_IMAGE_PATH = "image_path"
        const val KEY_OUTPUT_PATH = "output_path"
    }

    override suspend fun doWork(): Result {
        val exerciseId = inputData.getString(KEY_EXERCISE_ID) ?: return Result.failure()
        val imagePath = inputData.getString(KEY_IMAGE_PATH) ?: return Result.failure()

        return try {
            val bucket = SupabaseClient.client.storage["exercise-images"]
            val bytes = bucket.downloadAuthenticated(imagePath)
            val outputFile = File(applicationContext.filesDir, "exercises/$exerciseId.png").apply {
                parentFile?.mkdirs()
            }
            withContext(Dispatchers.IO) {
                outputFile.writeBytes(bytes)
            }
            val output = workDataOf(KEY_OUTPUT_PATH to outputFile.absolutePath)
            Result.success(output)
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
