package com.dynamitos.mediacloud.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.dynamitos.mediacloud.R
import com.dynamitos.mediacloud.data.LoginRepository
import com.dynamitos.mediacloud.data.model.AudioPlayer
import com.dynamitos.mediacloud.databinding.ActivityMainBinding
import com.dynamitos.mediacloud.ui.login.LoginActivity
import com.dynamitos.mediacloud.ui.upload.UploadActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.tabs.TabLayout



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var uploadImages: FloatingActionButton
    lateinit var uploadVideos: FloatingActionButton
    lateinit var uploadMusic: FloatingActionButton
    var isUploadOpen = false

    private fun setupGallery(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread.sleep(1000)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPagerMain
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

        findViewById<CircularProgressIndicator>(R.id.main_load_bar).visibility = View.GONE

        AudioPlayer.getInstance().initTextFields(findViewById(R.id.audioPlayerParent))
    }

    private fun resetData(sp: SharedPreferences){
        sp.edit().remove("auth_token").apply();
        sp.edit().remove("display_name").apply();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginRepository = LoginRepository.getInstance()
        val sp = getSharedPreferences(getString(com.dynamitos.mediacloud.R.string.app_name), Context.MODE_PRIVATE)

        //COMMENT THIS IF YOU WANT TO REMEMBER YOUR LOGIN!
        //This just exists for testing the full process incl. login
        //resetData(sp)

        if(!loginRepository.attemptLoginFromPrefs(sp)) {
            val intent = Intent(this, LoginActivity::class.java)
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    setupGallery()
                }
            }.launch(intent)

        } else {
            setupGallery()
        }

        val upload = findViewById<View>(R.id.uploadButton) as FloatingActionButton
        uploadImages = findViewById<View>(R.id.uploadImages) as FloatingActionButton
        uploadVideos = findViewById<View>(R.id.uploadVideos) as FloatingActionButton
        uploadMusic = findViewById<View>(R.id.uploadMusic) as FloatingActionButton
        upload.setOnClickListener {
            if (!isUploadOpen) {
                showUploadMenu()
            } else {
                closeUploadMenu()
            }
        }

        val buttonSelectImages = findViewById<ImageButton>(R.id.uploadImages)
        buttonSelectImages.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showUploadMenu() {
        isUploadOpen = true
        uploadImages.animate().translationY(-resources.getDimension(R.dimen.standard_55))
        uploadVideos.animate().translationY(-resources.getDimension(R.dimen.standard_105))
        uploadMusic.animate().translationY(-resources.getDimension(R.dimen.standard_155))
    }

    private fun closeUploadMenu() {
        isUploadOpen = false
        uploadImages.animate().translationY(0F)
        uploadVideos.animate().translationY(0F)
        uploadMusic.animate().translationY(0F)
    }




}