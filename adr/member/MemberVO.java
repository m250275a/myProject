package adr.member;

@SuppressWarnings("serial")
public class MemberVO implements java.io.Serializable {
    private Integer memno;
    private String memname;
    private String mememail;
    private String mempassword;
    
	public MemberVO(Integer memno, String memname, String mememail, String mempassword) {
		super();
		this.memno = memno;
		this.memname = memname;
		this.mememail = mememail;
		this.mempassword = mempassword;
	}

	public Integer getMemno() {
		return memno;
	}

	public void setMemno(Integer memno) {
		this.memno = memno;
	}

	public String getMemname() {
		return memname;
	}

	public void setMemname(String memname) {
		this.memname = memname;
	}

	public String getMememail() {
		return mememail;
	}

	public void setMememail(String mememail) {
		this.mememail = mememail;
	}

	public String getMempassword() {
		return mempassword;
	}

	public void setMempassword(String mempassword) {
		this.mempassword = mempassword;
	}
    
}
