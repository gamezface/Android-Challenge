package com.gamezface.domain.show

import com.gamezface.domain.show.entity.Schedule
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ScheduleTest {
    @Test
    fun scheduleNullableTest() {
        val schedule = Schedule(null, null)
        assertNull(schedule.time)
        assertNull(schedule.days)
    }

    @Test
    fun scheduleModelTest() {
        val schedule = Schedule("11:00", listOf("Monday", "Sunday"))
        assertNotNull(schedule.time)
        assertNotNull(schedule.days)
        assertEquals(schedule.time, "11:00")
        assert(!schedule.time.isNullOrEmpty())
        assertEquals(schedule.days?.first(), "Monday")
        assertEquals(schedule.days?.last(), "Sunday")
    }
}