package com.example.sprints_taskmanager.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sprints_taskmanager.data.db.dao.AttachmentDao
import com.example.sprints_taskmanager.data.db.dao.ProjectDao
import com.example.sprints_taskmanager.data.db.dao.TaskDao
import com.example.sprints_taskmanager.data.db.dao.UserDao
import com.example.sprints_taskmanager.data.db.entities.Attachment
import com.example.sprints_taskmanager.data.db.entities.Project
import com.example.sprints_taskmanager.data.db.entities.Task
import com.example.sprints_taskmanager.data.db.entities.User
import com.example.sprints_taskmanager.data.db.entities.ProjectTaskCrossRef

@Database(
    entities = [User::class, Project::class, Task::class, Attachment::class, ProjectTaskCrossRef::class],
    version = 3,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao
    abstract fun attachmentDao(): AttachmentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "task_manager_db"
            ).fallbackToDestructiveMigration()
                .build()   }

}