package com.substantive.prepare.repository.Room

import android.arch.persistence.room.*
import android.content.Context
import android.os.AsyncTask
import com.substantive.prepare.repository.Room.Entities.BlogPost
import com.substantive.prepare.repository.Room.Entities.EmergencyZoneNotification
import com.substantive.prepare.repository.Room.Entities.WeatherAlert
import com.substantive.prepare.repository.Room.Entities.ZoneEntity
import java.util.*

@Database(entities = [WeatherAlert::class, ZoneEntity::class, ForecastDao::class, BlogPost::class, EmergencyZoneNotification::class], version = 1)
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

    abstract fun weatherAlerts() : WeatherAlertDao
    abstract fun blogPosts() : BlogDao
    abstract fun emergencyZoneNotifications() : EmergencyZoneNotificationDao
    abstract fun zones() : ZoneDao
    abstract fun forecasts() : ForecastDao

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