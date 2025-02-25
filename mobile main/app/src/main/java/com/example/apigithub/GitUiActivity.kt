package com.example.apigithub

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.calyr.network.GithubRemoteDataSource
import com.calyr.network.RetrofitBuilder
import com.example.apigithub.ui.theme.APIGitHubTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GitUiActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = applicationContext
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APIGitHubTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GitUi( modifier = Modifier.padding(innerPadding), context = context)
                }
            }
        }
    }
}


@Composable
fun GitUi(modifier: Modifier = Modifier, context: Context) {
    val dataSource: GithubRemoteDataSource = GithubRemoteDataSource(
        RetrofitBuilder
    )
    var urlImage by remember { mutableStateOf("")}
    var userId by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var userCompany by remember { mutableStateOf("") }
    var userBio by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.github_ui_title)
        )
        TextField(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            value = userId, onValueChange = {
                userId = it

            })
        Button(onClick = {
            //val show = Toast.makeText(context, userId, Toast.LENGTH_LONG).show()
            CoroutineScope(Dispatchers.IO).launch {
                val response = dataSource.getAvatarInfo(userId)
                urlImage = response.url ?: ""
                userName = response.name ?: "No tiene información de nombre"
                userCompany = response.company ?: "No tiene una compañía"
                userBio = response.bio ?: "No cuenta con biografía"
            }
        }) {
            Text(text = stringResource(id = R.string.github_ui_button))
        }
        AsyncImage(
            model = urlImage,
            contentDescription = null
        )

        Column(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nombre: $userName",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Compañía: $userCompany",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Biografía: $userBio",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

    }
}
