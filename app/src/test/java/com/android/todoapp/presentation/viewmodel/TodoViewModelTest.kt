package com.android.todoapp.presentation.viewmodel

import com.android.todoapp.domain.model.Task
import com.android.todoapp.domain.usecase.AddTaskUseCase
import com.android.todoapp.domain.usecase.DeleteTaskUseCase
import com.android.todoapp.domain.usecase.GetTasksUseCase
import com.android.todoapp.domain.usecase.ToggleTaskUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para TodoViewModel.
 * Testa o comportamento do ViewModel com diferentes estados.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TodoViewModelTest {

    private lateinit var getTasksUseCase: GetTasksUseCase
    private lateinit var addTaskUseCase: AddTaskUseCase
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase
    private lateinit var toggleTaskUseCase: ToggleTaskUseCase
    private lateinit var viewModel: TodoViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        
        getTasksUseCase = mockk(relaxed = true)
        addTaskUseCase = mockk(relaxed = true)
        deleteTaskUseCase = mockk(relaxed = true)
        toggleTaskUseCase = mockk(relaxed = true)
        
        viewModel = TodoViewModel(
            getTasksUseCase,
            addTaskUseCase,
            deleteTaskUseCase,
            toggleTaskUseCase
        )
    }

    @Test
    fun `when initialized, should load tasks`() = runTest {
        // Given
        val expectedTasks = listOf(
            Task(1, "Task 1", false),
            Task(2, "Task 2", true)
        )
        coEvery { getTasksUseCase() } returns flowOf(expectedTasks)

        // When
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(expectedTasks, uiState.tasks)
        assertFalse(uiState.isLoading)
        assertNull(uiState.errorMessage)
    }

    @Test
    fun `when addTask event with valid title, should call addTaskUseCase`() = runTest {
        // Given
        val title = "New Task"
        coEvery { addTaskUseCase(title) } returns Unit

        // When
        viewModel.onEvent(TodoUiEvent.AddTask(title))
        advanceUntilIdle()

        // Then
        coEvery { addTaskUseCase(title) }
    }

    @Test
    fun `when addTask event with blank title, should show error`() = runTest {
        // Given
        val blankTitle = "   "
        coEvery { addTaskUseCase(blankTitle) } throws IllegalArgumentException("Título da tarefa não pode estar vazio")

        // When
        viewModel.onEvent(TodoUiEvent.AddTask(blankTitle))
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertNotNull(uiState.errorMessage)
        assertTrue(uiState.errorMessage!!.contains("Título da tarefa não pode estar vazio"))
    }

    @Test
    fun `when toggleTask event, should call toggleTaskUseCase`() = runTest {
        // Given
        val task = Task(1, "Task 1", false)
        coEvery { toggleTaskUseCase(task) } returns Unit

        // When
        viewModel.onEvent(TodoUiEvent.ToggleTask(task))
        advanceUntilIdle()

        // Then
        coEvery { toggleTaskUseCase(task) }
    }

    @Test
    fun `when deleteTask event, should call deleteTaskUseCase`() = runTest {
        // Given
        val task = Task(1, "Task 1", false)
        coEvery { deleteTaskUseCase(task) } returns Unit

        // When
        viewModel.onEvent(TodoUiEvent.DeleteTask(task))
        advanceUntilIdle()

        // Then
        coEvery { deleteTaskUseCase(task) }
    }

    @Test
    fun `when clearError event, should clear error message`() = runTest {
        // Given
        val uiState = viewModel.uiState.value
        assertNull(uiState.errorMessage)

        // When
        viewModel.onEvent(TodoUiEvent.ClearError)
        advanceUntilIdle()

        // Then
        val newUiState = viewModel.uiState.value
        assertNull(newUiState.errorMessage)
    }
}
