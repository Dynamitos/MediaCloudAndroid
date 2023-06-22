package com.dynamitos.mediacloud.ui.audio

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.dynamitos.mediacloud.R
import com.dynamitos.mediacloud.data.LoginRepository
import com.dynamitos.mediacloud.data.model.UserAudio
import com.github.chrisbanes.photoview.PhotoView

class AudioDetail : Fragment() {

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
        return inflater.inflate(R.layout.fragment_audio_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val audio = arguments?.getParcelable<UserAudio>(AUDIO)
        val imageView = view.findViewById<PhotoView>(R.id.detail_audio)

        val glideUrl = GlideUrl(audio?.artURL, LazyHeaders.Builder()
            .addHeader("Authorization", LoginRepository.getInstance().user!!.authToken)
            .build())

        Glide.with(requireContext())
            .load(glideUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
            /*.into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    startPostponedEnterTransition()
                    imageView.setImageDrawable(resource)
                }
            })*/

        view.findViewById<TextView>(R.id.songNameDetail).text = audio?.name
        view.findViewById<TextView>(R.id.artistNameDetail).text = audio?.artistName
        view.findViewById<TextView>(R.id.albumNameDetail).text = audio?.albumName
    }

    companion object {
        private const val AUDIO = "audio_item"
        private const val TRANSITION_NAME = "transition_name"

        fun newInstance(image: UserAudio, transitionName: String): AudioDetail {
            val fragment = AudioDetail()
            val args = Bundle()
            args.putParcelable(AUDIO, image)
            args.putString(TRANSITION_NAME, transitionName)
            fragment.arguments = args
            return fragment
        }
    }
}