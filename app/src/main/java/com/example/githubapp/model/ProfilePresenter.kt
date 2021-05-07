package com.example.githubapp.model

import android.content.Context

class ProfilePresenter (private var mainView: Contract.View?,
                        private val model: Contract.Model) : Contract.Presenter,
Contract.Model.OnFinishedListener {

    override fun onFinished(context: Context) {
        if (mainView != null) {
            mainView!!.hideProgress()
        }
    }

    override suspend fun getDataFromNetwork(context: Context) {
        if (mainView != null) {
            mainView!!.showProgress()
            mainView!!.displayData(model.fetchData(this,context))
            mainView!!.hideProgress()
        }
    }


    override fun onDestroy() {
        mainView = null
    }
}