package com.kiratnine.ktcourse.repository

import com.kiratnine.ktcourse.model.Comment
import com.kiratnine.ktcourse.model.Lecture
import com.kiratnine.ktcourse.model.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByAuthorOrderByCreatedAtAsc(author: Profile): List<Comment>
    fun findByLectureOrderByCreatedAtAsc(lecture: Lecture): List<Comment>
}