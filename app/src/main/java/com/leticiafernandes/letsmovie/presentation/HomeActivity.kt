package com.leticiafernandes.letsmovie.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.databinding.ActivityHomeBinding
import com.leticiafernandes.letsmovie.presentation.view.adapter.BottomBarAdapter
import com.leticiafernandes.movie.presentation.MoviesFragment
import dagger.hilt.android.AndroidEntryPoint

private const val PAGE_POPULAR_MOVIES = 0
private const val PAGE_FAVOURITE_MOVIES = 1

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    private lateinit var binding: ActivityHomeBinding
    private val moviesFragment: MoviesFragment = MoviesFragment.newInstance()
    private val adapter = BottomBarAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupViewPager()
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_popular_movies -> binding.viewPagerMovies.currentItem = PAGE_POPULAR_MOVIES
            R.id.item_favourite_movies -> binding.viewPagerMovies.currentItem = PAGE_FAVOURITE_MOVIES
            else -> {
            }
        }
        return true
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    private fun setupViewPager() {
        binding.viewPagerMovies.swipeEnabled = false
        adapter.addFragment(moviesFragment)
        binding.viewPagerMovies.adapter = adapter
        binding.viewPagerMovies.addOnPageChangeListener(this)
    }
}
