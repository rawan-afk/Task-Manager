package com.example.sprints_taskmanager.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sprints_taskmanager.data.db.entities.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task): Long

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getById(id: Int): Task?

    @Query("""
        SELECT tasks.* 
        FROM tasks
        INNER JOIN project_task_cross_ref
        ON tasks.id = project_task_cross_ref.taskId
        WHERE project_task_cross_ref.projectId = :projectId
    """)
    suspend fun getTasksInProjectOnce(projectId: Int): List<Task>

    @Query("""
        SELECT tasks.* 
        FROM tasks
        INNER JOIN project_task_cross_ref
        ON tasks.id = project_task_cross_ref.taskId
        WHERE project_task_cross_ref.projectId = :projectId
    """)
    fun getTasksInProjectFlow(projectId: Int): Flow<List<Task>>

    @Query("""
        SELECT tasks.* 
        FROM tasks
        INNER JOIN project_task_cross_ref
        ON tasks.id = project_task_cross_ref.taskId
        WHERE project_task_cross_ref.projectId = :projectId
    """)
    fun getTasksInProjectLiveData(projectId: Int): LiveData<List<Task>>
}