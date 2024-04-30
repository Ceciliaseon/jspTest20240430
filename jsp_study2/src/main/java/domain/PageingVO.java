package domain;

public class PageingVO {
	
	private int pageNo; // 현재 페이지 번호
	private int qty; // 한페이지에 보여줄 게시글 수 (10개)
	
	private String type;
	private String keyword;
	
	public PageingVO() {
		// 페이지 번호를 클릭하기 전의 기본값 설정
		this.pageNo = 1;
		this.qty=10;
	}
	
	public PageingVO(int pageNo, int qty, String type, String keyword) {
		this.pageNo = pageNo;
		this.qty = qty;
		this.type = type;
		this.keyword = keyword;
	}
	
	public int getPageStart() { // db에서 사용되는 시작 번지 값을 설정
		// 1>0 , 2> 10, 3>20
		return (this.pageNo-1)*qty;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "PageingVO [pageNo=" + pageNo + ", qty=" + qty + ", type=" + type + ", keyword=" + keyword + "]";
	}


	
}
