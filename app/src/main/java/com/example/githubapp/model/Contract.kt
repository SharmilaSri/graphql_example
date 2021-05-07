package com.example.githubapp.model

import android.app.Activity
import android.content.Context
import com.apollographql.apollo.api.Response

interface Contract {

    interface View {
        fun showProgress()

        fun hideProgress()

        suspend fun getData(context: Context)

        fun displayData(response: Response<ProfileDetailsQuery.Data>)
    }

    interface Model {

        interface OnFinishedListener {

            fun onFinished(context: Context)
        }

        suspend fun fetchData(onFinishedListener: OnFinishedListener?,context: Context) : Response<ProfileDetailsQuery.Data>
    }

    interface Presenter {
        suspend fun getDataFromNetwork(context: Context)

        fun onDestroy()
    }
}