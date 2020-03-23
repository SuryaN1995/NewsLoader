package com.example.news.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.databinding.AdapterMainBinding
import com.example.news.network.model.Article

/**
 * Created by SURYA N on 21/3/20.
 */
class MainAdapter(var articles : List<Article>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    class MainViewHolder(private val binding: AdapterMainBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article){
            binding.article = article
            val listener = binding.root.context as RecyclerItemListener?
            binding.root.setOnClickListener {
                listener?.onItemClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = DataBindingUtil.inflate<AdapterMainBinding>(LayoutInflater.from(parent.context),
            R.layout.adapter_main,parent,false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(articles[position])
    }
}