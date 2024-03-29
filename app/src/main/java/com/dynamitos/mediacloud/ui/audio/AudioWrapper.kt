package com.dynamitos.mediacloud.ui.audio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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
import com.dynamitos.mediacloud.data.model.AudioPlayer
import com.dynamitos.mediacloud.data.model.UserAudio
import com.dynamitos.mediacloud.network.APIClient
import com.dynamitos.mediacloud.ui.LockableViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch


class AudioWrapper : Fragment(), AudioClickListener {
    private lateinit var viewModel: AudioGalleryViewModel
    private var audios: List<UserAudio> = emptyList()
    private var currTrack = -1

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

    fun getAudio(username: String, token: String) : List<UserAudio> {
        return listOf(
            UserAudio.createFromParams("Then We Are Decided", "img/Dynamitos/Eden.jpg", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", "Bob Bingham", "Jesus Christ Superstar", 123),
            UserAudio.createFromParams("The Man From the Daily Mail", "img/Dynamitos/Mobius.jpg", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", "The Rebel Hearts", "Unforgotten Legends", 456),
            UserAudio.createFromParams("Wellerman", "img/Dynamitos/Kevin.jpg", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", "Jonathan Young", "Young's Old Covers (2019-2021)", 789),
            UserAudio.createFromParams("Then We Are Decided", "img/Dynamitos/Eden.jpg", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", "Bob Bingham", "Jesus Christ Superstar", 123),
            UserAudio.createFromParams("The Man From the Daily Mail", "img/Dynamitos/Mobius.jpg", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", "The Rebel Hearts", "Unforgotten Legends", 456),
            UserAudio.createFromParams("Wellerman", "img/Dynamitos/Kevin.jpg", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", "Jonathan Young", "Young's Old Covers (2019-2021)", 789),
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listener = this
        lifecycleScope.launch {
            val user = LoginRepository.getInstance().user!!
            audios = APIClient.getInstance().apiService.getMusic(user.displayName, user.authToken)

            val galleryAdapter = AudioAdapter(audios, listener)
            val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_audio)
            val gridLayoutManager = GridLayoutManager(context, 2)
            recyclerView.layoutManager = gridLayoutManager
            recyclerView.adapter = galleryAdapter

            currTrack = 0
            //AudioPlayer.getInstance().play(audios[0].songURL!!, audios[0].name!!, audios[0].artistName, audios[0].albumName, requireContext())
            //AudioPlayer.getInstance().pause()

            view.rootView.findViewById<ImageButton>(R.id.pausePlayBtn).setOnClickListener{
                AudioPlayer.getInstance().toggle()
            }
            view.rootView.findViewById<ImageButton>(R.id.prevBtn).setOnClickListener{
                if(currTrack > 0) {
                    currTrack--
                    AudioPlayer.getInstance().play(audios[currTrack].songURL!!, audios[currTrack].name!!,
                        audios[currTrack].artistName, audios[currTrack].albumName, requireContext())
                }
            }
            view.rootView.findViewById<ImageButton>(R.id.nextBtn).setOnClickListener{
                if(currTrack < audios.size - 1) {
                    currTrack++
                    AudioPlayer.getInstance().play(audios[currTrack].songURL!!, audios[currTrack].name!!,
                        audios[currTrack].artistName, audios[currTrack].albumName, requireContext())
                }
            }
        }
    }

    override fun onAudioClicked(position: Int, audio: UserAudio, view: ImageView) {
        val viewPager = view.rootView.findViewById<LockableViewPager>(R.id.view_pager_main)
        val tabs = view.rootView.findViewById<TabLayout>(R.id.tabs)

        lifecycleScope.launch {
            val audioViewPagerFragment = AudioPager.newInstance(position, audios)

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