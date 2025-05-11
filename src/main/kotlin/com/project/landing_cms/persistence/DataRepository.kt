package com.project.landing_cms.persistence

import com.project.landing_cms.model.entity.Data
import com.project.landing_cms.model.entity.LandingInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DataRepository: JpaRepository<Data, Long> {

    fun findByLandingKeyAndIsDeletedFalse(landingKey: LandingInfo): List<Data>?

    fun countByLandingKey(landingKey: LandingInfo): Int

}