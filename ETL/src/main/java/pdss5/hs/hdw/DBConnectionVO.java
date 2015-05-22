package pdss5.hs.hdw;

import java.io.Serializable;

public class DBConnectionVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer exeGrpId;
	private String srcDriver;
	private String srcUrl;
	private String srcUsername;
	private String srcPassword;	
	private String tgtDriver;
	private String tgtUrl;
	private String tgtUsername;
	private String tgtPassword;
	
	public DBConnectionVO(){};
	
	public DBConnectionVO(Integer exeGrpId, String srcDriver, String srcUrl, String srcUsername, String srcPassword,
			String tgtDriver, String tgtUrl, String tgtUsername, String tgtPassword){
		super();
		this.exeGrpId = exeGrpId;			// 실행그룹 ID
		this.srcDriver = srcDriver;			// 소스 드라이버
		this.srcUrl = srcUrl;				// 소스 url
		this.srcUsername = srcUsername;		// 소스 유저네임
		this.srcPassword = srcPassword;		// 소스 패스워드
		this.tgtDriver = tgtDriver;			// 타겟 드라이버
		this.tgtUrl = tgtUrl;				// 타겟 url
		this.tgtUsername = tgtUsername;		// 타겟 유저네임
		this.tgtPassword = tgtPassword;		// 타겟 패스워드
	}

	public Integer getExeGrpId() {
		return exeGrpId;
	}

	public void setExeGrpId(Integer exeGrpId) {
		this.exeGrpId = exeGrpId;
	}

	public String getSrcDriver() {
		return srcDriver;
	}

	public void setSrcDriver(String srcDriver) {
		this.srcDriver = srcDriver;
	}

	public String getSrcUrl() {
		return srcUrl;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}

	public String getSrcUsername() {
		return srcUsername;
	}

	public void setSrcUsername(String srcUsername) {
		this.srcUsername = srcUsername;
	}

	public String getSrcPassword() {
		return srcPassword;
	}

	public void setSrcPassword(String srcPassword) {
		this.srcPassword = srcPassword;
	}

	public String getTgtDriver() {
		return tgtDriver;
	}

	public void setTgtDriver(String tgtDriver) {
		this.tgtDriver = tgtDriver;
	}

	public String getTgtUrl() {
		return tgtUrl;
	}

	public void setTgtUrl(String tgtUrl) {
		this.tgtUrl = tgtUrl;
	}

	public String getTgtUsername() {
		return tgtUsername;
	}

	public void setTgtUsername(String tgtUsername) {
		this.tgtUsername = tgtUsername;
	}

	public String getTgtPassword() {
		return tgtPassword;
	}

	public void setTgtPassword(String tgtPassword) {
		this.tgtPassword = tgtPassword;
	}		
}
