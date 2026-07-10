package com.pplog.app.data.local

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pplog.app.data.local.dao.ExerciseDao
import com.pplog.app.data.local.seed.ExerciseSeedData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SeedDatabaseWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val exerciseDao: ExerciseDao by inject()

    override suspend fun doWork(): Result {
        val count = exerciseDao.count()
        if (count == 0) {
            exerciseDao.insertAll(ExerciseSeedData.getExercises())
        }
        return Result.success()
    }
}
