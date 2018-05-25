package com.leticiafernandes.letsmovie.presentation.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.presentation.view.fragment.FavouriteMoviesFragment
import com.leticiafernandes.letsmovie.presentation.view.fragment.PopularMoviesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MoviesActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var popularMoviesFragment: PopularMoviesFragment? = null
    private var favouriteMoviesFragment: FavouriteMoviesFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        popularMoviesFragment = PopularMoviesFragment()
        favouriteMoviesFragment = FavouriteMoviesFragment()

        bottomNavigation.setOnNavigationItemSelectedListener(this)
        showFragment(popularMoviesFragment!!)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_popular_movies -> showFragment(popularMoviesFragment!!)
            R.id.item_favourite_movies -> showFragment(favouriteMoviesFragment!!)
            else -> {}
        }
        return true
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.moviesFrame, fragment)
                .commit()
    }
}
