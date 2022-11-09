package com.edu.util;

import java.io.Serializable;

public class Paging implements Serializable{
	
	// 페이지 == 화면

	// 페이지당 게시물 수 (페이지(화면) 1개에 게시물 PAGE_SCALE 개가 나옴)
	public static final int PAGE_SCALE = 10;
	
	// 화면당 블럭 수 (한 화면에 블럭이 3개까지 나옴) 
	public static final int BLOCK_SCALE = 10;
	
	// 현재 페이지 위치
	private int curPage;

	// 전체 페이지 갯수
	private int totPage;
	// 전체 페이지 블록 갯수(그러니까 블럭이 5개씩 있으면 총 3개의 블럭페이지가 생긴다?) 즉 1,2,3,4,5 1개 / 6,7,8,9,10 2개 / 11~ 3개
	private int totBlock;
	// 현재 페이지 블록
	private int curBlock;
	// 이전 페이지 블록
	private int prevBlock;
	// 다음 페이지 블록
	private int nextBlock;
	
	//#{start}
	private int pageBegin;
	//#{end}
	private int pageEnd;
	
	//[해당 페이지의 블럭 시작점] 한 페이지에 블럭갯수가 5개면 시작점이 블럭비긴 종료점이 블럭엔드
	private int blockBegin;
	//[해당 페이지의 블럭 종료점]
	private int blockEnd;
	
	// Paging(레코드 갯수, 현재 페이지 번호)
	public Paging(int count, int curPage) {
		curBlock = 1;
		this.curPage = curPage;
		
		setTotPage(count);
		setPageRange();
		setTotBlock();
		setBlockRange();
	}
	
	public void setBlockRange() {
		curBlock = (int)Math.ceil((curPage - 1) / BLOCK_SCALE) + 1;
		System.out.println(curPage + "현재페이지" + curBlock + "현재 블럭");
		
		blockBegin = (curBlock - 1) * BLOCK_SCALE + 1;
		
		blockEnd = blockBegin + BLOCK_SCALE - 1;
		
		if(blockEnd > totPage) {
			blockEnd = totPage;
		}
		
		prevBlock = (curPage == 1) ? 1 : (curBlock - 1) * BLOCK_SCALE;
		
		nextBlock = curBlock > totBlock ? (curBlock * BLOCK_SCALE)
				: (curBlock * BLOCK_SCALE) + 1;
		
		if(prevBlock <= 0) {
			prevBlock = 1;
		}
		
		if(nextBlock >= totPage) {
			nextBlock = totPage;
		}
	}
	
	public void setPageRange() {
		pageBegin = (curPage - 1) * PAGE_SCALE + 1;
		
		pageEnd = pageBegin + PAGE_SCALE - 1;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotPage() {
		return totPage;
	}

	public void setTotPage(int count) {
		
		this.totPage = (int)Math.ceil(count * 1.0 / PAGE_SCALE);
	}

	public int getTotBlock() {
		return totBlock;
	}

	public void setTotBlock() {
		totBlock = (int)Math.ceil((double)totPage / (double)BLOCK_SCALE);
	}

	public int getCurBlock() {
		return curBlock;
	}

	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}

	public int getPrevBlock() {
		return prevBlock;
	}

	public void setPrevBlock(int prevBlock) {
		this.prevBlock = prevBlock;
	}

	public int getNextBlock() {
		return nextBlock;
	}

	public void setNextBlock(int nextBlock) {
		this.nextBlock = nextBlock;
	}

	public int getPageBegin() {
		return pageBegin;
	}

	public void setPageBegin(int pageBegin) {
		this.pageBegin = pageBegin;
	}

	public int getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}

	public int getBlockBegin() {
		return blockBegin;
	}

	public void setBlockBegin(int blockBegin) {
		this.blockBegin = blockBegin;
	}

	public int getBlockEnd() {
		return blockEnd;
	}

	public void setBlockEnd(int blockEnd) {
		this.blockEnd = blockEnd;
	}
	
	
}
