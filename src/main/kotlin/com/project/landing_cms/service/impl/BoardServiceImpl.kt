package com.project.landing_cms.service.impl

import jakarta.persistence.EntityNotFoundException
import com.project.landing_cms.model.dto.BoardDTO
import com.project.landing_cms.model.entity.Board
import com.project.landing_cms.model.entity.LandingInfo
import com.project.landing_cms.model.entity.Member
import com.project.landing_cms.persistence.BoardRepository
import com.project.landing_cms.service.BoardService
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl(private val boardRepository: BoardRepository): BoardService {

    override fun getBoardList(landingInfo: LandingInfo): List<BoardDTO.BoardInfo>? {
        return boardRepository.findByLandingKeyAndDelYnFalse(landingInfo).map { board -> BoardDTO.BoardInfo().toDto(board) }
    }

    override fun save(board: Board) {
        boardRepository.save(board)
    }

    override fun getBoard(bNo: Long): BoardDTO.BoardInfo? {
        return boardRepository.findById(bNo).map { board -> BoardDTO.BoardInfo().toDto(board) }.orElseThrow { EntityNotFoundException() }
    }

    override fun getBoardListRegDateDesc(landingInfo: LandingInfo): List<BoardDTO.BoardInfo>? {
        return boardRepository.findByLandingKeyAndDelYnFalseOrderByRegDateDesc(landingInfo).map { board -> BoardDTO.BoardInfo().toDto(board) }
    }

    override fun delete(bNoList: ArrayList<Long>) {
        boardRepository.updateDelYn(bNoList)
    }
}