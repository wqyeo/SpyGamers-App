package com.example.spygamers.screens.friendrecommendation

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.spygamers.Screen
import com.example.spygamers.components.appbar.AppBar
import com.example.spygamers.controllers.GamerViewModel
import com.example.spygamers.services.ServiceFactory
import com.example.spygamers.services.recommendations.FriendRequestBody
import com.example.spygamers.services.recommendations.FriendsRecommendationBody
import com.example.spygamers.services.recommendations.RecommendationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FriendRecommendationScreen(
    navController: NavController,
    viewModel: GamerViewModel
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                navigationIconImage = Icons.Default.ArrowBack,
                navigationIconDescription = "Back Button",
                onNavigationIconClick = {
                    navController.popBackStack()
                },
                appBarTitle = "Friend Suggestions"
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen
    ) {
        MainBody(viewModel, navController);
    }
}

@Composable
private fun MainBody(viewModel: GamerViewModel, navController: NavController){
    val serviceFactory = ServiceFactory();
    val sessionToken by viewModel.sessionToken.collectAsState()
    val recommendations = viewModel.recommendedFriends
    val context = LocalContext.current

    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }
    fun removeRecommendation(idToRemove: Int) {
        viewModel.removeFriendRecommendationsByID(idToRemove)
    }

    LaunchedEffect(true) {
        if (sessionToken.isBlank()) {
            // TODO: No session token, warn and redirect to login page...
            Log.w("FriendRecommendationScreen", "Auth token is blank...");
            navController.navigate(Screen.LoginScreen.route)
        }

        // API Call to fetch list of recommendations from backend API
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            try {
                val service = serviceFactory.createService(RecommendationService::class.java)
                val response = service.getRecommendations(FriendsRecommendationBody(sessionToken, chunk_size = 25))

                if (!response.isSuccessful) {
                    Log.e("FriendRecommendationScreen", "Failed to get recommendations :: $response")
                    Toast.makeText(context, "Failed to fetch recommendations!", Toast.LENGTH_LONG).show()
                    return@launch
                }

                // Fetch body response, ensure its not blank...
                val responseBody = response.body()
                if (responseBody == null) {
                    Log.e("FriendRecommendationScreen", "Response body is null")
                    Toast.makeText(context, "Failed to fetch recommendations!", Toast.LENGTH_LONG).show()
                    return@launch
                }

                // Set recommendations in viewModel...
                viewModel.setRecommendedFriends(responseBody.result)
                isLoading = false;
            } catch (e: Exception) {
                Log.e("FriendRecommendationScreen", "Error fetching recommendations :: ", e)
            }
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return;
    }

    // Empty recommendation, display no recommendations text....
    if (recommendations.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = "No friends to recommend...",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        LazyColumn {
            // Loop through each recommendation,
            // creating a row of recommendation...
            items(recommendations.size) { index ->
                val recommendation = recommendations[index]
                FriendRecommendationRow(
                    recommendation,
                    onViewProfile = {
                        viewModel.setViewingUserAccount(it)
                        navController.navigate(Screen.ViewProfileScreen.route)
                    },
                    onRequestSent =  {
                        // Send friend request to target,
                        // if successful, remove from recommended list...
                        viewModel.viewModelScope.launch {
                            val service = serviceFactory.createService(RecommendationService::class.java);
                            val response = service.sendFriendRequest(
                                FriendRequestBody(
                                    sessionToken,
                                    recommendation.id
                                )
                            )

                            if (response.isSuccessful) {
                                removeRecommendation(recommendation.id)
                                Toast.makeText(context, "Friend request sent!", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "Failed to send friend request!", Toast.LENGTH_LONG).show()
                                Log.e(
                                    "Friend Recommendation",
                                    "Failed to get Recommendation: ${response.message()}"
                                )
                            }
                        }
                    }
                )
            }
        }

    }
}