package com.example.tabapp.fragments.Personmodel

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "person_table")
data class Person (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val Name: String,
    val Surname: String,
    val Phone: Int,
): Parcelable
