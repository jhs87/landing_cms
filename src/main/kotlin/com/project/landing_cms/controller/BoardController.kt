package com.project.landing_cms.controller

import com.project.landing_cms.model.dto.BoardDTO
import com.project.landing_cms.model.entity.*
import com.project.landing_cms.service.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

/**
 * Board controller
 *
 * @property memberService
 * @property memberRelateKeyService
 * @property boardService
 * @property dataKeyService
 * @constructor Create empty Board controller
 */
@Controller
class BoardController(private val memberService: MemberService,
                      private val memberRelateKeyService: MemberRelateKeyService,
                      private val boardService: BoardService,
                      private val dataKeyService: DataKeyService,
                      private val landingInfoService: LandingInfoService
) {

    /**
     * 게시판 리스트 호출
     *
     * @param lNo
     * @param member
     * @param model
     * @return ModelAndView
     */
    @GetMapping("/boards/{lNo}")
    fun boardList(@PathVariable("lNo") lNo: Long, @AuthenticationPrincipal member: Member, model: Model): ModelAndView {
        val landingInfo: LandingInfo = landingInfoService.getLandingInfo(lNo)
        val boardList: List<BoardDTO.BoardInfo>? = boardService.getBoardList(landingInfo)
        model.addAttribute("lNo", lNo)
        model.addAttribute("boardTitle", landingInfo.getTitle())
        model.addAttribute("list", boardList)
        return ModelAndView("board/index")
    }

    /**
     * 게시글 확인
     *
     * @param bNo
     * @param member
     * @param model
     * @return ResponseEntity
     */
    @GetMapping("/boards/reg")
    fun boardRegPage(@RequestParam(value = "bNo") bNo: Long, @AuthenticationPrincipal member: Member,
                     model: Model): ResponseEntity<BoardDTO.BoardInfo> {
        val boardInfo: BoardDTO.BoardInfo? = boardService.getBoard(bNo)
        return ResponseEntity(boardInfo, HttpStatus.OK)
    }

    /**
     * 게시글 등록/수정
     *
     * @param boardReg
     * @param member
     * @param request
     * @param model
     * @return ModelAndView
     */
    @PostMapping("/boards")
    fun boardReg(@ModelAttribute boardReg: BoardDTO.BoardReg,
                 @AuthenticationPrincipal member: Member,
                 request: HttpServletRequest, model: Model): ModelAndView {
        val landingInfo: LandingInfo = landingInfoService.getLandingInfo(boardReg.lNo!!)
        boardService.save(Board(boardReg, landingInfo))
        val boardList: List<BoardDTO.BoardInfo>? = boardService.getBoardList(landingInfo)
        model.addAttribute("lNo", boardReg.lNo)
        model.addAttribute("list", boardList)
        return ModelAndView("board/index")
    }

    /**
     * 게시글 삭제
     *
     * @param bNoList
     * @param member
     * @param request
     * @param model
     * @return ModelAndView
     */
    @DeleteMapping("/boards")
    fun boardDel(@RequestParam(value = "bNoList[]") bNoList: ArrayList<Long>,
                 @AuthenticationPrincipal member: Member,
                 request: HttpServletRequest, model: Model): ResponseEntity<String> {
        boardService.delete(bNoList)
        return ResponseEntity(HttpStatus.OK)
    }
}