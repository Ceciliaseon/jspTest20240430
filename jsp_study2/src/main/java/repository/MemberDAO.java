package repository;

import java.util.List;

import domain.MemberVO;

public interface MemberDAO {

	int insert(MemberVO mvo);

	MemberVO login(MemberVO mvo);

	int logout(String id);

	int update(MemberVO mvo);

	List<MemberVO> list();

	int delete(String id);

}
