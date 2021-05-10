package com.example.githubapp.di

import com.example.githubapp.MainActivity
import com.example.githubapp.views.ProfileFragment
import dagger.Subcomponent

@Subcomponent
interface ProfileSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileSubComponent
    }

    // Types that can be retrieved from the graph
    fun inject (activity: MainActivity)
    fun inject (fragment: ProfileFragment)
}