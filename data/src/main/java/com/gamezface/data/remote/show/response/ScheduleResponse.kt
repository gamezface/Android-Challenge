package com.gamezface.data.remote.show.response

import com.gamezface.data.base.BaseResponse
import com.gamezface.domain.show.entity.Schedule

data class ScheduleResponse(val time: String?, val days: List<String>?) : BaseResponse<Schedule> {
    override fun toDomain() = Schedule(time, days)
}
