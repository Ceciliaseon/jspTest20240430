package domain;

public class CommentVO {
	
	private int cno; // 댓글번호
	private int bno; // 게시글번호
	private String writer; // 작성자
	private String content; //댓글
	private String regdate; // 작성일
	
	public CommentVO() {}
	
	//post : bno, writer, content
	public CommentVO(int bno, String writer, String content) {
		this.bno = bno;
		this.writer = writer;
		this.content = content;
	}
	
	//modify : cno, content
	public CommentVO(int cno, String content) {
		this.cno = cno;
		this.content = content;
	}
	//all
	public CommentVO(int cno, int bno, String writer, String content, String regdate) {
		this(bno, writer, content);
		this.cno = cno;
		this.regdate = regdate;	
	}

	public int getCno() {
		return cno;
	}

	public void setCno(int cno) {
		this.cno = cno;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "CommentVO [" + cno + ", bno=" + bno + ", writer=" + writer + ", content=" + content + ", regdate="
				+ regdate + "]";
	}

	
}
