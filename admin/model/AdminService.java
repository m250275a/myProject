package com.admin.model;

import java.util.List;

public class AdminService {
	
	private AdminDAO_interface dao;

	public AdminService() {
		dao = new AdminDAO();
	}

	public AdminVO addAdmin(String adminname, String adminvarname, String adminid, 
			String adminphone, String adminaddr, String adminpsw, String adminemail, String adminlevel) {

		AdminVO adminVO = new AdminVO();

		adminVO.setAdminname(adminname);
		adminVO.setAdminvarname(adminvarname);
		adminVO.setAdminid(adminid);
		adminVO.setAdminphone(adminphone);
		adminVO.setAdminaddr(adminaddr);
		adminVO.setAdminpsw(adminpsw);
		adminVO.setAdminemail(adminemail);
		adminVO.setAdminlevel(adminlevel);
		dao.insert(adminVO);

		return adminVO;
	}

	public AdminVO updateAdmin(Integer adminno, String adminname, String adminvarname, String adminid, 
			String adminphone, String adminaddr, String adminpsw, String adminemail, String adminlevel) {

		AdminVO adminVO = new AdminVO();
		
    
		adminVO.setAdminno(adminno);
		adminVO.setAdminname(adminname);
		adminVO.setAdminvarname(adminvarname);
		adminVO.setAdminid(adminid);
		adminVO.setAdminphone(adminphone);
		adminVO.setAdminaddr(adminaddr);
		adminVO.setAdminpsw(adminpsw);
		adminVO.setAdminemail(adminemail);
		adminVO.setAdminlevel(adminlevel);		
		dao.update(adminVO);

		return adminVO;
	}

	public void deleteAdmin(Integer adminno) {
		dao.delete(adminno);
	}

	public AdminVO getOneAdmin(Integer adminno) {
		return dao.findByPrimaryKey(adminno);
	}

	public List<AdminVO> getAll() {
		return dao.getAll();
	}

}
