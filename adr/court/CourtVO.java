package adr.court;

public class CourtVO implements java.io.Serializable {
    private Integer courtno;
    private Double courtlat;
    private Double courtlng;
    private String courtloc;
    private String courtname;
    private String courtdesc;
//    private byte[] courtimg;

//    public CourtVO(Integer courtno, Double courtlat, Double courtlng, String courtloc, String courtname, String courtdesc, byte[] courtimg) {
    public CourtVO(Integer courtno, Double courtlat, Double courtlng, String courtloc, String courtname, String courtdesc) {
        this.courtno = courtno;
        this.courtlat = courtlat;
        this.courtlng = courtlng;
        this.courtloc = courtloc;
        this.courtname = courtname;
        this.courtdesc = courtdesc;
//        this.courtimg = courtimg;
    }

    public Integer getCourtno() {
        return courtno;
    }
    public void setCourtno(Integer courtno) {
        this.courtno = courtno;
    }
    public Double getCourtlat() {
        return courtlat;
    }
    public void setCourtlat(Double courtlat) {
        this.courtlat = courtlat;
    }
    public Double getCourtlng() {
        return courtlng;
    }
    public void setCourtlng(Double courtlng) {
        this.courtlng = courtlng;
    }
    public String getCourtloc() {
        return courtloc;
    }
    public void setCourtloc(String courtloc) {
        this.courtloc = courtloc;
    }
    public String getCourtname() {
        return courtname;
    }
    public void setCourtname(String courtname) {
        this.courtname = courtname;
    }
    public String getCourtdesc() {
        return courtdesc;
    }
    public void setCourtdesc(String courtdesc) {
        this.courtdesc = courtdesc;
    }
//    public byte[] getCourtimg() {
//        return courtimg;
//    }
//    public void setCourtimg(byte[] courtimg) {
//        this.courtimg = courtimg;
//    }


}