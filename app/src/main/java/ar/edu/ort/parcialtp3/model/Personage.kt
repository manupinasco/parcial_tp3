package ar.edu.ort.parcialtp3.model

import android.os.Parcel
import android.os.Parcelable

data class Personage(
    val id: Int,
    val name: String?,
    val status: String?,
    val species: String?,
    val image: String?

) : Parcelable {
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
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeString(species)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Personage> {
        override fun createFromParcel(parcel: Parcel): Personage {
            return Personage(parcel)
        }

        override fun newArray(size: Int): Array<Personage?> {
            return arrayOfNulls(size)
        }
    }
}


