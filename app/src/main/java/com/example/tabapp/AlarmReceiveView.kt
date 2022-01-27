package com.example.tabapp

import android.app.*
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.media.MediaPlayer
import android.media.AudioManager
import android.media.MediaPlayer.OnPreparedListener

import android.media.RingtoneManager
import android.net.Uri
import java.io.IOException
import kotlinx.android.synthetic.main.activity_alarm_on_receive.*
import android.widget.TextView
import android.provider.MediaStore
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tabapp.fragments.Personmodel.viewmodel.PersonViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.util.*
import kotlin.concurrent.timerTask


class AlarmReceiveView : AppCompatActivity() {

    companion object {
        const val CAMERA_PERMISSION_CODE = 1
    }

    private var mediaPlayer: MediaPlayer? = null
    private var drug_code: String? = null
    private var drug_name: String? = null
    private var drug_dose: String? = null
    private var textView: TextView? = null
    private lateinit var mPersonViewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_on_receive)

        drug_code = intent.getStringExtra("Kod_kreskowy")
        drug_name = intent.getStringExtra("Nazwa_leku")
        drug_dose = intent.getStringExtra("Dawka")
        textView = findViewById(R.id.drug_code_info)
        mPersonViewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

        startsecondactivity()
    }

    fun startsecondactivity() {
        if (!isDestroyed) {
            val timeTask = timerTask {
                if (!isDestroyed) {
                    val phones = mPersonViewModel.loadAllPhones()
                    for (phone in phones) {
                        try {
                            val smsManager: SmsManager = SmsManager.getDefault()
                            smsManager.sendTextMessage(
                                phone.toString(),
                                null,
                                "UWAGA! Alarm uruchomiony przez aplikację TabApp nie został wyłączony!",
                                null,
                                null
                            );
                        } catch (e: Exception) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG)
                                .show();
                        }
                    }
                    Toast.makeText(
                        getApplicationContext(),
                        "Wiadomość informująca bliskich została wysłana",
                        Toast.LENGTH_LONG
                    ).show();
                }
            }
            val timer = Timer()
            timer.schedule(timeTask, 60000)
        }
    }

    override fun onStart() {
        super.onStart()

        val settings = getSharedPreferences("Switch1 state", 0)
        val switch1OnOff = settings.getBoolean("switchkey", false)
        val settings2 = getSharedPreferences("Switch2 state", 0)
        val switch2OnOff = settings2.getBoolean("switch2key", false)

        val myUri: Uri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE)

        take_photo.setOnClickListener {
            dispatchTakePictureIntent()
        }
        take_medicine_btn.setOnClickListener {
            onStop()
        }
        button2.setOnClickListener {
            val phones = mPersonViewModel.loadAllPhones()
            for (phone in phones) {
                try {
                    val smsManager: SmsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(
                        phone.toString(), null,
                        "UWAGA! Alarm uruchomiony przez aplikację TabApp nie został wyłączony z powodu braku leku!",
                        null, null
                    );
                } catch (e: Exception) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
            Toast.makeText(getApplicationContext(), "Wiadomość informująca bliskich została wysłana", Toast.LENGTH_LONG).show();
            onStop()
        }
        drugs_info.setText("$drug_name")
        mediaPlayer = MediaPlayer()
        class Listener : OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer) {
                mp.isLooping = true;
                mp.start()
            }
        }
        mediaPlayer!!.setOnPreparedListener(Listener())
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            mediaPlayer!!.setDataSource(applicationContext, myUri)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mediaPlayer!!.prepareAsync()

        if (switch2OnOff) {
            take_medicine_btn.setVisibility(View.GONE)
        } else {
            infoText.setVisibility(View.GONE)
            take_photo.setVisibility(View.GONE)
            textView?.setText("Przyjmij $drug_dose sztuk/i leku $drug_name")
        }
    }

    override fun onStop() {
        super.onStop()

        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, CAMERA_PERMISSION_CODE)
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
                if (blockText == drug_code) {
                    Toast.makeText(this, "Wskazane opakowanie jest poprawne.", Toast.LENGTH_LONG).show()
                    textView?.setText("Przyjmij $drug_dose sztuk/i leku $drug_name")
                    take_photo.setVisibility(View.GONE)
                    take_medicine_btn.setVisibility(View.GONE)
                    infoText.setVisibility(View.GONE)
                    mediaPlayer!!.release()
                    mediaPlayer = null
                } else {
                    Toast.makeText(this, "Błędne opakowanie! Spróbuj ponownie", Toast.LENGTH_LONG).show()
                    textView?.setText("$blockText")
                }
            }
        } else {
            Toast.makeText(this, "Nie wykryto tekstu", Toast.LENGTH_LONG).show()
        }
    }
}