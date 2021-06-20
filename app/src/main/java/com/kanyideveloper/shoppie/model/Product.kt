package com.kanyideveloper.shoppie.model

import android.os.Parcel
import android.os.Parcelable

class Product(val id: Int, val productName: String?, val productDescription: String?, val productPrice: String?, val productImageUrl: String?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(productName)
        parcel.writeString(productDescription)
        parcel.writeString(productPrice)
        parcel.writeString(productImageUrl)
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