package com.gsatria.a2kang.model.response

import com.google.gson.annotations.SerializedName

data class TukangHomeResponse(
    @SerializedName("profile")
    val nestedProfile: TukangResponse? = null,
    @SerializedName("jobs")
    val nestedJobs: TukangJobsSection? = null,

    @SerializedName("ID")
    val flatId: Int? = null,
    @SerializedName("Name")
    val flatName: String? = null,
    @SerializedName("Bio")
    val flatBio: String? = null,
    @SerializedName("Services")
    val flatServices: String? = null,
    @SerializedName("Rating")
    val flatRating: Float? = null
) {
    val profile: TukangResponse?
        get() {
            if (nestedProfile != null) return nestedProfile

            if (flatId != null || flatName != null) {
                return TukangResponse(
                    id = flatId,
                    name = flatName,
                    bio = flatBio,
                    services = flatServices,
                    rating = flatRating,
                    category = ""
                )
            }
            return null
        }

    val jobs: TukangJobsSection?
        get() = nestedJobs ?: TukangJobsSection()
}

// Class pendukung tetap sama...
data class TukangResponse(
    @SerializedName("ID") val id: Int? = null,
    @SerializedName("Name") val name: String? = null,
    @SerializedName("Bio") val bio: String? = null,
    @SerializedName("Services") val services: String? = null,
    @SerializedName("Category") val category: String? = null,
    @SerializedName("Rating") val rating: Float? = null
)

data class TukangJobsSection(
    @SerializedName("pending") val pending: List<TransactionResponse>? = emptyList(),
    @SerializedName("confirmed") val confirmed: List<TransactionResponse>? = emptyList(),
    @SerializedName("done") val done: List<TransactionResponse>? = emptyList()
)

data class TransactionResponse(
    @SerializedName("ID") val id: Int? = null,
    @SerializedName("Title") val title: String? = null,
    @SerializedName("Date") val date: String? = null,
    @SerializedName("Time") val time: String? = null
)