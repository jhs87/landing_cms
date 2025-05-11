package com.project.landing_cms.persistence

import com.project.landing_cms.model.entity.FileInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FileInfoRepository: JpaRepository<FileInfo, Long> {
}