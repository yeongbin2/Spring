package com.edu.member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.util.ParameterMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.edu.member.model.MemberDto;

@Repository
public class MemberDaoImpl implements MemberDao{

	@Autowired
	SqlSessionTemplate sqlSession;
	
	//mapper의 namespace를 변수로 생성해서 사용하자
	String namespace = "com.edu.member.";
	
	@Override
	public List<MemberDto> memberSelectList(String searchOption, String keyword, int start, int end) {
		
		//시작페이지와 끝페이지를 HashMap에 담아서 넘겨줌
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		map.put("searchOption", searchOption);
		return sqlSession.selectList(namespace + "memberSelectList", map);
	}

	@Override
	public MemberDto memberExist(String email, String password) {
		
		//이메일과 패스워드 값을 넘기기 위해서(값을 2개이상 전달시 HashMap 사용)
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("email", email);
		paramMap.put("pwd", password);
 
		return sqlSession.selectOne("com.edu.member.memberExist", paramMap);
	}

	@Override
	public int memberInsertOne(MemberDto memberDto, MultipartHttpServletRequest mulRequest) {
		// TODO Auto-generated method stub
		return sqlSession.insert("com.edu.member.memberInsertOne", memberDto);
	}

	@Override
	public MemberDto memberSelectOne(int no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.edu.member.memberSelectOne", no);
	}

	@Override
	public int memberUpdateOne(MemberDto memberDto) {
		// TODO Auto-generated method stub
		return sqlSession.update(namespace + "memberUpdateOne", memberDto);
	}

	@Override
	public void memberDeleteOne(int no) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + "memberDeleteOne", no);
	}

	@Override
	public int memberSelectTotalCount(String searchOption, String keyword) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		
		return (int)sqlSession.selectOne(
				namespace + "memberSelectTotalCount", map);
	}

	@Override
	public void insertFile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		sqlSession.insert(namespace + "insertFile", map);
	}

	@Override
	public List<Map<String, Object>> fileselectList(int no) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "fileselectList", no);
	}

	@Override
	public Map<String, Object> fileSelectStoredFileName(int parentSeq) {
		// TODO Auto-generated method stub
		
		return sqlSession.selectOne(namespace + "fileSelectStoredFileName", parentSeq);
	}

	@Override
	public int fileDelete(int parentSeq) {
		// TODO Auto-generated method stub
		return sqlSession.delete(namespace + "fileDelete", parentSeq);
	}

	@Override
	public List<MemberDto> memberSelectList2(String name, int start, int end) {
		// TODO Auto-generated method stub
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
 
		paramMap.put("start", start);
		paramMap.put("end", end);
		paramMap.put("name", name);
		return sqlSession.selectList(namespace + "memberSelectList2", paramMap);
		
		
		
	}

	@Override
	public int memberSelectTotalCount2(String name) {
		// TODO Auto-generated method stub
		return (int)sqlSession.selectOne(
				namespace + "memberSelectTotalCount2", name);
	}

	@Override
	public int updatePw(MemberDto memberDto) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update(namespace+ "updatePw", memberDto);
	}


	@Override
	public int idCheck(String email) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+ "idCheck", email);
	}
	
	@Override
	public MemberDto readMember(String email) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.edu.member.readMember", email);
	}
	

	
}
