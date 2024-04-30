package repository;

import java.util.List;

import domain.BoardVO;
import domain.PageingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> selectList(PageingVO pgvo);

	BoardVO detail(int bno);

	int update(BoardVO bvo);

	int delete(int bno);

	List<BoardVO> contentList(String id);

	int totalCount(PageingVO pgvo);

	String getFileName(int bno);

	int upCount(int bno);

}
