package com.exam.allmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.exam.allmovies.ui.detail.AMDetail
import com.exam.allmovies.ui.home.AMHome
import com.exam.allmovies.ui.navigation.Detail
import com.exam.allmovies.ui.navigation.Home
import com.exam.allmovies.ui.navigation.WishList
import com.exam.allmovies.ui.theme.AllMoviesTheme
import com.exam.allmovies.ui.wishlist.AMWishList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllMoviesTheme {
                val navigationController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navigationController,
                        startDestination = Home,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Home> {
                            AMHome(navController = navigationController) { id, inWishList ->
                                navigationController.navigate(
                                    Detail(id = id, inWishList = inWishList)
                                )
                            }
                        }
                        composable<Detail> { backStackEntry ->
                            val movie = backStackEntry.toRoute<Detail>()
                            AMDetail(
                                id = movie.id, navController = navigationController,
                                onWishList = movie.inWishList,
                                fromWishList = movie.fromWishList
                            )
                        }
                        composable<WishList> {
                            AMWishList(
                                navController = navigationController,
                                navigateDetail = { id, inWishList ->
                                    navigationController.navigate(
                                        Detail(id = id, inWishList = inWishList,fromWishList = true)
                                    )
                                })
                        }
                    }
                }
            }
        }
    }
}
