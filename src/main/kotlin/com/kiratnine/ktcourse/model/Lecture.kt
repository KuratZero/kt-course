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

    val tags: MutableSet<String> = mutableSetOf(),

    @ManyToMany(mappedBy = "createdLectures", fetch = FetchType.LAZY)
    var creators: MutableSet<Profile> = mutableSetOf(),

    @ManyToMany(mappedBy = "favoriteLectures", fetch = FetchType.LAZY)
    var favoriteByProfiles: MutableSet<Profile> = mutableSetOf(),

    @ManyToOne
    @JoinColumn(name = DbFields.SEMESTER_ID)
    var semester: Semester? = null,
) : BaseEntity() {
    fun updateCreators(creators: Collection<Profile>) {
        this.creators.clear()
        creators.forEach { it.createdLectures.add(this) }
        this.creators.addAll(creators)
    }

    fun removeCreators() {
        this.creators.forEach { it.createdLectures.remove(this) }
        this.creators.clear()
    }
}
