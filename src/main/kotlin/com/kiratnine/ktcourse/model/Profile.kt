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

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val skills: MutableList<Skill> = mutableListOf(),

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val workExperience: MutableList<WorkExperience> = mutableListOf(),

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val education: MutableList<Education> = mutableListOf(),

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val contacts: MutableList<Contact> = mutableListOf(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = DbTable.PROFILE_LECTURES,
        joinColumns = [JoinColumn(name = DbFields.PROFILE_ID)],
        inverseJoinColumns = [JoinColumn(name = DbFields.LECTURE_ID)]
    )
    val lectures: MutableSet<Lecture> = mutableSetOf(),
) : BaseEntity() {
    fun replaceSkills(skills: Collection<Skill>) {
        this.skills.clear()
        skills.forEach {
            it.profile = this
            this.skills.add(it)
        }
    }

    fun replaceWorkExperiences(workExperiences: Collection<WorkExperience>) {
        this.workExperience.clear()
        workExperiences.forEach {
            it.profile = this
            this.workExperience.add(it)
        }
    }

    fun replaceEducations(educations: Collection<Education>) {
        this.education.clear()
        educations.forEach {
            it.profile = this
            this.education.add(it)
        }
    }

    fun replaceContacts(contacts: Collection<Contact>) {
        this.contacts.clear()
        contacts.forEach {
            it.profile = this
            this.contacts.add(it)
        }
    }
}
