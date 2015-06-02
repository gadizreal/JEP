package pdss5.hs.hdw;

public class LogTableVO {

	private String prgrmId	;		// 프로그램 ID
	private String elpsdTime;		// 작업 시간
	private String errCntnt	;		// 에러 개수
	private String exeEndTmstmp	;	// 종료 시간
	private String exeGrpId	;		// 그룹 ID
	private String exeStrtTmstmp;	// 시작 시간
	private String exeSttsVal	;	// 프로그램 상태
	private String exeTypNm	;		// 실행 타입
	private Integer failCnt	;		// 실패 row 개수
	private Integer rowCnt	;		// 대상 row 개수
	private String sqlCondtnTxt	;	// 조건문
	private String srcTblId	;		// 소스 테이블 ID
	private String srcTblNm	;		// 소스 테이블 이름
	private String srcTblOwnrId	;	// 소스 테이블 소유자
	private String tgtTblId	;		// 타겟 테이블 ID
	private String tgtTblNm;		// 타겟 테이블 이름
	
	public LogTableVO() {
		super();
		// TODO Auto-generated constructor stub
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

	
	
	
	
}
