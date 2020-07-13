package com.leticiafernandes.letsmovie.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.presentation.view.adapter.BottomBarAdapter
import com.leticiafernandes.movie.presentation.MoviesFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

private const val PAGE_POPULAR_MOVIES = 0
private const val PAGE_FAVOURITE_MOVIES = 1

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    private val moviesFragment: MoviesFragment = MoviesFragment.newInstance()
    //private var favouriteMoviesFragment: FavouriteMoviesFragment? = null
    //lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setContentView(R.layout.activity_home)
        setupViewPager()
        bottomNavigation.setOnNavigationItemSelectedListener(this)
        supportActionBar?.title = getString(R.string.title_movies)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                //homeViewModel.logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_popular_movies -> viewPagerMovies.currentItem = PAGE_POPULAR_MOVIES
            R.id.item_favourite_movies -> viewPagerMovies.currentItem = PAGE_FAVOURITE_MOVIES
            else -> {
            }
        }
        return true
    }

    override fun onPageSelected(position: Int) {
        when (position) {
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
        adapter.addFragment(moviesFragment)
        viewPagerMovies.adapter = adapter
        viewPagerMovies.addOnPageChangeListener(this)
    }
}
