package com.leticiafernandes.letsmovie.presentation.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.infrastructure.SharedPreferencesManager
import com.leticiafernandes.letsmovie.presentation.helper.ActivityHelper.Companion.goToActivity
import com.leticiafernandes.letsmovie.presentation.view.adapter.BottomBarAdapter
import com.leticiafernandes.letsmovie.presentation.view.fragment.FavouriteMoviesFragment
import com.leticiafernandes.letsmovie.presentation.view.fragment.PopularMoviesFragment
import kotlinx.android.synthetic.main.activity_main.*


class MoviesActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    private var popularMoviesFragment: PopularMoviesFragment? = null
    private var favouriteMoviesFragment: FavouriteMoviesFragment? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var sharedPreferencesManager: SharedPreferencesManager? = null

    companion object {
        var PAGE_POPULAR_MOVIES = 0
        var PAGE_FAVOURITE_MOVIES = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager()
        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferencesManager = SharedPreferencesManager(this)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_logout -> {
                firebaseAuth?.currentUser?.uid
                firebaseAuth?.signOut()
                sharedPreferencesManager?.saveUserEmail("")
                goToActivity(this, LoginActivity::class.java)
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
            PAGE_FAVOURITE_MOVIES -> favouriteMoviesFragment?.loadFavouriteList() //TODO remove
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    private fun setupViewPager() {
        viewPagerMovies.swipeEnabled = false
        val adapter = BottomBarAdapter(supportFragmentManager)
        popularMoviesFragment = PopularMoviesFragment()
        favouriteMoviesFragment = FavouriteMoviesFragment()
        adapter.addFragment(popularMoviesFragment!!)
        adapter.addFragment(favouriteMoviesFragment!!)
        viewPagerMovies.adapter = adapter
        viewPagerMovies.addOnPageChangeListener(this)
    }
}
