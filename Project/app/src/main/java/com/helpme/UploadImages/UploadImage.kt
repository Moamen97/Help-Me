package com.helpme.UploadImages

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.helpme.R
import kotlinx.android.synthetic.main.activity_upload_image.*
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import Control.UserControl
import Utility.imageKind
import com.helpme.EditProfile.MyProfile

class UploadImage : AppCompatActivity() {
    var bitmap: Bitmap? = null

    object imageInfo {
        var kind: imageKind = imageKind.profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)
        uploadImageBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                uploadImageBtn.isEnabled=false
                selectNewImageBtn.isEnabled = false
                if (bitmap != null)
                    UserControl.getInstance().uploadImage(this@UploadImage, bitmap)
                else {
                    showMessage("Image not Selected yet",Toast.LENGTH_SHORT)
                    uploadImageBtn.isEnabled = true
                    selectNewImageBtn.isEnabled = true
                }
            }
        })
    }

    fun showMessage(m: String,period:Int= Toast.LENGTH_LONG) {
        Toast.makeText(this, m,period).show()
    }

    var PICK_IMAGE = 0
    fun selectNewImage(view: View) {
        bitmap = null
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            var uri = data.data
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri)
            CurrentImage.setImageBitmap(bitmap)
        }
    }


    override fun onBackPressed() {
        if (uploadProgressbar.progress == uploadProgressbar.max ||
                uploadProgressbar.progress == 0) {
            super.onBackPressed()
            val intent = Intent(this@UploadImage, MyProfile::class.java)
            startActivity(intent)
            this.finish()
        }
    }

}