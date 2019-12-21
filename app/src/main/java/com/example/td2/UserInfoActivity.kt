package com.example.td2

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.new_user_info_act.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class UserInfoActivity<fragment> : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(UserInfoViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_user_info_act)
        take_picture_button.setOnClickListener{
            askCameraPermissionAndOpenCamera()
        }
        upload_image_button.setOnClickListener{
            askGalleryPermissionAndOpenGallery()
        }
        update.setOnClickListener{
            viewModel.update(firstname =  edit_firstname_info.text.toString(),
                lastname =  edit_lastname_info.text.toString(),
                email = edit_email_info.text.toString())
        }

        viewModel.userLiveData.observe(this, Observer { userInfo ->
            edit_firstname_info.setText(userInfo?.firstname)
            edit_lastname_info.setText(userInfo?.lastname)
            edit_email_info.setText(userInfo?.email)

            Glide.with(this).load(userInfo?.avatar)
                .circleCrop()
                .override(500, 500)
                .into(image_view_act)
        })

    }




    private fun askCameraPermissionAndOpenCamera(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                showDialogBeforeRequest { requestCameraPermission() }
            }
            else {
                requestCameraPermission()
            }

        }
        else {
            openCamera()
        }


    }


    private fun askGalleryPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                showDialogBeforeRequest { requestGalleryPermission() }
            }
            else {
                requestGalleryPermission()
            }

        }
        else {
            openGallery()
        }

    }


    private fun showDialogBeforeRequest(callback: () -> Unit){
        AlertDialog.Builder(this).apply {
            setMessage("EHH Passe la caméra chacal et un coca bien frais")
            setPositiveButton(android.R.string.ok) { _, _ -> callback.invoke()}
            setCancelable(true)
            show()
        }
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
    }

    private fun requestGalleryPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), GALLERY_PERMISSION_CODE)
    }

    private fun openCamera(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun openGallery(){
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            Toast.makeText(this, "Si tu refuse chakal, tu peux pas prendre de photo", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as? Bitmap
            if (bitmap != null) {
                handlePhotoTaken(bitmap)
            }
        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data as? Uri
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            handlePhotoTaken(bitmap)
        }
    }

    private fun handlePhotoTaken(bitmap: Bitmap) {
        val imageBody = imageToBody(bitmap)
        viewModel.updateAvatar(imageBody)
    }


    private fun imageToBody(image: Bitmap?): MultipartBody.Part{
        val f = File(cacheDir, "tmpfile.jpg")
        f.createNewFile()
        try {
            val fos = FileOutputStream(f)
            image?.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val body = RequestBody.create(MediaType.parse("image/png"), f)
        return MultipartBody.Part.createFormData("avatar", f.path, body)
    }


    override fun onResume() {
        super.onResume()
        viewModel.loadInfos()
    }



    companion object {
        const val CAMERA_PERMISSION_CODE = 42
        const val CAMERA_REQUEST_CODE = 201
        const val GALLERY_REQUEST_CODE = 69
        const val GALLERY_PERMISSION_CODE = 66
    }
}