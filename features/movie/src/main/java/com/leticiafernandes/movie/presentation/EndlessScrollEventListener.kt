package com.leticiafernandes.movie.presentation

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Class based in this tutorial https://baraabytes.com/android-endless-scrolling-with-recyclerview/
 */

abstract class EndlessScrollEventListener(
        private val linearLayoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    /** is number of items that we could have after our
     * current scroll position before we start loading
     * more items  */
    private val visibleThreshold = 5

    /** to keep track of the page that we would like to
     * retrieve from a server our database
     */
    private var currentPage = 0

    /** total number of items that we retrieve lastly */
    private var previousTotalItemCount = 0

    /** the initial index of the page that'll start from  */
    private val startingPageIndex = 0

    /******* variables we could get from linearLayoutManager *******/

    /******* variables we could get from linearLayoutManager  */
    /** the total number of items that we currently have on our recyclerview and we
     * get it from linearLayoutManager  */
    private var totalItemCount = 0

    /** the position of last visible item in our view currently
     * get it from linearLayoutManager  */
    private var lastVisibleItemPosition = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        totalItemCount = linearLayoutManager.itemCount
        lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
        }

        if (totalItemCount > previousTotalItemCount) {
            previousTotalItemCount = totalItemCount
        }

        if (isLoadingData().not() && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, recyclerView)
        }
    }

    fun reset() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
    }

    abstract fun onLoadMore(pageNumber: Int, recyclerView: RecyclerView)

    abstract fun isLoadingData(): Boolean
}
