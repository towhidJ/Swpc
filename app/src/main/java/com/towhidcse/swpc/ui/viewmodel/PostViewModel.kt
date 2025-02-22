package com.towhidcse.swpc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towhidcse.swpc.data.model.Post
import com.towhidcse.swpc.data.network.RetrofitClient
import com.towhidcse.swpc.data.repository.PostRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val repository = PostRepository(RetrofitClient.apiService)

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _newPostResponse = MutableStateFlow<Post?>(null)
    val newPostResponse: StateFlow<Post?> = _newPostResponse.asStateFlow()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            repository.getPosts().collect { _posts.value = it }
        }
    }

    fun createPost(post: Post) {
        viewModelScope.launch {
            repository.createPost(post).collect { _newPostResponse.value = it }
        }
    }
}