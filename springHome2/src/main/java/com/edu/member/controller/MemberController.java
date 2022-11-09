package com.edu.member.controller;

import java.awt.BufferCapabilities.FlipContents;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.edu.member.model.MemberDto;
import com.edu.member.service.MemberService;
import com.edu.util.Paging;

// 어노테이션 드리븐
@Controller
public class MemberController {

	private static final Logger logger 
		= LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	//사용자와 상호작용하는 화면 (GET방식) - 로그인
	//스프링에서 HttpSession session 세션객체를 만들어놔서 사용할 수 있음, Model은 req를 대체함
	@RequestMapping(value="/auth/login.do", method = RequestMethod.GET)
	public String login(HttpSession session, Model model) {
	
		//logger.info, logger.trace, logger.debug, ~~~ 아래는 info사용한 예시
		logger.info("Welcome MemberController login!");
		
		return "auth/LoginForm";
	}
	
	//실제 로그인을 확인하는 작업(매개변수 email, password를 통해 DB조회)
	@RequestMapping(value="/auth/loginCtr.do", method = RequestMethod.POST)
	public String loginCtr(String email, String password
			, HttpSession session, Model model) {	
		logger.info("Welcome MemberController loginCtr! " + email + 
				", " + password);
		
		MemberDto memberDto = memberService.memberExist(email, password);
		
		String viewUrl = "";
		if(memberDto != null) {
			session.setAttribute("member", memberDto);
			
			viewUrl =  "redirect:/member/list.do";
		}else {
			viewUrl = "/auth/LoginFail";
		}
		
		return viewUrl;
	}
	
	//로그아웃
	@RequestMapping(value="/auth/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session) {
			
		logger.info("Goodbye MemberController logout!");
		
		session.invalidate();
		
		return "redirect:/member/list.do";
	}	
	
	//페이징 작업이 추가됨 (RequestMethod.GET, POST를 둘다 수행)
	// 메소드를 method = {RequestMethod.GET, RequestMethod.POST} 블록{}처리를 통해 GET,POST방식 둘다 요청을 수행 할 수 있다
	// 매개변수(parameter)에 @RequestParam를 선언, defaultValue를 선언하면 curPage를 호출하지 않아도 알아서 default값 1을 넣어준다
	// 즉 로직상으로 회원목록에 들어오면 무조건 첫번째 페이지(curPage = 1)를 보여준다
	@RequestMapping(value = "/member/list.do"
			, method = {RequestMethod.GET, RequestMethod.POST})
	public String memberList(@RequestParam(defaultValue = "1") int curPage, Model model,
			@RequestParam(defaultValue = "all")String searchOption
		  , @RequestParam(defaultValue = "")String keyword)  {

		//logger에 {}안에 한개의 값(curPage의 값)이 들어간다  형식 : {} , 들어갈 변수 또는 값 (여기선 {}, curpage)
		logger.info("Welcome MemberController memberList! curPage: {}" + ", searchOption: {}"
			, curPage, searchOption);
		
		logger.info("keyword: {}",keyword);
		
		if ("name".equals(searchOption)) {
			searchOption = "mname";
		};
		
		int totalCount = memberService.memberSelectTotalCount(searchOption, keyword);
		
		
		
		logger.info("totalCount: {}", totalCount);
		
		Paging memberPaging = new Paging(totalCount, curPage);
		
		int start = memberPaging.getPageBegin();
		int end = memberPaging.getPageEnd();
		
		List<MemberDto> memberList =
				memberService.memberSelectList(searchOption, keyword, start, end);
		//sql 페이징 쿼리실행결과 + 토탈카운트를 담아서 멤버리스트와 같이 모델에 담아준다
		//map을 활용하면 다양한 데이터를 쉽게 객체를 만들 수 있다
		//Map의 value타입이 Object인 이유 -> 스프링은 객체지향 프로그래밍 
		Map<String, Object> pagingMap = 
				new HashMap<String, Object>();
		
		
		//Map에다가 totalCount, memberPaging을 key로해서 담고
		pagingMap.put("totalCount", totalCount);
		pagingMap.put("memberPaging", memberPaging);
		
		Map<String, Object> searchMap = 
				new HashMap<String, Object>();
		
		searchMap.put("searchOption", searchOption);
		searchMap.put("keyword", keyword);
		
		logger.info("curPage: {}", curPage);
		logger.info("curBlock: {}", memberPaging.getCurBlock());
		
		//Map을 pagingMap 키로 model에 담아서
		//MemberListView에서 ${pagingMap.memberPaging.blockBegin} pagingMap의 인스턴스를 EL태그로 사용한다
		model.addAttribute("memberList", memberList);
		model.addAttribute("pagingMap", pagingMap);
		model.addAttribute("searchMap", searchMap);
		
		return "member/MemberListView";
	}
	
	
	//상세보기 (선택시 상세정보를 보여줌 readOnly 페이지)
	@RequestMapping(value="/member/one.do", method = RequestMethod.GET)
	public String memberOne(int no, Model model
			, @RequestParam(defaultValue = "1") int curPage
			, @RequestParam(defaultValue = "all")String searchOption
			, @RequestParam(defaultValue = "")String keyword) {
		logger.debug("Welcome MemberController memberOne!{}" , no);
		
		Map<String, Object> map = memberService.memberSelectOne(no);
		MemberDto memberDto = (MemberDto) map.get("memberDto");
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchOption", searchOption);
		searchMap.put("keyword", keyword);
		searchMap.put("curPage", curPage);
		
		List<Map<String, Object>> fileList = (List<Map<String, Object>>) map.get("fileList");
		
		model.addAttribute("memberDto", memberDto);
		model.addAttribute("fileList", fileList);
		model.addAttribute("searchMap", searchMap);
		
		return "member/MemberOneView";
	}		
	
	//멤버추가(멤버폼 화면)
	@RequestMapping(value="/member/add.do", method = RequestMethod.GET)
	public String add(Model model) {
		logger.debug("Welcome MemberController memberAdd!");
		
		return "member/MemberForm";
	}	
	
	//멤버추가 (실제로 멤버추가되는 로직) ~~Ctr -> 작업
	//10.17 - 파일업로드(이미지) 기능 추가 MultipartHttpServletRequest mulRequest 파라미터로 추가
	@RequestMapping(value="/member/addCtr.do", method = RequestMethod.POST)
	public String memberAdd(MemberDto memberDto,
			MultipartHttpServletRequest mulRequest, Model model) {	
		logger.trace("Welcome MemberController memberAdd! 신규등록 처리!!! "
			+ memberDto);
		
		try {
			memberService.memberInsertOne(memberDto, mulRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("오랜만에 예외 처리 한다");
			System.out.println("파일 문제 발생");
			e.printStackTrace();
		}
		
		return "redirect:/member/list.do";
	}
	
	
	//회원수정 화면으로 (Go to MemberUpdateForm // {} 안에 no가 들어감)
	//requestMethod명시 생략시 디폴트로 GET이 들어옴
	@RequestMapping(value="/member/update.do")
	public String memberUpdate(int no, Model model
			, @RequestParam(defaultValue = "1") int curPage
			, @RequestParam(defaultValue = "all")String searchOption
			, @RequestParam(defaultValue = "")String keyword) {
		logger.debug("Welcome memberUpdate enter {}", no);
		
		Map<String, Object> map = memberService.memberSelectOne(no);
		Map<String, Object> searchMap = new HashMap<String, Object>();
		Map<String, Object> pagingMap = new HashMap<String, Object>();
		
		searchMap.put("searchOption", searchOption);
		searchMap.put("keyword", keyword);
		pagingMap.put("curPage", curPage);
		
		
		MemberDto memberDto = (MemberDto) map.get("memberDto");
		List<Map<String, Object>> fileList = (List<Map<String, Object>>) map.get("fileList");
		
		model.addAttribute("memberDto", memberDto);
		model.addAttribute("fileList", fileList);
		model.addAttribute("searchMap", searchMap);
		model.addAttribute("curPage", curPage);
		
		return "member/MemberUpdateForm";
	}	
	
	//수정시 바로바로 적용되게 바꾸기(세션?)
	@RequestMapping(value = "/member/updateCtr.do", method = RequestMethod.POST)
	   public String memberUpdateCtr(HttpSession session, MemberDto memberDto, Model model,
			   @RequestParam(value= "fileIex", defaultValue = "-1")
	   int fileIdx, MultipartHttpServletRequest multipartHttpServletRequest)  {
	                     // email.password 네임값을 가져옴(@RequestMapping의 힘)
	      logger.info("Welcome MemberController memberUpdateCtr!" + memberDto);
	      
	      try {
	    	  memberService.memberUpdateOne(memberDto, multipartHttpServletRequest, fileIdx);
		} catch (Exception e) {
			e.printStackTrace();
		}
	      
	         MemberDto sessionMemberDto =
	               (MemberDto)session.getAttribute("member");
	         
	         if (sessionMemberDto != null) {
	            if (sessionMemberDto.getNo() == memberDto.getNo()) {
	            
	            	MemberDto newMemberDto = new MemberDto();
	               
	               newMemberDto.setNo(memberDto.getNo());
	               newMemberDto.setEmail(memberDto.getEmail());
	               newMemberDto.setName(memberDto.getName());
	               
	               session.removeAttribute("member");
	               
	               session.setAttribute("member", newMemberDto);
	            }
	         }
	    
	   
	      
	      
	      return "common/successPage";
	   }
	
	//삭제(삭제시 세션없애는 처리?)
	@RequestMapping(value="/member/deleteCtr.do", method = RequestMethod.GET)
	public String memberDelete(int no, HttpSession session, Model model) {
		logger.info("Welcome MemberController memberDeleteCtr! " + no);
		
		memberService.memberDeleteOne(no);
		MemberDto memberDto2 = (MemberDto) session.getAttribute("member");
		
		
		
		if(memberDto2.getNo() == no) {
			session.invalidate();
		}
		
		return "redirect:/member/list.do";
	}
	
	@RequestMapping(value = "/findpw.do", method = RequestMethod.GET)
	public void findPwGET() throws Exception{
	}

	@RequestMapping(value = "/findpw.do", method = RequestMethod.POST)
	public void findPwPOST(@ModelAttribute MemberDto memberDto, HttpServletResponse response) throws Exception{
		memberService.findPw(response, memberDto);
	}
}
