package com.example.githubapp.model

import android.content.Context
import javax.inject.Inject

class ProfilePresenter(var mainView: Contract.View?,
                                            val model: Contract.Model) : Contract.Presenter{

    override suspend fun getDataFromNetwork(context: Context) {
        if (mainView != null) {
            mainView!!.showProgress()
            mainView!!.displayData(model.fetchData(context))
            mainView!!.hideProgress()
        }
    }


    override fun onDestroy() {
        mainView = null
    }
}