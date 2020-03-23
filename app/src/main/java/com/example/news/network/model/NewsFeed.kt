package com.example.news.network.model

/**
 * Created by SURYA N on 21/3/20.
 */
class NewsFeed {

    var articles : ArrayList<Article> ?= null

}

class Article {

    var source : Source ?= null
    var author : String ?= null
    var title : String ?= null
    var description : String ?= null
    var url : String ?= null
    var urlToImage : String ?= null
    var publishedAt : String ?= null
    var content : String ?= null

}

class Source {
    var id : String ?= null
    var name : String ?= null
}