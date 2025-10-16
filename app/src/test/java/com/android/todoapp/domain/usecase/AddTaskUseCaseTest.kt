package com.android.todoapp.domain.usecase

import com.android.todoapp.domain.model.Task
import com.android.todoapp.domain.repository.TaskRepository
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes unitários para AddTaskUseCase.
 * Testa a lógica de negócio de adicionar tarefas.
 */
class AddTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var addTaskUseCase: AddTaskUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        addTaskUseCase = AddTaskUseCase(repository)
    }

    @Test
    fun `when addTask called with valid title, should call repository addTask`() = runTest {
        // Given
        val title = "Test Task"

        // When
        addTaskUseCase(title)

        // Then
        val taskSlot = slot<Task>()
        coVerify { repository.addTask(capture(taskSlot)) }
        
        val capturedTask = taskSlot.captured
        assertEquals(title, capturedTask.title)
        assertEquals(false, capturedTask.isDone)
        assertEquals(0, capturedTask.id)
    }

    @Test
    fun `when addTask called with blank title, should throw IllegalArgumentException`() = runTest {
        // Given
        val blankTitle = "   "

        // When & Then
        try {
            addTaskUseCase(blankTitle)
            fail("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertEquals("Título da tarefa não pode estar vazio", e.message)
        }
    }

    @Test
    fun `when addTask called with empty title, should throw IllegalArgumentException`() = runTest {
        // Given
        val emptyTitle = ""

        // When & Then
        try {
            addTaskUseCase(emptyTitle)
            fail("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertEquals("Título da tarefa não pode estar vazio", e.message)
        }
    }

    @Test
    fun `when addTask called with title with spaces, should trim spaces`() = runTest {
        // Given
        val titleWithSpaces = "  Test Task  "

        // When
        addTaskUseCase(titleWithSpaces)

        // Then
        val taskSlot = slot<Task>()
        coVerify { repository.addTask(capture(taskSlot)) }
        
        val capturedTask = taskSlot.captured
        assertEquals("Test Task", capturedTask.title)
    }
}
