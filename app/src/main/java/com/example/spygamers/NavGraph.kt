package com.example.spygamers

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spygamers.controllers.GamerViewModel
import com.example.spygamers.screens.InitialScreen
import com.example.spygamers.screens.LoginScreen
import com.example.spygamers.screens.creategroup.CreateGroupScreen
import com.example.spygamers.screens.directmessage.DirectMessageScreen
import com.example.spygamers.screens.friendlist.FriendListScreen
import com.example.spygamers.screens.friendrecommendation.FriendRecommendationScreen
import com.example.spygamers.screens.grouplist.GroupListScreen
import com.example.spygamers.screens.groupmessage.GroupMessageScreen
import com.example.spygamers.screens.home.HomeScreen
import com.example.spygamers.screens.recommendgroups.RecommendGroupsScreen
import com.example.spygamers.screens.register.RegisterScreen
import com.example.spygamers.screens.viewprofile.ViewProfileScreen

sealed class Screen(val route: String) {
    /**
     * Used to show loading, to determine if the user have a auth_token or not,
     * before deciding to move to login or home screen...
     */
    object InitialScreen: Screen(route = "Initial_Screen")
    object LoginScreen : Screen(route = "Login_Screen")
    object RegisterScreen : Screen(route = "Register_Screen")
    object FriendListScreen : Screen(route = "FriendList_Screen")
    object GroupListScreen : Screen(route = "GroupList_Screen")
    object HomeScreen : Screen(route = "Home_Screen")
    object FriendRecommendationScreen : Screen(route = "FriendRecommendation_Screen")

    object GroupRecommendationScreen : Screen(route = "GroupRecommendation_Screen")
    object ViewProfileScreen : Screen(route = "ViewProfile_Screen")
    object CreateGroupScreen : Screen(route = "CreateGroup_Screen")

    object DirectMessageScreen: Screen(route = "DirectMessage_Screen")
    object GroupMessageScreen: Screen(route = "GroupMessaage_Screen")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: GamerViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.InitialScreen.route
    ) {

        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, viewModel)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController, viewModel)
        }

        composable(route = Screen.ViewProfileScreen.route) {
            ViewProfileScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController, viewModel)
        }
        composable(route = Screen.FriendListScreen.route) {
            FriendListScreen(navController = navController, viewModel)
        }
        composable(route = Screen.FriendRecommendationScreen.route) {
            FriendRecommendationScreen(navController = navController, viewModel)
        }

        composable(route = Screen.InitialScreen.route) {
            InitialScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.DirectMessageScreen.route) {
            DirectMessageScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.CreateGroupScreen.route) {
            CreateGroupScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.GroupMessageScreen.route) {
            GroupMessageScreen(navController = navController, viewModel = viewModel)
        }
        
        composable(route = Screen.GroupListScreen.route) {
            GroupListScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.GroupRecommendationScreen.route) {
            RecommendGroupsScreen(navController = navController, viewModel = viewModel)
        }
    }
}