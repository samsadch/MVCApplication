package ae.chamber.mvcapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseModel(
    val copyright: String,
    val num_results: Int,
    val results: List<ModelResult>,
    val status: String
):Parcelable
