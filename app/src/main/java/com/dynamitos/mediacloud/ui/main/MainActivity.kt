package com.dynamitos.mediacloud.ui.main

import android.R
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dynamitos.mediacloud.databinding.ActivityMainBinding
import com.dynamitos.mediacloud.ui.gallery.GalleryWrapper
import com.dynamitos.mediacloud.ui.main.SectionsPagerAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*Glide.with(binding.root.context)
            .load("https://cdn.pixabay.com/photo/2014/06/03/19/38/road-sign-361514_960_720.png")
            //.thumbnail(0.5f)
            //.diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.galleryImage)*/

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)


    }
}