package com.leticiafernandes.letsmovie.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.presentation.view.adapter.BottomBarAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    //private var moviesFragment: MoviesFragment? = null
    //private var favouriteMoviesFragment: FavouriteMoviesFragment? = null
    //lateinit var homeViewModel: HomeViewModel

    companion object {
        var PAGE_POPULAR_MOVIES = 0
        var PAGE_FAVOURITE_MOVIES = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupViewPager()
        bottomNavigation.setOnNavigationItemSelectedListener(this)
        val navController = findNavController(R.id.navigationHostFragment)
        bottomNavigation.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_logout -> {
                //homeViewModel.logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_popular_movies -> viewPagerMovies.currentItem = PAGE_POPULAR_MOVIES
            R.id.item_favourite_movies -> viewPagerMovies.currentItem = PAGE_FAVOURITE_MOVIES
            else -> {}
        }
        return true
    }

    override fun onPageSelected(position: Int) {
        when(position) {
            //PAGE_FAVOURITE_MOVIES -> favouriteMoviesFragment?.loadFavouriteList() //TODO remove
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    private fun setupViewPager() {
        viewPagerMovies.swipeEnabled = false
        val adapter = BottomBarAdapter(supportFragmentManager)
        //moviesFragment = MoviesFragment()
        //favouriteMoviesFragment = FavouriteMoviesFragment()
        //adapter.addFragment(moviesFragment!!)
        //adapter.addFragment(favouriteMoviesFragment!!)
        viewPagerMovies.adapter = adapter
        viewPagerMovies.addOnPageChangeListener(this)
    }
}
