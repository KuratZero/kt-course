package com.kiratnine.ktcourse.repository

import com.kiratnine.ktcourse.model.Lecture
import com.kiratnine.ktcourse.model.Semester
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Repository
interface LectureRepository : JpaRepository<Lecture, Long> {
    fun findAllByOrderByDateAsc(): List<Lecture>
    fun findAllBySemesterOrderByDateAsc(semester: Semester): List<Lecture>
    fun findAllByIdIn(ids: Collection<Long>): List<Lecture>
}