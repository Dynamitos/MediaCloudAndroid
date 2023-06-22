package com.dynamitos.mediacloud.ui.gallery

import android.os.Bundle
import android.transition.TransitionInflater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.dynamitos.mediacloud.R
import com.dynamitos.mediacloud.data.model.UserImage


class GalleryPager : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentItem = arguments?.getInt(INITIAL_POS)
        val images = arguments?.getParcelableArrayList<UserImage>(IMAGES)
        val galleryPagerAdapter = GalleryPagerAdapter(childFragmentManager, images!!.toList())

        val viewPager = view.findViewById<ViewPager>(R.id.view_pager_main)
        viewPager.adapter = galleryPagerAdapter
        viewPager.currentItem = currentItem ?: 0

        view.findViewById<Button>(R.id.close_button).setOnClickListener {
            close()
        }
    }

    fun close() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }
    companion object {
        fun newInstance(curr: Int, images: List<UserImage>): GalleryPager {
            val fragment = GalleryPager()
            val args = Bundle()
            args.putInt(INITIAL_POS, curr)
            args.putParcelableArrayList(IMAGES, ArrayList(images))
            fragment.arguments = args
            return fragment
        }
        private val INITIAL_POS = "initial_pos"
        private val IMAGES = "images"
    }
}