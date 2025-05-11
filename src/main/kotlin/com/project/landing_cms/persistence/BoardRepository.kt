package com.project.landing_cms.persistence

import com.project.landing_cms.model.entity.Board
import com.project.landing_cms.model.entity.LandingInfo
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository: JpaRepository<Board, Long> {

    fun findByLandingKeyAndDelYnFalse(landingInfo: LandingInfo): List<Board>

    fun findByLandingKeyAndDelYnFalseOrderByRegDateDesc(landingInfo: LandingInfo): List<Board>

    @Modifying
    @Transactional
    @Query(value = "UPDATE Board b SET b.delYn = true WHERE b.bNo in ?1")
    fun updateDelYn(bNoList: ArrayList<Long>)
}