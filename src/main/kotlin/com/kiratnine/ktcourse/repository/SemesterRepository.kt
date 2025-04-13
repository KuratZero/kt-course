package com.kiratnine.ktcourse.repository

import com.kiratnine.ktcourse.model.Semester
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Repository
interface SemesterRepository : JpaRepository<Semester, Long> {
    fun findAllByOrderByPositionAsc(): List<Semester>
}