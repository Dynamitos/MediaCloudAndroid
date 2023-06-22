package com.dynamitos.mediacloud.ui.audio


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dynamitos.mediacloud.data.model.UserImage

class AudioPagerAdapter(fm: FragmentManager, private val audios: List<UserImage>) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val audio = audios[position]
        return AudioDetail.newInstance(audio, audio.name!!)
    }

    override fun getCount(): Int {
        return audios.size
    }
}