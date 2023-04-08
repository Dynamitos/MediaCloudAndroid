package com.dynamitos.mediacloud.data.model

data class UserImageList(
    val images: Array<UserImage>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserImageList

        if (!images.contentEquals(other.images)) return false

        return true
    }

    override fun hashCode(): Int {
        return images.contentHashCode()
    }
}
