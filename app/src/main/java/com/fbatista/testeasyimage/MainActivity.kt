package com.fbatista.testeasyimage

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import pl.aprilapps.easyphotopicker.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var easyImage: EasyImage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
    }

    private fun setListeners() {
        cameraBtn.setOnClickListener { openSelectorDialog() }
    }

    private fun openSelectorDialog() {

        easyImage = EasyImage.Builder(this)
            .setChooserTitle("Seleciona uma opção")
            .setChooserType(ChooserType.CAMERA_AND_GALLERY)
            .setCopyImagesToPublicGalleryFolder(true)
            .setFolderName("App teste")
            .build()


        easyImage.openChooser(this)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        easyImage.handleActivityResult(requestCode, resultCode, data, this, object: DefaultCallback() {
            override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                val mediafile: MediaFile = imageFiles[0]

                val file = mediafile.file

                val bitmap = BitmapFactory.decodeFile(file.path)
                pictureIv.setImageBitmap(bitmap)

            }

            override fun onCanceled(source: MediaSource) {
                super.onCanceled(source)
                showToast("Cancelado")
            }

            override fun onImagePickerError(error: Throwable, source: MediaSource) {
                super.onImagePickerError(error, source)
                showToast("Erro ao pegar a foto")
            }
        })

    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
