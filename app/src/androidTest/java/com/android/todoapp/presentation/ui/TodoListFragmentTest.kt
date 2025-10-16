package com.android.todoapp.presentation.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.todoapp.presentation.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Testes de UI para TodoListFragment.
 * Testa o comportamento da interface do usuário.
 */
@RunWith(AndroidJUnit4::class)
class TodoListFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun `when app starts, should show empty list`() {
        // Verifica se a lista está vazia inicialmente
        onView(withId(com.android.todoapp.R.id.recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `when fab clicked, should navigate to form`() {
        // Clica no FAB para adicionar tarefa
        onView(withId(com.android.todoapp.R.id.fabAddTask))
            .perform(click())

        // Verifica se navegou para o formulário
        onView(withId(com.android.todoapp.R.id.etTaskTitle))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `when add task, should show in list`() {
        // Navega para o formulário
        onView(withId(com.android.todoapp.R.id.fabAddTask))
            .perform(click())

        // Adiciona uma tarefa
        onView(withId(com.android.todoapp.R.id.etTaskTitle))
            .perform(typeText("Test Task"))

        onView(withId(com.android.todoapp.R.id.btnSave))
            .perform(click())

        // Verifica se a tarefa aparece na lista
        onView(withText("Test Task"))
            .check(matches(isDisplayed()))
    }
}
