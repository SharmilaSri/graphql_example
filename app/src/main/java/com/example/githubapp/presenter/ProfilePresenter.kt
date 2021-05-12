package com.example.githubapp.presenter

import android.content.Context
import com.apollographql.apollo.api.Response
import com.example.githubapp.contract.Contract
import com.example.githubapp.model.ProfileModel

class ProfilePresenter(var mainView: Contract.View?) : Contract.Presenter {

    private var model=ProfileModel()
    lateinit var  response: Response<ProfileDetailsQuery.Data>
    val repos = mutableListOf<ProfileDetailsQuery.Repositories?>()


    override suspend fun getDataFromNetwork(context: Context) {
        if (mainView != null) {
            mainView!!.showProgress()
            response =model.fetchData(context)
            val newLaunches = response.data?.user?.repositories
            repos.add(newLaunches)
            mainView!!.displayData(response,repos)
            mainView!!.hideProgress()
        }
    }

    override fun onDestroy() {
        mainView = null
    }
}