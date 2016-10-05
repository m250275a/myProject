package com.member.model;

import java.util.*;

import com.ordered.model.OrderedVO;
import com.memteam.model.MemteamVO;
import com.videopicture.model.VideoPictureVO;

public interface MemberDAO_interface {
          public int insert(MemberVO memberVO);
          public int update(MemberVO memberVO);
          public void delete(Integer memno);
          public MemberVO findByPrimaryKey(Integer memno);
          public List<MemberVO> getAll();
          public List<MemberVO> getAllByName(String memname);
          public List<MemberVO> getAll(Map<String, String[]> map);
          //���U�b���A�P�_�H�c�O�_����
          public MemberVO GET_MAIL_STMT(String mememail);
          public Set<OrderedVO> getOrderedsByMemno(Integer memno);
          //�ק�|���ӽЪ��A
          public void updateJoinCheck(MemberVO memberVO);
          //��ӽж���
          public List<MemberVO> getJoinMem(String memcheck);
          //��y���������ӤH�v���Ӥ�
          public List<VideoPictureVO> getTeamWinerPic(Integer memno);
          //��s�y���y��
          public int updateSkill(MemberVO memberVO);
          //��s�|�����
          public int updateInfo(MemberVO memberVO);
		  public int updateSkill2(MemberVO memberVO);
}