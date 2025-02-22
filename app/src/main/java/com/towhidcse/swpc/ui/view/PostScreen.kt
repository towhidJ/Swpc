package com.towhidcse.swpc.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.towhidcse.swpc.data.model.Post
import com.towhidcse.swpc.ui.viewmodel.PostViewModel
import androidx.compose.foundation.text.BasicTextField

import androidx.compose.ui.Alignment

import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun PostScreen(viewModel: PostViewModel = viewModel()) {
    val posts by viewModel.posts.collectAsState()
    val newPostResponse by viewModel.newPostResponse.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        PostInputForm { title, body ->
            viewModel.createPost(Post(0, title, body))
        }

        newPostResponse?.let { post ->
            Text("New Post Created: ${post.title}", style = MaterialTheme.typography.bodyLarge)
        }

        LazyColumn {
            items(posts) { post ->
                PostItem(post)
            }
        }
    }
}

@Composable
fun PostInputForm(onPostClick: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        BasicTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        BasicTextField(
            value = body,
            onValueChange = { body = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        Button(
            onClick = { onPostClick(title, body) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Post")
        }
    }
}

@Composable
fun PostItem(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.body, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPostItem() {
    PostItem(Post(1, "Sample Title", "This is a sample post body."))
}