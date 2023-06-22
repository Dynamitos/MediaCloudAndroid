package com.dynamitos.mediacloud.ui.audio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dynamitos.mediacloud.R
import com.dynamitos.mediacloud.data.LoginRepository
import com.dynamitos.mediacloud.data.model.AudioClickListener
import com.dynamitos.mediacloud.data.model.AudioGalleryViewModel
import com.dynamitos.mediacloud.data.model.ImageClickListener
import com.dynamitos.mediacloud.data.model.UserImage
import com.dynamitos.mediacloud.network.APIClient
import com.dynamitos.mediacloud.ui.LockableViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch


class AudioWrapper : Fragment(), AudioClickListener {
    private lateinit var viewModel: AudioGalleryViewModel
    private var images: List<UserImage> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*parentFragmentManager
            .beginTransaction()
            .add(R.id.content, ImageGallery.newInstance())
            .commit()*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_wrapper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listener = this
        lifecycleScope.launch {
            val user = LoginRepository.getInstance().user!!
            images = APIClient.getInstance().apiService.getImages(user.displayName, user.authToken)

            val galleryAdapter = AudioAdapter(images, listener)
            val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_audio)
            val gridLayoutManager = GridLayoutManager(context, 2)
            recyclerView.layoutManager = gridLayoutManager
            recyclerView.adapter = galleryAdapter
        }
    }

    override fun onAudioClicked(position: Int, image: UserImage, view: ImageView) {
        val viewPager = view.rootView.findViewById<LockableViewPager>(R.id.view_pager_main)
        val tabs = view.rootView.findViewById<TabLayout>(R.id.tabs)

        lifecycleScope.launch {
            val audioViewPagerFragment = AudioPager.newInstance(position, images)

            tabs?.visibility = View.GONE
            parentFragmentManager.beginTransaction()
                .addSharedElement(view, ViewCompat.getTransitionName(view)!!)
                .addToBackStack(TAG)
                .replace(R.id.content_audio, audioViewPagerFragment)
                .commit()
            viewPager.isPagingEnabled = false
        }
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true ) {
                override fun handleOnBackPressed() {
                    tabs?.visibility = View.VISIBLE
                    parentFragmentManager.popBackStack()
                    viewPager.isPagingEnabled = true
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    companion object {
        const val TAG = "RecyclerAudioViewFragment"
        fun newInstance() = AudioWrapper()
    }
}