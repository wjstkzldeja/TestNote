package org.kdea.board;

public class BoardVO {
	private int boardNum;
	private String boardid;
	private String boardpwd;
	private String boardtitle;
	private String boardcontent;
	private String email;
	private java.sql.Date boardhiredate;
	private int commentNum;
	private int pageNum;
	private int totalRows;
	private int active;
	
	public BoardVO(){
		
	}
	//积己磊 矫累
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

	public String getBoardid() {
		return boardid;
	}
	public void setBoardid(String boardid) {
		this.boardid = boardid;
	}
	public String getBoardpwd() {
		return boardpwd;
	}
	public void setBoardpwd(String boardpwd) {
		this.boardpwd = boardpwd;
	}
	public String getBoardtitle() {
		return boardtitle;
	}
	public void setBoardtitle(String boardtitle) {
		this.boardtitle = boardtitle;
	}
	public String getBoardcontent() {
		return boardcontent;
	}
	public void setBoardcontent(String boardcontent) {
		this.boardcontent = boardcontent;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public java.sql.Date getBoardhiredate() {
		return boardhiredate;
	}
	public void setBoardhiredate(java.sql.Date boardhiredate) {
		this.boardhiredate = boardhiredate;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	
	//积己磊 场
	
}
