package com.dynamitos.mediacloud.ui.gallery

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.dynamitos.mediacloud.R
import com.dynamitos.mediacloud.data.LoginRepository
import com.dynamitos.mediacloud.data.model.UserImage
import com.dynamitos.mediacloud.network.APIClient
import com.github.chrisbanes.photoview.PhotoView

class ImageDetail : Fragment() {

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
        return inflater.inflate(R.layout.fragment_image_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = arguments?.getParcelable<UserImage>(IMAGE)
        val imageView = view.findViewById<PhotoView>(R.id.detail_image)

        val glideUrl = GlideUrl(image?.imgURL, LazyHeaders.Builder()
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
    }

    companion object {
        private const val IMAGE = "image_item"
        private const val TRANSITION_NAME = "transition_name"

        fun newInstance(image: UserImage, transitionName: String): ImageDetail {
            val fragment = ImageDetail()
            val args = Bundle()
            args.putParcelable(IMAGE, image)
            args.putString(TRANSITION_NAME, transitionName)
            fragment.arguments = args
            return fragment
        }
    }
}