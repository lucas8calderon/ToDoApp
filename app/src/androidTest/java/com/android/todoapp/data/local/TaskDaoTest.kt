package com.android.todoapp.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.todoapp.data.mapper.TaskMapper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Testes instrumentados para TaskDao.
 * Testa o comportamento real do Room Database.
 */
@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        
        taskDao = database.todoDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `when insert task, should be able to retrieve it`() = runTest {
        // Given
        val taskEntity = TaskEntity(
            id = 1,
            title = "Test Task",
            isDone = false
        )

        // When
        taskDao.insert(taskEntity)
        val tasks = taskDao.getAllTodos().first()

        // Then
        assertEquals(1, tasks.size)
        assertEquals("Test Task", tasks[0].title)
        assertEquals(false, tasks[0].isDone)
    }

    @Test
    fun `when insert multiple tasks, should retrieve all`() = runTest {
        // Given
        val task1 = TaskEntity(1, "Task 1", false)
        val task2 = TaskEntity(2, "Task 2", true)

        // When
        taskDao.insert(task1)
        taskDao.insert(task2)
        val tasks = taskDao.getAllTodos().first()

        // Then
        assertEquals(2, tasks.size)
        assertTrue(tasks.any { it.title == "Task 1" })
        assertTrue(tasks.any { it.title == "Task 2" })
    }

    @Test
    fun `when update task, should reflect changes`() = runTest {
        // Given
        val taskEntity = TaskEntity(1, "Test Task", false)
        taskDao.insert(taskEntity)

        // When
        val updatedTask = taskEntity.copy(isDone = true)
        taskDao.update(updatedTask)
        val tasks = taskDao.getAllTodos().first()

        // Then
        assertEquals(1, tasks.size)
        assertTrue(tasks[0].isDone)
    }

    @Test
    fun `when delete task, should remove from database`() = runTest {
        // Given
        val taskEntity = TaskEntity(1, "Test Task", false)
        taskDao.insert(taskEntity)

        // When
        taskDao.delete(taskEntity)
        val tasks = taskDao.getAllTodos().first()

        // Then
        assertTrue(tasks.isEmpty())
    }
}
