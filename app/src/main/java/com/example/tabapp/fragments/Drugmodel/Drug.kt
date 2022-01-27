package com.example.tabapp.fragments.Drugmodel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "drug_table")
data class Drug (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val Name: String,
    val Dose: Double,
    val DailyDoses: Int,
    val Period: Int,
    val Code: String
): Parcelable