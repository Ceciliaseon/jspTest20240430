package service;

import java.util.List;

import domain.BoardVO;
import domain.PageingVO;

public interface BoardService {

	int insert(BoardVO bvo);

	List<BoardVO> getList(PageingVO pgvo);

	BoardVO detail(int bno);

	int update(BoardVO bvo);

	int delete(int bno);

	List<BoardVO> selectList(String id);

	int getTotal(PageingVO pgvo);

	String getFileName(int bno);

	int readCount(int bno);

}
