package com.emergencyzone.emergencyzone.repository.Data

import android.arch.persistence.room.*
import android.content.Context
import android.os.AsyncTask
import android.support.annotation.MainThread
import com.emergencyzone.emergencyzone.repository.Data.Entities.BlogPost
import com.emergencyzone.emergencyzone.repository.Data.Entities.EmergencyZoneNotification
import com.emergencyzone.emergencyzone.repository.Data.Entities.Notification
import com.emergencyzone.emergencyzone.repository.Data.Entities.WeatherAlert
import java.time.Instant
import java.time.LocalDate
import java.util.*

@Database(entities = [Notification::class, WeatherAlert::class, BlogPost::class, EmergencyZoneNotification::class], version = 1)
@TypeConverters(MainDatabase.Converters::class)
abstract class MainDatabase : RoomDatabase() {
    companion object {

        private var mInstance : MainDatabase? = null

        fun runAsync(block : MainDatabase.() -> Unit) : DatabaseAsync
        {
            return DatabaseAsync().execute {
                    block(mInstance!!)
            }
        }
        fun runInAsyncTransaction(block : MainDatabase.() -> Unit) : DatabaseAsync
        {
            return DatabaseAsync().execute {
                runInTransaction(Runnable {
                    block(mInstance!!)
                })
            }
        }

        fun getInstance() : MainDatabase
        {
            return mInstance!!
        }

        fun createInstance(context : Context) : MainDatabase
        {
            mInstance?.let {
                return it
            }

            mInstance = Room.databaseBuilder(context.applicationContext, MainDatabase::class.java, "main").build()
            return mInstance!!
        }
    }

    abstract fun notifications() : NotificationDao
    abstract fun weatherAlerts() : WeatherAlertDao
    abstract fun blogPosts() : BlogDao
    abstract fun emergencyZoneNotifications() : EmergencyZoneNotificationDao

    class Converters
    {
        @TypeConverter
        fun fromDateString(date : String) : Date?
        {
            if (date.isEmpty())
            {
                return null
            }
            return  Date(date)
        }

        @TypeConverter
        fun toDateString(date : Date?) : String
        {
            return date?.toString() ?: ""
        }
    }

    class DatabaseAsync : AsyncTask<Unit, Unit, Unit>()
    {
        private lateinit var mtask : MainDatabase.() -> Unit
        private var mFinishTask : (MainDatabase.() -> Unit)? = null
        fun execute (block : MainDatabase.() -> Unit) : DatabaseAsync
        {
            mtask = block
            execute()
            return this
        }

        fun onComplete(block : MainDatabase.() -> Unit)
        {
            mFinishTask = block
        }

        override fun doInBackground(vararg p0: Unit?){
            mtask(MainDatabase.getInstance())
        }

        override fun onPostExecute(result: Unit?) {
            mFinishTask?.let { it(MainDatabase.getInstance()) }
        }



    }
}