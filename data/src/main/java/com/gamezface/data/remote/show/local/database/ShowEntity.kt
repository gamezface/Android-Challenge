package com.gamezface.data.remote.show.local.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gamezface.domain.show.entity.Image
import com.gamezface.domain.show.entity.Show

@Entity
data class ShowEntity(
    @PrimaryKey
    @ColumnInfo(name = FIELD_ID) var id: Long? = null,
    @ColumnInfo(name = FIELD_NAME) var name: String? = null,
    @ColumnInfo(name = FIELD_IMAGE_URL) var image: String? = null,
) {
    companion object {
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
        const val FIELD_IMAGE_URL = "image_url"

        fun toEntity(domain: Show) = ShowEntity(domain.id, domain.name, domain.image?.getImageUrl())
    }

    fun toDomain() =
        Show(
            id = id,
            name = name,
            genres = null,
            averageRuntime = null,
            schedule = null,
            image = Image(image, null, null),
            summary = null,
            embedded = null
        )
}
