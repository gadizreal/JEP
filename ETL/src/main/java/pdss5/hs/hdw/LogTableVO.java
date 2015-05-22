package pdss5.hs.hdw;

public class LogTableVO {

	private Integer logNo	;
	private String prgrmId	;
	private String elpsdTime;
	private String errCntnt	;
	private String exeEndTmstmp	;
	private String exeGrpId	;
	private String exeStrtTmstmp;
	private String exeSttsVal	;
	private String exeTypNm	;
	private Integer failCnt	;
	private Integer rowCnt	;	
	private String sqlCondtnTxt	;
	private String srcTblId	;
	private String srcTblNm	;
	private String srcTblOwnrId	;
	private String tgtTblId	;
	private String tgtTblNm;
	private String upTmstmp	;
	
	public LogTableVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LogTableVO(String prgrmId, String elpsdTime, String errCntnt,
			String exeEndTmstmp, String exeGrpId, String exeStrtTmstmp,
			String exeSttsVal, String exeTypNm, Integer failCnt,
			Integer rowCnt, String sqlCondtnTxt, String srcTblId,
			String srcTblNm, String srcTblOwnrId, String tgtTblId,
			String tgtTblNm, String upTmstmp) {
		super();
		this.prgrmId = prgrmId;
		this.elpsdTime = elpsdTime;
		this.errCntnt = errCntnt;
		this.exeEndTmstmp = exeEndTmstmp;
		this.exeGrpId = exeGrpId;
		this.exeStrtTmstmp = exeStrtTmstmp;
		this.exeSttsVal = exeSttsVal;
		this.exeTypNm = exeTypNm;
		this.failCnt = failCnt;
		this.rowCnt = rowCnt;
		this.sqlCondtnTxt = sqlCondtnTxt;
		this.srcTblId = srcTblId;
		this.srcTblNm = srcTblNm;
		this.srcTblOwnrId = srcTblOwnrId;
		this.tgtTblId = tgtTblId;
		this.tgtTblNm = tgtTblNm;
		this.upTmstmp = upTmstmp;
	}

	public String getPrgrmId() {
		return prgrmId;
	}

	public void setPrgrmId(String prgrmId) {
		this.prgrmId = prgrmId;
	}

	public String getElpsdTime() {
		return elpsdTime;
	}

	public void setElpsdTime(String elpsdTime) {
		this.elpsdTime = elpsdTime;
	}

	public String getErrCntnt() {
		return errCntnt;
	}

	public void setErrCntnt(String errCntnt) {
		this.errCntnt = errCntnt;
	}

	public String getExeEndTmstmp() {
		return exeEndTmstmp;
	}

	public void setExeEndTmstmp(String exeEndTmstmp) {
		this.exeEndTmstmp = exeEndTmstmp;
	}

	public String getExeGrpId() {
		return exeGrpId;
	}

	public void setExeGrpId(String exeGrpId) {
		this.exeGrpId = exeGrpId;
	}

	public String getExeStrtTmstmp() {
		return exeStrtTmstmp;
	}

	public void setExeStrtTmstmp(String exeStrtTmstmp) {
		this.exeStrtTmstmp = exeStrtTmstmp;
	}

	public String getExeSttsVal() {
		return exeSttsVal;
	}

	public void setExeSttsVal(String exeSttsVal) {
		this.exeSttsVal = exeSttsVal;
	}

	public String getExeTypNm() {
		return exeTypNm;
	}

	public void setExeTypNm(String exeTypNm) {
		this.exeTypNm = exeTypNm;
	}

	public Integer getFailCnt() {
		return failCnt;
	}

	public void setFailCnt(Integer failCnt) {
		this.failCnt = failCnt;
	}

	public Integer getRowCnt() {
		return rowCnt;
	}

	public void setRowCnt(Integer rowCnt) {
		this.rowCnt = rowCnt;
	}

	public String getSqlCondtnTxt() {
		return sqlCondtnTxt;
	}

	public void setSqlCondtnTxt(String sqlCondtnTxt) {
		this.sqlCondtnTxt = sqlCondtnTxt;
	}

	public String getSrcTblId() {
		return srcTblId;
	}

	public void setSrcTblId(String srcTblId) {
		this.srcTblId = srcTblId;
	}

	public String getSrcTblNm() {
		return srcTblNm;
	}

	public void setSrcTblNm(String srcTblNm) {
		this.srcTblNm = srcTblNm;
	}

	public String getSrcTblOwnrId() {
		return srcTblOwnrId;
	}

	public void setSrcTblOwnrId(String srcTblOwnrId) {
		this.srcTblOwnrId = srcTblOwnrId;
	}

	public String getTgtTblId() {
		return tgtTblId;
	}

	public void setTgtTblId(String tgtTblId) {
		this.tgtTblId = tgtTblId;
	}

	public String getTgtTblNm() {
		return tgtTblNm;
	}

	public void setTgtTblNm(String tgtTblNm) {
		this.tgtTblNm = tgtTblNm;
	}

	public String getUpTmstmp() {
		return upTmstmp;
	}

	public void setUpTmstmp(String upTmstmp) {
		this.upTmstmp = upTmstmp;
	}

	public Integer getLogNo() {
		return logNo;
	}

	public void setLogNo(Integer logNo) {
		this.logNo = logNo;
	}

	
	
	
	
}
