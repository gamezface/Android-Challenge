package com.gamezface.domain.show.entity

data class Show(
    val id: Long?,
    val name: String?,
    val genres: List<String>?,
    val averageRuntime: Int?,
    val schedule: Schedule?,
    val image: Image?,
    val summary: String?,
    val embedded: Embedded?
) {
    fun getSchedule(): String {
        return schedule?.takeUnless { it.days.isNullOrEmpty() }?.run {
            if (time.isNullOrEmpty()) days?.joinToString()
            else days?.joinToString().plus(" at $time")
        } ?: "$averageRuntime min"
    }

    fun getGenres() = genres?.joinToString()
}