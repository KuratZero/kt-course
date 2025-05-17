package com.kiratnine.ktcourse.model

import com.kiratnine.ktcourse.model.constant.DbFields
import com.kiratnine.ktcourse.model.converter.MapToJsonConverter
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Entity
class Lecture(
    @Convert(converter = MapToJsonConverter::class)
    val title: LocalizedString,
    @Convert(converter = MapToJsonConverter::class)
    val description: LocalizedString,
    val date: LocalDateTime,

    var presentationId: String? = null,

    @ManyToMany(mappedBy = "lectures", fetch = FetchType.LAZY)
    var profiles: MutableSet<Profile> = mutableSetOf(),

    @ManyToOne
    @JoinColumn(name = DbFields.SEMESTER_ID)
    var semester: Semester? = null,
) : BaseEntity() {
    fun updateProfiles(profiles: Collection<Profile>) {
        this.profiles.clear()
        profiles.forEach { it.lectures.add(this) }
        this.profiles.addAll(profiles)
    }

    fun removeProfiles() {
        this.profiles.forEach { it.lectures.remove(this) }
        this.profiles.clear()
    }
}