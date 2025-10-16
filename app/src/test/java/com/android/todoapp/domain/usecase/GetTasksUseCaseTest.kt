package com.android.todoapp.domain.usecase

import com.android.todoapp.domain.model.Task
import com.android.todoapp.domain.repository.TaskRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para GetTasksUseCase.
 * Testa a lógica de negócio de obter tarefas.
 */
class GetTasksUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var getTasksUseCase: GetTasksUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        getTasksUseCase = GetTasksUseCase(repository)
    }

    @Test
    fun `when invoke called, should return tasks from repository`() = runTest {
        // Given
        val expectedTasks = listOf(
            Task(1, "Task 1", false),
            Task(2, "Task 2", true)
        )
        every { repository.getTasks() } returns flowOf(expectedTasks)

        // When
        val result = getTasksUseCase()

        // Then
        result.collect { tasks ->
            assertEquals(expectedTasks, tasks)
            assertEquals(2, tasks.size)
            assertEquals("Task 1", tasks[0].title)
            assertEquals("Task 2", tasks[1].title)
            assertFalse(tasks[0].isDone)
            assertTrue(tasks[1].isDone)
        }
    }

    @Test
    fun `when invoke called with empty list, should return empty list`() = runTest {
        // Given
        val emptyTasks = emptyList<Task>()
        every { repository.getTasks() } returns flowOf(emptyTasks)

        // When
        val result = getTasksUseCase()

        // Then
        result.collect { tasks ->
            assertTrue(tasks.isEmpty())
        }
    }
}
