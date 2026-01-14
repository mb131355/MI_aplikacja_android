package com.example.mi.powiadomienia

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class PracownikZdrapki(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Notyfikacje.pokazPowiadomienieZdrapki(applicationContext)
        return Result.success()
    }
}
