package com.emergencyzone.emergencyzone.repository.Api.DataObjects

import java.util.*

class BlogPosts(val items : List<BlogPost>)
class BlogPost(val published : Date, val url : String, val selflink : String, val title : String, val content : String)
