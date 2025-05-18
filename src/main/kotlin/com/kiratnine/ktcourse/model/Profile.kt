package com.kiratnine.ktcourse.model

import com.kiratnine.ktcourse.model.constant.DbFields
import com.kiratnine.ktcourse.model.constant.DbTable
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Entity
class Profile(
    @Column(unique = true, nullable = false)
    val login: String,

    @Column(nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    var role: Role = Role.USER,

    val name: String,

    var avatarId: String? = null,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = DbFields.WORK_EXPERIENCE_ID)
    var workExperience: WorkExperience? = null,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = DbFields.EDUCATION_ID)
    var education: Education? = null,

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    val contacts: MutableList<Contact> = mutableListOf(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = DbTable.PROFILE_LECTURES,
        joinColumns = [JoinColumn(name = DbFields.PROFILE_ID)],
        inverseJoinColumns = [JoinColumn(name = DbFields.LECTURE_ID)]
    )
    val createdLectures: MutableSet<Lecture> = mutableSetOf(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = DbTable.PROFILE_FAVORITE_LECTURES,
        joinColumns = [JoinColumn(name = DbFields.PROFILE_ID)],
        inverseJoinColumns = [JoinColumn(name = DbFields.LECTURE_ID)]
    )
    val favoriteLectures: MutableSet<Lecture> = mutableSetOf(),
) : BaseEntity() {
    fun replaceContacts(contacts: Collection<Contact>) {
        this.contacts.clear()
        contacts.forEach {
            it.profile = this
            this.contacts.add(it)
        }
    }
}
