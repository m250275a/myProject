package adr.court;

public class DotVO implements java.io.Serializable {
//  private String dotSerialID;
  private Integer memno;
  private String memname;
  private String mememail;
  private Double dotLat;
  private Double dotLng;
  private boolean inField;
  
  public DotVO(Integer memno, String memname, String mememail, Double dotLat, Double dotLng, boolean inField) {
      this.memno = memno;
      this.memname = memname;
      this.mememail = mememail;
      this.dotLat = dotLat;
      this.dotLng = dotLng;
      this.inField = inField;
  }

//  public DotVO(String dotSerialID, Double dotLat, Double dotLng, boolean inField) {
//      this.dotSerialID = dotSerialID;
//      this.dotLat = dotLat;
//      this.dotLng = dotLng;
//      this.inField = inField;
//  }

//  public String getDotSerialID() {
//      return dotSerialID;
//  }
//
//  public void setDotSerialID(String dotSerialID) {
//      this.dotSerialID = dotSerialID;
//  }


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

  public Double getDotLat() {
      return dotLat;
  }

  public void setDotLat(Double dotLat) {
      this.dotLat = dotLat;
  }

  public Double getDotLng() {
      return dotLng;
  }

  public void setDotLng(Double dotLng) {
      this.dotLng = dotLng;
  }

  public boolean getInField() {
      return inField;
  }

  public void setInField(boolean inField) {
      this.inField = inField;
  }

//    @Override
//    public boolean equals(Object that) {
//        if(that instanceof DotVO) {
//            DotVO d = (DotVO) that;
//            return this.dotSerialID.equals(d.dotSerialID);
//        }
//        return false;
//    }
    
    @Override
    public boolean equals(Object that) {
    	if(that instanceof DotVO) {
    		DotVO d = (DotVO) that;
//    		return this.memno.equals(d.memno); //memno世界唯一。
    		return this.memno.intValue() == d.memno.intValue(); //memno世界唯一。
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
        return 999; //回傳的值隨便打，重點變成比較equals.
    }
    
}
