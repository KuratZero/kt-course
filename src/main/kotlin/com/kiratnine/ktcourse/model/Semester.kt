package com.kiratnine.ktcourse.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Entity
class Semester(
    val title: String,
    val description: String,
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