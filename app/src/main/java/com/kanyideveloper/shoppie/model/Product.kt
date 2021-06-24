package com.kanyideveloper.shoppie.model

import android.os.Parcel
import android.os.Parcelable

class Product(val id: Int = 0, val itemName: String? = null, val itemDescription: String? = null, val itemPrice: Int = 0, val itemImage: String? = null) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(itemName)
        parcel.writeString(itemDescription)
        parcel.writeInt(itemPrice)
        parcel.writeString(itemImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}