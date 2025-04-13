package com.kiratnine.ktcourse.repository

import com.kiratnine.ktcourse.model.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Repository
interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findByLogin(login: String): Profile?
    fun findAllByLoginIn(logins: List<String>): List<Profile>
}