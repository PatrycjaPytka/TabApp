package com.example.tabapp.fragments.addDrug

import android.app.*
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tabapp.R
import com.example.tabapp.fragments.Drugmodel.Drug
import com.example.tabapp.fragments.Drugmodel.viewmodel.DrugViewModel
import kotlinx.android.synthetic.main.fragment_drug_add.*
import kotlinx.android.synthetic.main.fragment_drug_add.view.*
import java.util.*
import com.example.tabapp.AlarmReceiveView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.text.SimpleDateFormat





class DrugAddFragment : Fragment() {

    private lateinit var mDrugViewModel: DrugViewModel

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_drug_add, container, false)
        mDrugViewModel = ViewModelProvider(this).get(DrugViewModel::class.java)

        view.code_btn.setOnClickListener {
            dispatchTakePictureIntent()
        }

        view.save_btn.setOnClickListener {
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        val Name = name.text.toString()
        val Dose = dose.text
        val DailyDoses = daily_doses.text
        val Period = period.text
        val Code = code.text.toString()

        if(inputCheck(Name, Dose, DailyDoses, Period, Code)) {
            val drug = Drug(0, Name, (Dose.toString()).toDouble(), Integer.parseInt(DailyDoses.toString()), Integer.parseInt(Period.toString()), Code.toString())
            mDrugViewModel.addDrug(drug)

            addAlarm(Name, (Dose.toString()).toFloat(), Integer.parseInt(DailyDoses.toString()), (Period.toString()).toFloat(), Code)

            Toast.makeText(requireContext(), "Dodano nowy lek", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_drugAddFragment_to_drugListFragment)
        } else {
            Toast.makeText(requireContext(), "Wprowadzono niepoprawne dane.", Toast.LENGTH_LONG).show()
        }
    }

    private fun addAlarm(name: String, dose: Float, daily_doses: Int, period: Float, code: String) {
        val start = 7
        val middle_day = 14
        val stop = 22

        val calendar = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        val calendar3 = Calendar.getInstance()

        if (Integer.parseInt(daily_doses.toString()) == 1) {
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                middle_day, 0, 0
            )
            setAlarm(calendar.timeInMillis, name, dose, period, code);
        }
        if (Integer.parseInt(daily_doses.toString()) == 2) {
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                start, 0, 0
            )
            calendar2.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                stop, 0, 0
            )
            setAlarm(calendar.timeInMillis, name, dose, period, code);
            setAlarm(calendar2.timeInMillis, name, dose, period, code);
        }
        if (Integer.parseInt(daily_doses.toString()) == 3) {
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                start, 0, 0
            )
            calendar2.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                middle_day, 0, 0
            )
            calendar3.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                stop, 0, 0
            )
            setAlarm(calendar.timeInMillis, name, dose, period, code);
            setAlarm(calendar2.timeInMillis, name, dose, period, code);
            setAlarm(calendar3.timeInMillis, name, dose, period, code);
        }
        else {
            for (doses in (1..Integer.parseInt(daily_doses.toString())))  {

                var time = if (doses == 1) {
                                start
                            } else {
                                start + (16 / (doses - 1))
                            }
                var hour = Integer.parseInt(time.toString())
                val minute = 10 * (time - hour)

                if (minute >= 60) {
                    hour += 1
                    var new_minute = minute - 60
                    calendar.set(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        hour, new_minute, 0
                    )
                    setAlarm(calendar.timeInMillis, name, (dose.toString()).toFloat(), period, code);
                } else {
                    calendar.set(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        hour, minute, 0
                    )
                    setAlarm(calendar.timeInMillis, name, (dose.toString()).toFloat(), period, code);
                }
            }
        }
    }

    private fun setAlarm(timeInMillis: Long, name: String, dose: Float, period: Float, code: String) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiveView::class.java)

        // Ustawienie alarmu na minutę po dodaniu - w celu testowania poprawności działania
//        val msUntilTriggerHour: Long = 120000 // 2 minuty
//        val alarmTimeAtUTC: Long = Calendar.getInstance().getTimeInMillis() + msUntilTriggerHour
//        val notifyTimeAtUTC: Long = alarmTimeAtUTC  - 60000 // 1 minutę przed alarmem

        intent.putExtra("Kod_kreskowy", "$code")
        intent.putExtra("Nazwa_leku", "$name")
        intent.putExtra("Dawka", ("$dose").toString())
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val settings = this.requireActivity().getSharedPreferences("Switch1 state", 0)
        val switch1OnOff = settings.getBoolean("switchkey", false)
        var formatter = SimpleDateFormat("yyyy.MM.dd 'o' hh:mm:ss z")
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+01:00"))
        if (switch1OnOff) {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent,
            )
            var dateString = formatter.format(Date(timeInMillis))
            Toast.makeText(requireContext(), ("Alarm został dodany"), Toast.LENGTH_SHORT).show()
            Log.d("MainActivity", "Dodany alarm $dateString")
        } else {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent,
            )
            var dateString = formatter.format(Date(timeInMillis))
            Toast.makeText(requireContext(), ("Alarm został dodany"), Toast.LENGTH_SHORT).show()
            Log.d("MainActivity", "Dodany alarm $dateString")
        }
    }

    private fun inputCheck(name: String, dose: Editable, daily_doses: Editable, period: Editable, code: String): Boolean {
        return !(TextUtils.isEmpty(name) && dose.isEmpty() && daily_doses.isEmpty() && period.isEmpty() && TextUtils.isEmpty(code))
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
                Toast.makeText(requireContext(), "Coś poszło nie tak", Toast.LENGTH_LONG).show()
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
                requireContext(),
                "Wystąpił błąd. Spróbuj ponownie: " + e.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun displayTextFromImage(firebaseVisionText: FirebaseVisionText?) {
        if (firebaseVisionText != null) {
            for (block in firebaseVisionText.textBlocks) {
                val blockText = block.text
                code.setText(blockText)
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Nie wykryto tekstu", Toast.LENGTH_LONG
            ).show()
        }
    }

}

