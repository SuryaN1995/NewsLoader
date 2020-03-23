package com.example.news.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.databinding.ActivityMainBinding
import com.example.news.network.ApiHelper
import com.example.news.network.model.Article
import com.example.news.utils.AppConstants
import com.example.news.utils.SpaceItemDecor
import com.example.news.utils.ViewUtils
import com.example.news.utils.viewModelFactory
import com.example.news.viewModel.MainViewModel

class MainActivity : AppCompatActivity(), RecyclerItemListener {

    private var binding : ActivityMainBinding ?= null
    private var mAdapter : MainAdapter ?= null
    private var viewModel : MainViewModel ?= null
    private var article: Article ?= null
    private var alertDialog : AlertDialog ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory {
            MainViewModel(ApiHelper())
        })[MainViewModel::class.java]
        binding?.ivBack?.setOnClickListener {
            onBackPressed()
        }
        initView()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.fragments.isNotEmpty()){
            supportFragmentManager.popBackStackImmediate()
            setUpToolBar(true)
        }else
            super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        setUpToolBar(true)
    }

    private fun setUpToolBar(isActivity : Boolean) {
        binding?.ivBack?.visibility = if(isActivity) View.GONE else View.VISIBLE
        binding?.rvFeed?.visibility = if(isActivity) View.VISIBLE else View.INVISIBLE
        binding?.fragment?.visibility = if(isActivity) View.INVISIBLE else View.VISIBLE
        binding?.title?.text = if(isActivity) getString(R.string.app_name) else article?.source?.name
    }

    private fun initView() {
        viewModel?.articles?.observe(this, Observer {
            it?.let {
                mAdapter?.articles = it
                mAdapter?.notifyDataSetChanged()
            }
        })
        viewModel?.isLoading?.observe(this, Observer {
            it?.let {
                if(it){
                    alertDialog = ViewUtils.showProgress(this,getString(R.string.loading))
                }else
                    ViewUtils.dismissProgress(alertDialog)
            }
        })
        binding?.title?.text = getString(R.string.app_name)
        mAdapter = MainAdapter(arrayListOf())
        binding?.rvFeed?.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(SpaceItemDecor())
        }
        viewModel?.fetchNewsFeed()
    }

    override fun onItemClicked(position: Int){
        launchWebFragment(viewModel?.articles?.value?.get(position))
    }

    private fun launchWebFragment(article: Article?) {
        this.article = article
        val fragment = MainWebFragment()
        val bundle = Bundle()
        bundle.putString(AppConstants.WEB_KEY,article?.url)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.fragment,fragment).addToBackStack(fragment.tag).commitAllowingStateLoss()
        setUpToolBar(false)
    }
}
