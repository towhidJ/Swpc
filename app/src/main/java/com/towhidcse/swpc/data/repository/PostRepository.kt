package com.towhidcse.swpc.data.repository


import com.towhidcse.swpc.data.model.Post
import com.towhidcse.swpc.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostRepository(private val apiService: ApiService) {
    fun getPosts(): Flow<List<Post>> = flow {
        emit(apiService.getPosts())
    }

    fun createPost(post: Post): Flow<Post> = flow {
        emit(apiService.createPost(post))
    }
}