package com.example.news.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.network.ApiHelper
import com.example.news.network.ApiService
import com.example.news.network.model.Article
import com.example.news.network.model.NewsFeed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by SURYA N on 21/3/20.
 */
class MainViewModel(private val apiHelper: ApiHelper) : ViewModel() {

    val articles = MutableLiveData<List<Article>>().apply {
        postValue(arrayListOf())
    }

    val isLoading = MutableLiveData<Boolean>().apply {
        postValue(null)
    }

    fun fetchNewsFeed() {
        isLoading.value = true
        apiHelper.fetchRecord(object : ApiService.Callback {
            override fun onSuccess(result: NewsFeed) {
                viewModelScope.launch(Dispatchers.Main) {
                    articles.value = result.articles
                    isLoading.value = false
                }
            }

            override fun onFailure(error: Throwable) {
                viewModelScope.launch(Dispatchers.Main){
                    isLoading.value = false
                }
            }

        })
    }
}