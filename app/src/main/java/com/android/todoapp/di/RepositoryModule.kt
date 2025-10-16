package com.android.todoapp.di

import com.android.todoapp.data.repository.TaskRepository
import com.android.todoapp.domain.repository.TaskRepository as TaskRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepository: TaskRepository
    ): TaskRepositoryInterface
}