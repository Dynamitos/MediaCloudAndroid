package com.dynamitos.mediacloud.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.dynamitos.mediacloud.R
import com.dynamitos.mediacloud.data.LoginRepository
import com.dynamitos.mediacloud.databinding.ActivityMainBinding
import com.dynamitos.mediacloud.ui.login.LoginActivity
import com.dynamitos.mediacloud.ui.upload.UploadActivity
import com.google.android.material.progressindicator.CircularProgressIndicator


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun setupGallery(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread.sleep(1000)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

        findViewById<CircularProgressIndicator>(R.id.main_load_bar).visibility = View.GONE
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

        val buttonSelectImages = findViewById<ImageButton>(R.id.uploadButton)
        buttonSelectImages.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent);
        }

    }


}