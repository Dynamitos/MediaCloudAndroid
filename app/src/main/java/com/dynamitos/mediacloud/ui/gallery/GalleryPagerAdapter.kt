package com.dynamitos.mediacloud.ui.gallery


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dynamitos.mediacloud.data.model.UserImage

class GalleryPagerAdapter(fm: FragmentManager, private val images: List<UserImage>) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val image = images[position]
        return ImageDetail.newInstance(image, image.name!!)
    }

    override fun getCount(): Int {
        return images.size
    }
}