package com.example.news.network

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class ApiHelper {

    private var disposable : Disposable ?= null

    init {
        disposable = CompositeDisposable()
    }

    fun fetchRecord(callback: ApiService.Callback){
        disposable?.dispose()
        disposable = ApiManager.fetchFeeds()?.subscribe({
            callback.onSuccess(it)
        },{
            callback.onFailure(it)
        })
    }

}