package com.leticiafernandes.letsmovie.presentation.view.activity

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.databinding.ActivityMainBinding
import com.leticiafernandes.letsmovie.presentation.presenter.IMainPresenter
import com.leticiafernandes.letsmovie.presentation.presenter.MainPresenter
import com.leticiafernandes.letsmovie.presentation.view.adapter.BottomBarAdapter
import com.leticiafernandes.letsmovie.presentation.view.fragment.FavouriteMoviesFragment
import com.leticiafernandes.letsmovie.presentation.view.fragment.PopularMoviesFragment
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IMainMvpView


class MoviesActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener, IMainMvpView {

    private lateinit var binding: ActivityMainBinding
    private var popularMoviesFragment: PopularMoviesFragment? = null
    private var favouriteMoviesFragment: FavouriteMoviesFragment? = null
    lateinit var presenter: IMainPresenter

    companion object {
        var PAGE_POPULAR_MOVIES = 0
        var PAGE_FAVOURITE_MOVIES = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPager()
        presenter = MainPresenter(this)
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_logout -> {
                presenter.logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_popular_movies -> binding.viewPagerMovies.currentItem = PAGE_POPULAR_MOVIES
            R.id.item_favourite_movies -> binding.viewPagerMovies.currentItem = PAGE_FAVOURITE_MOVIES
            else -> {}
        }
        return true
    }

    override fun onPageSelected(position: Int) {
        when(position) {
            PAGE_FAVOURITE_MOVIES -> favouriteMoviesFragment?.loadFavouriteList()
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun getContext(): Context {
        return this
    }

    private fun setupViewPager() {
        binding.viewPagerMovies.swipeEnabled = false
        val adapter = BottomBarAdapter(supportFragmentManager)
        popularMoviesFragment = PopularMoviesFragment()
        favouriteMoviesFragment = FavouriteMoviesFragment()
        adapter.addFragment(popularMoviesFragment!!)
        adapter.addFragment(favouriteMoviesFragment!!)
        binding.viewPagerMovies.adapter = adapter
        binding.viewPagerMovies.addOnPageChangeListener(this)
    }
}
