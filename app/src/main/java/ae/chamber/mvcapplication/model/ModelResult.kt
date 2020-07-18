package ae.chamber.mvcapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelResult(
    val abstract: String,
    val adx_keywords: String,
    val asset_id: Long,
    val byline: String,
    val column: String?,
    val des_facet: List<String>,
    //val geo_facet: List<String>,
    val id: Long,
    //val media: List<Media>,
    //val org_facet: List<String?>?,
    //val per_facet: List<String>,
    val published_date: String,
    val section: String,
    val source: String,
    val title: String,
    val type: String,
    val url: String,
    val views: Int
): Parcelable



/*
@Parcelize
data class Media(
    val approved_for_syndication: Int,
    val caption: String,
    val copyright: String,
    @SerializedName("media-metadata") val mediaMetadata: List<MediaMetadata>,
    val subtype: String,
    val type: String
):Parcelable

@Parcelize
data class MediaMetadata(
    val format: String,
    val height: Int,
    val url: String,
    val width: Int
):Parcelable*/
