package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PageingVO;
import orm.DatabaseBuilder;

public class BoardDAOImpl implements BoardDAO {
	
	private static final Logger log = LoggerFactory.getLogger(BoardDAOImpl.class);
	
	private SqlSession sql;
	public BoardDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}
	@Override
	public int insert(BoardVO bvo) {
		log.info("insert dao in");
		int isOk = sql.insert("BoardMapper.add", bvo);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}
	@Override
	public List<BoardVO> selectList(PageingVO pgvo) {
		log.info("list dao in");
		return sql.selectList("BoardMapper.list", pgvo);
	}
	
	@Override
	public BoardVO detail(int bno) {
		log.info("detail dao in");
		return sql.selectOne("BoardMapper.one", bno);
	}
	
	@Override
	public int update(BoardVO bvo) {
		log.info("update dao in");
		int isOk = sql.update("BoardMapper.update", bvo);
		if (isOk > 0) {
			sql.commit();
		}
		return isOk;
	}
	@Override
	public int delete(int bno) {
		log.info("delete dao in");
		int isOk = sql.delete("BoardMapper.del", bno);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public List<BoardVO> contentList(String id) {
		log.info("contentList dao in");
		return sql.selectList("BoardMapper.writer", id);
	}
	@Override
	public int totalCount(PageingVO pgvo) {
		log.info("totalCount dao in");
		return sql.selectOne("BoardMapper.cnt", pgvo);
	}
	@Override
	public String getFileName(int bno) {
		log.info("getFileName dao in");
		return sql.selectOne("BoardMapper.getFileName", bno);
	}
	@Override
	public int upCount(int bno) {
		int isOk = sql.update("BoardMapper.readCount", bno);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

}
