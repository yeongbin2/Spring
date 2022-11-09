package com.edu.member.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.edu.member.model.MemberDto;

public interface MemberService {

	public List<MemberDto> memberSelectList(String searchOption, String keyword, int start, int end);

	public MemberDto memberExist(String email, String password);
	
	public void memberInsertOne(MemberDto memberDto, MultipartHttpServletRequest mulRequest) throws Exception;

	public Map<String, Object> memberSelectOne(int no);

	public int memberUpdateOne(MemberDto memberDto, MultipartHttpServletRequest multipartHttpServletRequest, int fileIdx) throws Exception;

	public void memberDeleteOne(int no);

	public int memberSelectTotalCount(String searchOption, String keyword);

	public List<MemberDto> memberSelectList2(String name, int start, int end);

	public int memberSelectTotalCount2(String name);

	//이메일발송
	public void sendEmail(MemberDto memberDto, String div) throws Exception;

	//비밀번호찾기
	public void findPw(HttpServletResponse resp, MemberDto memberDto) throws Exception;

	
}
