package com.example.tabapp

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*


class MenuActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        tts = TextToSpeech(this, this)

        picture_btn.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    fun drugsList(view: View) {
        val mainintent = Intent(this, MainActivity::class.java)
        startActivity(mainintent)
    }
    fun alarmSettings(view: View) {
        val alarmintent = Intent(this, AlarmActivity::class.java)
        startActivity(alarmintent)
    }
    fun closePerson(view: View) {
        val personintent = Intent(this, ClosePersonActivity::class.java)
        startActivity(personintent)
    }
    fun firstAid(view: View) {
        val aidintent = Intent(this, FirstAidActivity::class.java)
        startActivity(aidintent)
    }

    fun alarmReceiverTest(view: View) {
        val intent = Intent(this, AlarmReceiveView::class.java)
        startActivity(intent)
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, AlarmReceiveView.CAMERA_PERMISSION_CODE)
        } catch (e: ActivityNotFoundException) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_PERMISSION_CODE) {
                val imageBitmap = data?.extras?.get("data") as Bitmap

                detectTextFromImage(imageBitmap)
            } else {
                Toast.makeText(this, "Coś poszło nie tak", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun detectTextFromImage(imageBitmap: Bitmap) {
        var firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap)
        var firebaseVisionTextDetector = FirebaseVision.getInstance().onDeviceTextRecognizer
        firebaseVisionTextDetector.processImage(firebaseVisionImage).addOnSuccessListener { firebaseVisionText ->
            displayTextFromImage(firebaseVisionText)
        }.addOnFailureListener { e ->
            Toast.makeText(
                this,
                "Wystąpił błąd. Spróbuj ponownie: " + e.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun displayTextFromImage(firebaseVisionText: FirebaseVisionText?) {
        if (firebaseVisionText != null) {
            for (block in firebaseVisionText.textBlocks) {
                val blockText = block.text
                if (blockText != "") {
                    Log.d("MainActivity", "$blockText")
                    tts!!.speak(blockText, TextToSpeech.QUEUE_FLUSH, null)
//                    Toast.makeText(this, "$blockText", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Nie udało się odczytać nazwy leku. Spróbuj ponownie", Toast.LENGTH_LONG)
                }
            }
        } else {
            Toast.makeText(
                this,
                "Nie wykryto tekstu", Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("MainActivity","The Language specified is not supported!")
            } else {
                Log.e("MainActivity","Initialization succeed")
            }

        } else {
            Log.e("MainActivity", "Initilization Failed!")
        }
    }

}