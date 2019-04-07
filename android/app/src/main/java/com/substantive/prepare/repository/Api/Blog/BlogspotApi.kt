package com.substantive.prepare.repository.Api.Blog


import com.substantive.prepare.repository.Api.Blog.DataObjects.BlogPosts
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://www.googleapis.com/
interface BlogspotApi {

    @GET("/blogger/v3/{id}/posts")
    fun getBlogPosts(@Path("id") id : Int, @Query("apikey") key : String) : Observable<BlogPosts>

}