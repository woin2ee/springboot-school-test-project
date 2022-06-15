package iducs.springboot.yjwboard.controller;

import iducs.springboot.yjwboard.domain.Member;
import iducs.springboot.yjwboard.domain.PageRequestDTO;
import iducs.springboot.yjwboard.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final
    MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("regform")
    public String getRegform(Model model) {
        model.addAttribute("member", Member.builder().build());
        return "/members/regform";
    }

    @PostMapping("")
    public String postMember(@ModelAttribute("member") Member member, Model model) {
        memberService.create(member);
        return "redirect:/members";
    }

    @GetMapping("")
    public String getMembers(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("list", memberService.readListBy(pageRequestDTO));
        return "members/members";
    }

    @GetMapping("/{idx}")
    public String getMember(@PathVariable("idx") Long seq, Model model) {
        Member member = memberService.readById(seq);
        model.addAttribute("member", member);
        return "/members/member";
    }

    @GetMapping("/{idx}/upform")
    public String getUpform(@PathVariable("idx") Long seq, Model model) {
        Member member = memberService.readById(seq);
        model.addAttribute("member", member);
        return "/members/upform";
    }

    @PutMapping("{idx}")
    public String putMember(@ModelAttribute("member") Member member, Model model) {
        memberService.update(member);
        model.addAttribute(member);
        return "/members/member";
    }

    @GetMapping("/{idx}/delform")
    public String getDelform(@PathVariable("idx") Long seq, Model model) {
        Member member = memberService.readById(seq);
        model.addAttribute(member);
        return "/members/delform";
    }

    @DeleteMapping("{idx}")
    public String delMember(@ModelAttribute("member") Member member, Model model) {
        memberService.delete(member);
        return "redirect:/members";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("member", Member.builder().build());
        return "/members/login";
    }

    @PutMapping("/login")
    public String postLogin(@ModelAttribute("member") Member member, HttpServletRequest request) {
        Member dto = null;
        if ((dto = memberService.loginByEmail(member)) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("login", dto);

            if (dto.getId().contains("admin")) {
                session.setAttribute("isAdmin", true);
            }

            return "redirect:/home/";
        } else {
            return "/members/loginfail";
        }
    }
}
