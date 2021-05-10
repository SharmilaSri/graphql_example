package com.example.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.coroutines.toFlow
import com.example.githubapp.di.AppComponent
import com.example.githubapp.di.ProfileSubComponent
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var profileComponent: ProfileSubComponent

    override fun onCreate(savedInstanceState: Bundle?) {

        profileComponent=(application as GitApplication).appComponent.profileComponent().create()
        profileComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
