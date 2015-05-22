package pdss5.hs.hdw;

import java.io.Serializable;

public class MasterTableVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String srcSystmNm;		// 소스 시스템 이름
	private String srcTblId;		// 소스 테이블 ID
	private Integer exeGrpId;		// 실행그룹 ID
	private String exeTpyNm;		// 실행타입 이름 - 예) java
	private String srcTblOwnrId;	// 소스 테이블 소유자 ID
	private String srcTblNm; 		// 소스 테이블 이름
	private String tgtTblId;		// 타겟 테이블 ID
	private String tgtTblNm;		// 타겟 테이블 이름
	private String sqlCondtnTxt;	// 추가 sql문 - 조건문
	private String extrctColId;		// 추출 대상 컬럼 ID
	private String extrctCol;		// 추출 대산 컬럼 이름	
	private String useYn;			// 추가 기능을 위한 옵션
	private String exeYn;			// 추가 기능을 위한 옵션
	
	public MasterTableVO(){};
	
	public MasterTableVO(String srcSystmNm, String srcTblId, Integer exeGrpId, String exeTpyNm,  String srcTblOwnrId, String srcTblNm, 
			String tgtTblId, String tgtTblNm, String sqlCondtnTxt, String extrctColId, String extrctCol, String useYn,
			String exeYn){
		super();
		this.srcSystmNm = srcSystmNm;
		this.srcTblId = srcTblId;
		this.exeGrpId = exeGrpId;
		this.exeTpyNm = exeTpyNm;
		this.srcTblOwnrId = srcTblOwnrId;
		this.srcTblNm = srcTblNm;
		this.tgtTblId = tgtTblId;
		this.tgtTblNm = tgtTblNm;
		this.sqlCondtnTxt = sqlCondtnTxt;
		this.extrctColId = extrctColId;
		this.extrctCol = extrctCol;
		this.useYn = useYn;
		this.exeYn = exeYn;		
	}

	public String getSrcSystmNm() {
		return srcSystmNm;
	}

	public void setSrcSystmNm(String srcSystmNm) {
		this.srcSystmNm = srcSystmNm;
	}

	public String getSrcTblId() {
		return srcTblId;
	}

	public void setSrcTblId(String srcTblId) {
		this.srcTblId = srcTblId;
	}

	public Integer getExeGrpId() {
		return exeGrpId;
	}

	public void setExeGrpId(Integer exeGrpId) {
		this.exeGrpId = exeGrpId;
	}

	public String getExeTpyNm() {
		return exeTpyNm;
	}

	public void setExeTpyNm(String exeTpyNm) {
		this.exeTpyNm = exeTpyNm;
	}

	public String getSrcTblOwnrId() {
		return srcTblOwnrId;
	}

	public void setSrcTblOwnrId(String srcTblOwnrId) {
		this.srcTblOwnrId = srcTblOwnrId;
	}

	public String getSrcTblNm() {
		return srcTblNm;
	}

	public void setSrcTblNm(String srcTblNm) {
		this.srcTblNm = srcTblNm;
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

	public String getSqlCondtnTxt() {
		return sqlCondtnTxt;
	}

	public void setSqlCondtnTxt(String sqlCondtnTxt) {
		this.sqlCondtnTxt = sqlCondtnTxt;
	}

	public String getExtrctColId() {
		return extrctColId;
	}

	public void setExtrctColId(String extrctColId) {
		this.extrctColId = extrctColId;
	}

	public String getExtrctCol() {
		return extrctCol;
	}

	public void setExtrctCol(String extrctCol) {
		this.extrctCol = extrctCol;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getExeYn() {
		return exeYn;
	}

	public void setExeYn(String exeYn) {
		this.exeYn = exeYn;
	}	
}
