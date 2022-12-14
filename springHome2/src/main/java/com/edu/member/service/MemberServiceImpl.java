package com.edu.member.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.edu.member.dao.MemberDao;
import com.edu.member.model.MemberDto;
import com.edu.util.FileUtils;

@Service
public class MemberServiceImpl implements MemberService{
	
	private static final Logger log 
		= LoggerFactory.getLogger(MemberServiceImpl.class);	

	@Autowired
	public MemberDao memberDao;
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Override
	public List<MemberDto> memberSelectList(String searchOption, String keyword, int start, int end) {
		// TODO Auto-generated method stub
	
		return memberDao.memberSelectList(searchOption, keyword, start, end);
	}

	@Override
	public MemberDto memberExist(String email, String password) {
		// TODO Auto-generated method stub
		return memberDao.memberExist(email, password);
	}

	@Override
	public void memberInsertOne(MemberDto memberDto,
			MultipartHttpServletRequest mulRequest) throws Exception {
		// TODO Auto-generated method stub
		
		memberDao.memberInsertOne(memberDto, mulRequest);
		
		Iterator<String> iterator = mulRequest.getFileNames();
		MultipartFile multipartFile = null;
		
		while (iterator.hasNext()) {
			multipartFile = mulRequest.getFile(iterator.next());
			
			if (multipartFile.isEmpty() == false) {
				log.debug("-------file start---------");
				
				log.debug("name : {} ", multipartFile.getName());
				log.debug("fileName : {}", multipartFile.getOriginalFilename());
				log.debug("size : {} ", multipartFile.getSize());
				
				log.debug("-------file end---------");
			}
		} //while end
		
		int parentSeq = memberDto.getNo();
		
		List<Map<String, Object>> list
			= fileUtils.parseInsertFileInfo(parentSeq
				, mulRequest);
		
		for (int i = 0; i < list.size(); i++) {
			memberDao.insertFile(list.get(i));
		}

	}

	@Override
	public Map<String, Object> memberSelectOne(int no) {
		// TODO Auto-generated method stub
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		MemberDto memberDto = memberDao.memberSelectOne(no);
		
		resultMap.put("memberDto", memberDto);
		
		List<Map<String, Object>> fileList = memberDao.fileselectList(no);
		resultMap.put("fileList", fileList);
		
		return resultMap;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int memberUpdateOne(MemberDto memberDto, MultipartHttpServletRequest multipartHttpServletRequest,
			int fileIdx) throws Exception {
		
			int resultNum = 0;
			
			try {
				resultNum = memberDao.memberUpdateOne(memberDto);
				
				int parentSeq = memberDto.getNo();
				Map<String, Object> tempFileMap = memberDao.fileSelectStoredFileName(parentSeq);
				
				List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(parentSeq, multipartHttpServletRequest);
				
				if (list.isEmpty() == false) {
					if (tempFileMap != null) {
						memberDao.fileDelete(parentSeq);
						
						//????????? ???????????? ????????? ???????????? ????????????
//						throw new Exception();
						
						//????????? ????????? ??????????????? ????????? ????????? ???????????? ??????
						fileUtils.parseUpdateFileInfo(tempFileMap);
					}
					
					for (Map<String, Object> map : list) {
						memberDao.insertFile(map);
					}
				}else if(fileIdx == -1){
					if (tempFileMap != null) {
						memberDao.fileDelete(parentSeq);
						fileUtils.parseUpdateFileInfo(tempFileMap);
					}
				}
			} catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			
			
			
		
		return resultNum;
	}
	

	@Override
	public void memberDeleteOne(int no) {
		// TODO Auto-generated method stub
		memberDao.memberDeleteOne(no);
	}

	@Override
	public int memberSelectTotalCount(String searchOption, String keyword) {
		// TODO Auto-generated method stub
		return memberDao.memberSelectTotalCount(searchOption, keyword);
	}

	@Override
	public List<MemberDto> memberSelectList2(String name, int start, int end) {
		// TODO Auto-generated method stub
		return memberDao.memberSelectList2(name, start, end);
	}

	@Override
	public int memberSelectTotalCount2(String name) {
		// TODO Auto-generated method stub
		return memberDao.memberSelectTotalCount2(name);
	}

	//???????????? ?????? ???????????????
	@Override
	public void sendEmail(MemberDto memberDto, String div) throws Exception {
		// Mail Server ??????
		String charSet = "utf-8";
		String hostSMTP = "smtp.naver.com"; //????????? ????????? smtp.naver.com
		String hostSMTPid = "yeongbin2";
		String hostSMTPpwd = "88451641aa!";

		// ????????? ?????? EMail, ??????, ??????
		String fromEmail = "yeongbin2@naver.com";
		String fromName = "?????????";
		String subject = "";
		String msg = "";

		if(div.equals("findpw")) {
			subject = "????????? ?????? ???????????? ?????????.";
			msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
			msg += "<h3 style='color: blue;'>";
			msg += memberDto.getName() + "?????? ?????? ???????????? ?????????. ??????????????? ???????????? ???????????????.</h3>";
			msg += "<p>?????? ???????????? : ";
			msg += memberDto.getPassword() + "</p></div>";
		}

		// ?????? ?????? E-Mail ??????
		String mail = memberDto.getEmail();
		try {
			HtmlEmail email = new HtmlEmail();
			email.setDebug(true);
			email.setCharset(charSet);
			email.setSSL(true);
			email.setHostName(hostSMTP);
			email.setSmtpPort(587); //????????? ????????? 587

			
			email.setAuthentication(hostSMTPid, hostSMTPpwd);
			email.setTLS(true);
			email.addTo(mail, charSet);
			email.setFrom(fromEmail, fromName, charSet);
			email.setSubject(subject);
			email.setHtmlMsg(msg);
//			Properties props = new Properties();
//	        props.put("mail.smtp.user", email);
//	        props.put("mail.smtp.host", hostSMTP);
//	        props.put("mail.smtp.port", 587);
//	        props.put("mail.smtp.starttls.enable", "true");
//	        props.put("mail.smtp.auth", "true");
//	        props.put("mail.smtp.socketFactory.port", 587);
//	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//	        props.put("mail.smtp.socketFactory.fallback", "false");

			email.send();
			
			
		} catch (Exception e) {
			System.out.println("???????????? ?????? : " + e);
		}
	}



	//??????????????????
	@Override
	public void findPw(HttpServletResponse response, MemberDto memberDto) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		MemberDto ck = memberDao.readMember(memberDto.getEmail());
		
		PrintWriter out = response.getWriter();
		// ????????? ???????????? ?????????
		if(memberDao.idCheck(memberDto.getEmail()) == 0) {
			out.print("???????????? ?????? ??????????????????.");
			out.close();
		}
		// ????????? ???????????? ?????????
		else if(!memberDto.getEmail().equals(ck.getEmail())) {
			out.print("???????????? ?????? ??????????????????.");
			out.close();
		}else {
			// ?????? ???????????? ??????
			String pw = "";
			for (int i = 0; i < 12; i++) {
				pw += (char) ((Math.random() * 26) + 97);
			}
			memberDto.setPassword(pw);
			// ???????????? ??????
			memberDao.updatePw(memberDto);
			// ???????????? ?????? ?????? ??????
			memberDto.setName(ck.getName());
			sendEmail(memberDto, "findpw");

			out.print("???????????? ?????? ??????????????? ?????????????????????.");
			out.close();
		}
	}

	

}
