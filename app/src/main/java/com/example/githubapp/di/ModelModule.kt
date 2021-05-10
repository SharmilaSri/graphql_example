package com.example.githubapp.di

import com.example.githubapp.model.Contract
import com.example.githubapp.model.ProfileModel
import dagger.Binds
import dagger.Module


@Module
abstract class ModelModule {
    @Binds
    abstract fun provideModel(model: ProfileModel): Contract.Model
}