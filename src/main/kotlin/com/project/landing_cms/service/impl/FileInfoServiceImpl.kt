package com.project.landing_cms.service.impl

import com.project.landing_cms.persistence.FileInfoRepository
import com.project.landing_cms.service.FileInfoService
import org.springframework.stereotype.Service

@Service
class FileInfoServiceImpl(private val fileInfoRepository: FileInfoRepository): FileInfoService {
}