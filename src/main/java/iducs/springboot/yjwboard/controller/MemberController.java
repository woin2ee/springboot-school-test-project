package iducs.springboot.yjwboard.controller;

import iducs.springboot.yjwboard.domain.Member;
import iducs.springboot.yjwboard.domain.PageRequestDTO;
import iducs.springboot.yjwboard.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/th")
    public String getThymeleaf() {
        return "thymeleaf";
    }

    @GetMapping("/buttons")
    public String getButtons() {
        return "buttons";
    }

    @GetMapping("/cards")
    public String getCards() {
        return "cards";
    }

    @GetMapping("/utilities-color")
    public String getUtilitiesColor() {
        return "utilities-color";
    }

    @GetMapping("/utilities-border")
    public String getUtilitiesBorder() {
        return "utilities-border";
    }

    @GetMapping("/utilities-animation")
    public String getUtilitiesAnimation() {
        return "utilities-animation";
    }

    @GetMapping("/utilities-other")
    public String getUtilitiesOther() {
        return "utilities-other";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @GetMapping("/forgot-password")
    public String getForgotPassword() {
        return "forgot-password";
    }

    @GetMapping("/404")
    public String get404() {
        return "404";
    }

    @GetMapping("/blank")
    public String getBlank() {
        return "blank";
    }

    @GetMapping("/charts")
    public String getCharts() {
        return "charts";
    }

    @GetMapping("/tables")
    public String getTables() {
        return "tables";
    }
}
