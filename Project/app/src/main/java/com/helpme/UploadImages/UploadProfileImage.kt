package com.helpme.UploadImages

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.helpme.R

import kotlinx.android.synthetic.main.activity_upload_profile_image.*
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import java.io.File
import com.google.android.gms.tasks.OnSuccessListener
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.helpme.R.id.CurrentProfileImage
import android.R.attr.bitmap
import com.google.firebase.storage.OnPausedListener
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import Common.mySelf
import Model.user
import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Build
import android.support.annotation.RequiresApi
import com.helpme.EditProfile.MyProfile
import kotlinx.android.synthetic.main.activity_my_profile.*
import java.util.*


class UploadProfileImage : AppCompatActivity() {
    var bitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_profile_image)
    }

    fun showMessage(m: String) {
        Toast.makeText(this, m, Toast.LENGTH_LONG).show()
    }

    var PICK_IMAGE = 0
    fun selectNewImage(view: View) {
        bitmap = null
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadImage(view: View) {
        uploadProgressbar.setProgress(1)
        if (bitmap != null) {
            var Images: StorageReference = Utility.Utility.storageHandler.reference
                    .child("${user.userStorageImageFolder}/" +
                            "${mySelf.get_userName()}.jpg")
            val baos = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            var data = baos.toByteArray()
            var tmp = data.size / (1024.0 * 1024.0)
            if (tmp >= 2.5) {
                showMessage("Image size is too big")
                uploadProgressbar.setProgress(0)
                return
            } else {
                val b = ByteArrayOutputStream()
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, (1.5 / tmp * 100).toInt(), b)
                data = b.toByteArray()
            }

            mySelf.Checkset_image(BitmapFactory.decodeByteArray(data, 0, data.size))

            val uploadTask = Images.putBytes(data)
            showMessage("Uploading...")

// Listen for state changes, errors, and completion of the upload.
            uploadTask.addOnProgressListener(object : OnProgressListener<UploadTask.TaskSnapshot> {
                override fun onProgress(taskSnapshot: UploadTask.TaskSnapshot) {
                    selectNewImageBtn.isEnabled = false
                    uploadImageBtn.isEnabled = false
                    val progress = 100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()
                    if (progress.toInt() != 0)
                        uploadProgressbar.setProgress(progress.toInt())

                }
            }).addOnFailureListener(OnFailureListener {
                // Handle unsuccessful uploads
                showMessage("Image Uploading Failed")
                mySelf.Checkset_image(null)

            }).addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                override fun onSuccess(p0: UploadTask.TaskSnapshot) {
                    uploadProgressbar.setProgress(0)
                    selectNewImageBtn.isEnabled = true
                    uploadImageBtn.isEnabled = true
                    showMessage("Image Uploading Done")
                }
            })
        } else
            showMessage("No new image selected")
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                var uri = data.data
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri)
                CurrentProfileImage.setImageBitmap(bitmap)
            }
        }
    }

    override fun onBackPressed() {
        if (uploadProgressbar.progress == uploadProgressbar.max ||
                uploadProgressbar.progress == 0) {
            super.onBackPressed()
            val intent = Intent(this@UploadProfileImage, MyProfile::class.java)
            startActivity(intent)
            this.finish()
        }
    }

}
