package com.example.githubapp.di

import com.example.githubapp.model.Contract
import com.example.githubapp.model.ProfileModel
import dagger.Binds
import dagger.Module


@Module
abstract class PresenterModule {
    @Binds
    abstract fun providePresenter(presenter: PresenterModule): Contract.Presenter
}