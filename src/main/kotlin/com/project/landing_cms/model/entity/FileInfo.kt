package com.project.landing_cms.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
class FileInfo {

    @Id
    @Comment("고유번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var fNo: Long? = null

    @Comment("파일명")
    private var fileName: String? = null

    @Comment("파일 경로")
    private var filePath: String? = null

    @Comment("파일 실제 경로")
    private var fileRealPath: String? = null

    @Comment("파일 크기")
    private var fileSize = 0

    @Comment("타겟 카테고리")
    private var targetCategory = 0

    @Comment("타겟 고유번호")
    private var parentId: Long? = null

    @Comment("삭제 여부")
    private var isDeleted = false
}