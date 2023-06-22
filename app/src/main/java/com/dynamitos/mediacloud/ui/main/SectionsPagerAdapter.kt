package com.dynamitos.mediacloud.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dynamitos.mediacloud.ui.gallery.GalleryWrapper
import com.dynamitos.mediacloud.data.Util
import com.dynamitos.mediacloud.ui.PlaceHolder
import com.dynamitos.mediacloud.ui.audio.AudioWrapper

//import com.dynamitos.mediacloud.data.R

private val TAB_TITLES = arrayOf(
    "Images",
    "Audio"
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment.
        if(position == 0){
            return GalleryWrapper.newInstance()
        } else if (position == 1) {
            return AudioWrapper.newInstance()
        }
        return PlaceHolder.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 2
    }
}