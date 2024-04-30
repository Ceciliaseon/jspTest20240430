package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import orm.DatabaseBuilder;
import service.CommentServiceImpl;

public class CommentDAOImpl implements CommentDAO {
	
	private static final Logger log = LoggerFactory.getLogger(CommentDAOImpl.class);	
	private SqlSession sql;
	
	public CommentDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int insert(CommentVO cvo) {
		log.info("insert dao in!");
		int isOk = sql.insert("CommentMapper.post", cvo);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public List<CommentVO> getList(int bno) {
		log.info("list dao in!");
		return sql.selectList("CommentMapper.list", bno);
	}

	@Override
	public int delete(int cno) {
		log.info("delete dao in!");
		int isOk = sql.delete("CommentMapper.del", cno);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int update(CommentVO cvo) {
		log.info("update dao in!");
		int isOk = sql.update("CommentMapper.up", cvo);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int removeAll(int bno) {
		int isOk = sql.delete("CommentMapper.all", bno);
		if(isOk >= 0) {
			sql.commit();
		}
		return isOk;
	}
}
