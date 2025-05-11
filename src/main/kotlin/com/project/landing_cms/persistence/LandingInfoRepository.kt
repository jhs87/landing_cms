package com.project.landing_cms.persistence

import com.project.landing_cms.model.entity.LandingInfo
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LandingInfoRepository: JpaRepository<LandingInfo, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE LandingInfo SET isUsed = ?2 WHERE lNo = ?1")
    fun setUsed(id: Long, used: Boolean)
}