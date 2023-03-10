package com.fastcampus.ch4.controller;


import com.fastcampus.ch4.dao.BoardDto;
import com.fastcampus.ch4.domain.PageHandler;
import com.fastcampus.ch4.domain.SearchCondition;
import com.fastcampus.ch4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/write")
    public String write(Model m){
        m.addAttribute("mode","new");
        return "board";
    }


    @PostMapping
    public String remove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr){
        String writer= (String) session.getAttribute("id");

        try {
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
          int rowCnt =  boardService.remove(bno,writer);
          if(rowCnt!=1)
              throw new Exception("board remove error");
          rattr.addFlashAttribute("msg","DEL_OK");
        } catch (Exception e) {
           e.printStackTrace();
            rattr.addFlashAttribute("msg","DEL_ERR");
        }

        return "redirect:/board/list";
    }

    @GetMapping("/read")
    public String read(Integer bno,int page, int pageSize, Model m) {
        try {
            BoardDto boardDto = boardService.read(bno);
            //m.addAttribute("boardDto", boardDto);
            m.addAttribute(boardDto);
            m.addAttribute("pageSize",pageSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "board";
    }


    @GetMapping("/list")
    public String list(SearchCondition sc,Model m, HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();  // ???????????? ???????????? ????????? ???????????? ??????

        try {
            int totalCnt=boardService.getSearchResultCnt(sc);
            PageHandler pageHandler=new PageHandler(totalCnt,sc);


            List<BoardDto> list=boardService.getSearchResultPage(sc);
            m.addAttribute("list",list);
            m.addAttribute("ph",pageHandler);
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "boardList"; // ???????????? ??? ????????????, ????????? ???????????? ??????
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. ????????? ?????????
        HttpSession session = request.getSession();
        // 2. ????????? id??? ????????? ??????, ????????? true??? ??????
        return session.getAttribute("id")!=null;
    }
}