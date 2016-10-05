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
          //註冊帳號，判斷信箱是否重複
          public MemberVO GET_MAIL_STMT(String mememail);
          public Set<OrderedVO> getOrderedsByMemno(Integer memno);
          //修改會員申請狀態
          public void updateJoinCheck(MemberVO memberVO);
          //找申請隊員
          public List<MemberVO> getJoinMem(String memcheck);
          //找球隊隊員的個人影音照片
          public List<VideoPictureVO> getTeamWinerPic(Integer memno);
          //更新球員球技
          public int updateSkill(MemberVO memberVO);
          //更新會員資料
          public int updateInfo(MemberVO memberVO);
		  public int updateSkill2(MemberVO memberVO);
}