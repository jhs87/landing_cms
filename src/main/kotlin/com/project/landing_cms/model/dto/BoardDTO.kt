package com.project.landing_cms.model.dto

import io.ktor.server.html.*
import lombok.Data
import com.project.landing_cms.model.entity.Board
import com.project.landing_cms.model.entity.LandingInfo
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.util.*

class BoardDTO {

    @Data
    class BoardReg {
        var bNo: Long? = null
        var title: String? = null
        var contents: String? = null
        var secretYn: Boolean? = false
        var password: String? = null
        var delYn: Boolean? = false
        var lNo: Long? = null
        var regDate: LocalDateTime? = LocalDateTime.now()
        var udtDate: LocalDateTime? = null
        var regMember: String? = null
    }

    @Data
    class BoardInfo {
        var bNo: Long? = null
        var title: String? = null
        var contents: String? = null
        var secretYn: Boolean? = null
        var password: String? = null
        var delYn: Boolean? = null
        var landingKey: LandingInfo? = null
        @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00")
        var regDate: LocalDateTime? = null
        @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00")
        var udtDate: LocalDateTime? = null
        var regMember: String? = null

        fun toDto(board: Board): BoardInfo {
            this.bNo = board.bNo
            this.title = board.title
            this.contents = board.contents
            this.secretYn = board.secretYn
            this.delYn = board.delYn
            this.landingKey = board.landingKey
            this.regDate = board.regDate
            this.udtDate = board.udtDate
            this.regMember = board.regMember
            return this
        }
    }

    @Data
    class BoardExcel {
        var title: String? = null
        var contents: String? = null
        @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00")
        var regDate: LocalDateTime? = null
        @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00")
        var udtDate: LocalDateTime? = null
        var regMember: String? = null

        fun toDto(info: BoardInfo): BoardExcel {
            this.title = info.title
            this.contents = info.contents
            this.regDate = info.regDate
            this.udtDate = info.udtDate
            this.regMember = info.regMember
            return this
        }
    }
}