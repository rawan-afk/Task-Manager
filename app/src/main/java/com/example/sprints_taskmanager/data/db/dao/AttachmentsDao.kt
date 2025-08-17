package com.example.sprints_taskmanager.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sprints_taskmanager.data.db.entities.Attachment

@Dao
interface AttachmentDao {
    @Insert
    suspend fun insert(attachment: Attachment): Long

    @Query("SELECT * FROM attachments WHERE taskId = :taskId")
    suspend fun getForTask(taskId: Int): List<Attachment>
}