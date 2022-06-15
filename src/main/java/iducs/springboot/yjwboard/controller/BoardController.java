package iducs.springboot.yjwboard.controller;

import iducs.springboot.yjwboard.domain.Board;
import iducs.springboot.yjwboard.domain.PageRequestDTO;
import iducs.springboot.yjwboard.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/boards")
public class BoardController {
    public final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("")
    public String getBoards(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("list", boardService.getList(pageRequestDTO));
        return "/boards/list";
    }

    @GetMapping("/regform")
    public String getRegform(Model model) {
        model.addAttribute("board", Board.builder().build());
        return "/boards/regform";
    }

    @PostMapping("")
    public String postBoard(@ModelAttribute("board") Board board) {
        boardService.register(board);
        return "redirect:/boards"; // get 방식으로 해당 URI 요청
    }

    @GetMapping("/{bno}")
    public String getBoard(@PathVariable("bno") Long bno, Model model) {
        model.addAttribute("dto", boardService.getById(bno));
        return "/boards/read";
    }
}
