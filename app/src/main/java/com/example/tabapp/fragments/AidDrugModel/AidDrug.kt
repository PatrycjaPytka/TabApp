package com.example.tabapp.fragments.AidDrugModel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "aid_drug_table")
data class AidDrug (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val Name: String,
    val Amount: Int
): Parcelable