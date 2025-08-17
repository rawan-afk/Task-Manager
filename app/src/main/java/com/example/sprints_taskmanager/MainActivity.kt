package com.example.sprints_taskmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.sprints_taskmanager.data.db.AppDatabase
import com.example.sprints_taskmanager.data.db.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(applicationContext)
        val userDao = db.userDao()
        val projectDao = db.projectDao()
        val taskDao = db.taskDao()

        val projectListState = mutableStateListOf<Project>()
        val contributorsList = listOf("Jana", "Ash")

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text("Projects in Database", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(16.dp))

                        Text("• Project 1 - Owner: Rawan - Contributors: ${contributorsList.joinToString(", ")}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("• Project 2 - Owner: Rawan - Contributors: Jana")

                        Spacer(modifier = Modifier.height(16.dp))
                        Text("", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))

                        LazyColumn {
                            items(projectListState) { project ->
                                Text("• ${project.title} - Owner ID: ${project.ownerId}")
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    db.runInTransaction {
                        db.clearAllTables()
                    }

                    val rawanId = userDao.insert(User(name = "Rawan", email = "rawan@example.com")).toInt()
                    val janaId = userDao.insert(User(name = "Jana", email = "jana@example.com")).toInt()
                    val ashId = userDao.insert(User(name = "Ash", email = "ash@example.com")).toInt()

                    val project1Id = projectDao.insert(Project(title = "Project 1", ownerId = rawanId)).toInt()
                    val project2Id = projectDao.insert(Project(title = "Project 2", ownerId = rawanId)).toInt()

                    val task1Id = taskDao.insert(Task(description = "Task 1 for Project 1"))
                    val task2Id = taskDao.insert(Task(description = "Task 2 for Project 1"))
                    val task3Id = taskDao.insert(Task(description = "Task 1 for Project 2"))

                    projectDao.insertProjectTaskCrossRef(ProjectTaskCrossRef(project1Id, task1Id.toInt()))
                    projectDao.insertProjectTaskCrossRef(ProjectTaskCrossRef(project1Id, task2Id.toInt()))
                    projectDao.insertProjectTaskCrossRef(ProjectTaskCrossRef(project2Id, task3Id.toInt()))

                    val allProjects = projectDao.getAllProjectsOnce()

                    withContext(Dispatchers.Main) {
                        projectListState.clear()
                        projectListState.addAll(allProjects)
                    }
                } catch (e: Exception) {
                    Log.e("Database", "Error: ${e.message}")
                }
            }
        }
    }
}