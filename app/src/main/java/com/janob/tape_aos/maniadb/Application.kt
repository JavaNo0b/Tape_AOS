package com.janob.tape_aos.maniadb

import android.app.Application
import com.janob.tape_aos.maniadb.repository.Repository

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        Repository.initialize(this)
    }
}