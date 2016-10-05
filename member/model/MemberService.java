package com.member.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.member.model.*;
import com.ordered.model.*;
import com.videopicture.model.VideoPictureVO;

public class MemberService {
	private MemberDAO_interface dao;

	public MemberService() {
		dao = new MemberDAO();
	}

	public MemberVO addMember(MemberVO memberVO) {

		dao.insert(memberVO);

		return memberVO;
	}

	public MemberVO updateMember(MemberVO memberVO) {
		dao.update(memberVO);

		return memberVO;
	}

	public void deleteMember(Integer memno) {
		dao.delete(memno);
	}

	public MemberVO getOneMember(Integer memno) {
		return dao.findByPrimaryKey(memno);
	}

	public List<MemberVO> getAll() {
		return dao.getAll();
	}

	public List<MemberVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	 public List<MemberVO> getAllByName(String memname){
	    return dao.getAllByName(memname);
	 }
	public MemberVO GET_MAIL_STMT(String mememail) {
		return dao.GET_MAIL_STMT(mememail);
	}
	public Set<OrderedVO> getOrderedsByMemno(Integer memno) {
		return dao.getOrderedsByMemno(memno);
	}
	
	//�ק�|���ӽвy�����A
		public MemberVO updateJoinCheck(Integer memno, String memcheck){
			MemberVO memberVO = new MemberVO();
			memberVO.setMemno(memno);
			memberVO.setMemcheck(memcheck);
			dao.updateJoinCheck(memberVO);
			return memberVO;	
		}
		//��ӽж���
		public List<MemberVO> getJoinMem(String memcheck){
			return dao.getJoinMem(memcheck);
		}
		//�ڵ��|���J��
		public void deleteJoinMem(Integer memno){
			dao.delete(memno);
		}
		 //��y���������ӤH�v���Ӥ�
		public List<VideoPictureVO> getTeamWinPic(Integer memno){
			return dao.getTeamWinerPic(memno);
		}
		public MemberVO updateSkill(MemberVO memberVO) {
			dao.updateSkill(memberVO);

			return memberVO;
		}
		
		public MemberVO updateSkill2(MemberVO memberVO) {
			dao.updateSkill(memberVO);

			return memberVO;
		}
		public MemberVO updateInfo(MemberVO memberVO) {
			dao.updateInfo(memberVO);

			return memberVO;
		}
}
