package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PageingVO;
import repository.BoardDAO;
import repository.BoardDAOImpl;

public class BoardServiceImpl implements BoardService {

	private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);
	private BoardDAO bdao;
	
	public BoardServiceImpl () {
		bdao = new BoardDAOImpl();
	}

	@Override
	public int insert(BoardVO bvo) {
		log.info("insert service in");
		return bdao.insert(bvo);
	}

	@Override
	public List<BoardVO> getList(PageingVO pgvo) {
		log.info("list service in");
		return bdao.selectList(pgvo);
	}

	@Override
	public BoardVO detail(int bno) {
		log.info("detail service in");
		return bdao.detail(bno);
	}

	@Override
	public int update(BoardVO bvo) {
		log.info("update service in");
		return bdao.update(bvo);
	}

	@Override
	public int delete(int bno) {
		//게시글을 지우기 전에 댓글을 삭제하고 글 지우기
		log.info("delete service in");
		CommentServiceImpl csv = new CommentServiceImpl();
		int isOk = csv.removeAll(bno);
		log.info("Comment removeAll >> {}",isOk);
		return bdao.delete(bno);
	}

	@Override
	public List<BoardVO> selectList(String id) {
		log.info("selectList service in");
		return bdao.contentList(id);
	}

	@Override
	public int getTotal(PageingVO pgvo) {
		log.info("getTotal service in");
		return bdao.totalCount(pgvo);
	}

	@Override
	public String getFileName(int bno) {
		log.info("getFileName service in");
		return bdao.getFileName(bno);
	}

	@Override
	public int readCount(int bno) {
		// TODO Auto-generated method stub
		return bdao.upCount(bno);
	}
	
}
