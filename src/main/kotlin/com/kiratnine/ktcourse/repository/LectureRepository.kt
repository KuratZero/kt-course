package com.kiratnine.ktcourse.repository

import com.kiratnine.ktcourse.model.Lecture
import com.kiratnine.ktcourse.model.Semester
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Repository
interface LectureRepository : JpaRepository<Lecture, Long> {
    fun findAllByOrderByDateAsc(): List<Lecture>
    fun findAllBySemesterOrderByDateAsc(semester: Semester): List<Lecture>
    fun findBySlug(slug: String): Optional<Lecture>
    fun deleteBySlug(slug: String)
    fun findAllBySlugIn(slugs: List<String>): List<Lecture>
}