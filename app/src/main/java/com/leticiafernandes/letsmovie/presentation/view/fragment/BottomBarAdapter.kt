package com.leticiafernandes.letsmovie.presentation.view.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by leticiafernandes on 25/05/18.
 */
class BottomBarAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    var fragments: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }
}