package com.project.landing_cms.model.entity

import jakarta.persistence.*
import com.project.landing_cms.model.dto.BoardDTO
import org.hibernate.annotations.Comment
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
class Board(boardReg: BoardDTO.BoardReg?, landingInfo: LandingInfo?) {

    @Id
    @Comment("고유번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var bNo: Long? = boardReg?.bNo

    @Comment("게시글 제목")
    var title: String? = boardReg?.title

    @Comment("게시글 내용")
    @Column(columnDefinition = "LONGTEXT")
    var contents: String? = boardReg?.contents

    @Comment("비밀글 여부")
    var secretYn: Boolean? = boardReg?.secretYn

    @Comment("비밀번호")
    var password: String? = boardReg?.password

    @Comment("삭제 여부")
    var delYn: Boolean? = boardReg?.delYn

    @Comment("등록한 사용자")
    var regMember: String? = boardReg?.regMember

    @Comment("등록 일시")
    @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00")
    var regDate: LocalDateTime? = boardReg?.regDate

    @Comment("수정 일시")
    @DateTimeFormat(pattern = "yyyy-MM-dd 00:00:00")
    var udtDate: LocalDateTime? = null

    @ManyToOne
    @Comment("연계 랜딩페이지")
    @JoinColumn(name = "LANDING_KEY")
    var landingKey: LandingInfo? = landingInfo
}