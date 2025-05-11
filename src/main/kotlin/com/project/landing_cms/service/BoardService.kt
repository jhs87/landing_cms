package com.project.landing_cms.service

import com.project.landing_cms.model.dto.BoardDTO
import com.project.landing_cms.model.entity.Board
import com.project.landing_cms.model.entity.LandingInfo
import com.project.landing_cms.model.entity.Member

interface BoardService {

    fun getBoardList(landingInfo: LandingInfo): List<BoardDTO.BoardInfo>?

    fun save(board: Board)

    fun getBoard(bNo: Long): BoardDTO.BoardInfo?

    fun getBoardListRegDateDesc(landingInfo: LandingInfo): List<BoardDTO.BoardInfo>?

    fun delete(bNoList: ArrayList<Long>)
}