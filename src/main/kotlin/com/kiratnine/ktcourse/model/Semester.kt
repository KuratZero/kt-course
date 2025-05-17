package com.kiratnine.ktcourse.model

import com.kiratnine.ktcourse.model.converter.MapToJsonConverter
import jakarta.persistence.CascadeType
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Entity
class Semester(
    @Convert(converter = MapToJsonConverter::class)
    val title: LocalizedString,
    @Convert(converter = MapToJsonConverter::class)
    val description: LocalizedString,
    val position: Long,

    @OrderBy("date ASC")
    @OneToMany(mappedBy = "semester", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var lectures: MutableList<Lecture> = mutableListOf(),
) : BaseEntity() {
    fun updateTopics(lectures: List<Lecture>) {
        this.lectures.clear()
        lectures.forEach {
            it.semester = this
        }
        this.lectures.addAll(lectures)
    }
}