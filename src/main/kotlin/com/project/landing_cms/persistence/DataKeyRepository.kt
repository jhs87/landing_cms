package com.project.landing_cms.persistence

import com.project.landing_cms.model.entity.DataKey
import com.project.landing_cms.model.entity.LandingInfo
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
interface DataKeyRepository: JpaRepository<DataKey, Long> {

    fun findByLandingKey(landingKey: LandingInfo): List<DataKey>?

    @Modifying
    @Transactional
    fun deleteByLandingKey(landingKey: LandingInfo)
}