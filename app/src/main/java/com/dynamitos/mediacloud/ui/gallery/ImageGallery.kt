package com.dynamitos.mediacloud.ui.gallery

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dynamitos.mediacloud.R
import com.dynamitos.mediacloud.data.model.ImageClickListener
import com.dynamitos.mediacloud.data.model.ImageGalleryViewModel
import com.dynamitos.mediacloud.data.Util
import com.dynamitos.mediacloud.data.model.UserImage

class ImageGallery : Fragment(), ImageClickListener {

    companion object {
        val TAG = "RecyclerViewFragment"
        fun newInstance() = ImageGallery()
    }

    private lateinit var viewModel: ImageGalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val galleryAdapter = GalleryAdapter(Util.getImageData(), this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = galleryAdapter
    }

    override fun onImageClicked(position: Int, image: UserImage, view: ImageView) {
        val galleryViewPagerFragment = GalleryPager.newInstance(position, Util.getImageData())

        parentFragmentManager.beginTransaction()
            .addSharedElement(view, ViewCompat.getTransitionName(view)!!)
            .addToBackStack(TAG)
            .replace(R.id.content, galleryViewPagerFragment)
            .commit()
    }
}