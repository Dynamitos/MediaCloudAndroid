package com.dynamitos.mediacloud.ui.upload

import android.content.Intent
import android.media.ExifInterface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.dynamitos.mediacloud.R
import com.dynamitos.mediacloud.data.model.UploadImage
import com.dynamitos.mediacloud.network.APIClient
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Pictures"),1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var imageArray = emptyArray<Uri>()
        if (resultCode == RESULT_OK && requestCode == 1){

            // if multiple images are selected
            if (data?.clipData != null) {
                var count = data.clipData?.itemCount

                for (i in 0 until count!!) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    imageArray += imageUri

                    //     iv_image.setImageURI(imageUri) Here you can assign your Image URI to the ImageViews
                }
                uploadFile(imageArray)
            }
            /*
            else if (data?.data != null) {
                // if single image is selected

                var imageUri: Uri = data.data!!
                uploadFile(imageUri)
                //   iv_image.setImageURI(imageUri) Here you can assign the picked image uri to your imageview

            }
            */
        }
        finish()
    }

    private fun uploadFile(uri: Array<Uri>) {
        var imageArray = emptyArray<UploadImage>()
        lifecycleScope.launch {
            for (i in uri.indices)
            {
                val stream = contentResolver.openInputStream(uri[i]) ?: return@launch
                val request = RequestBody.create(
                    MediaType.parse("image/*"),
                    stream.readBytes()
                ) // read all bytes using kotlin extension
                val filePart = MultipartBody.Part.createFormData(
                    "file",
                    "test.jpg",
                    request
                )
                APIClient.getInstance().apiService.uploadPhash(filePart)

                val exif = ExifInterface(stream)
                val name = exif.getAttribute(ExifInterface.TAG_FILE_SOURCE)
                val width = exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)
                val height = exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)
                imageArray += UploadImage(name?: "", width?.toInt() ?: 0, height?.toInt() ?: 0,"")
            }

            APIClient.getInstance().apiService.uploadImages(imageArray)
            Log.d("MyActivity", "on finish upload file")
        }
    }
}