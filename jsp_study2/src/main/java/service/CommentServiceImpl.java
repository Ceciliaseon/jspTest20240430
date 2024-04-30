package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import repository.CommentDAO;
import repository.CommentDAOImpl;


public class CommentServiceImpl implements CommentService {
	
	private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
	
	private CommentDAO cdao;
	
	public CommentServiceImpl() {
		cdao = new CommentDAOImpl();
	}

	@Override
	public int post(CommentVO cvo) {
		log.info("post service in!");
		return cdao.insert(cvo);
	}

	@Override
	public List<CommentVO> getList(int bno) {
		log.info("list service in!");
		return cdao.getList(bno);
	}

	@Override
	public int remove(int cno) {
		log.info("remove service in!");
		return cdao.delete(cno);
	}

	@Override
	public int update(CommentVO cvo) {
		log.info("update service in!");
		return cdao.update(cvo);
	}

	public int removeAll(int bno) {
		// TODO Auto-generated method stub
		return cdao.removeAll(bno);
	}
}
