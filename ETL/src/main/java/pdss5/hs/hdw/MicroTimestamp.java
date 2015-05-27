package pdss5.hs.hdw;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MicroTimestamp {	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private long timeInMillis = System.currentTimeMillis();
	private Date timeInDate = new Date(timeInMillis);
	private String timeInFormat = sdf.format(timeInDate);	
	
		
	public MicroTimestamp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SimpleDateFormat getSdf() {
		return sdf;
	}
	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
	public long getTimeInMillis() {
		return timeInMillis;
	}
	public void setTimeInMillis(long timeInMillis) {
		this.timeInMillis = timeInMillis;
	}
	public Date getTimeInDate() {
		return timeInDate;
	}
	public void setTimeInDate(Date timeInDate) {
		this.timeInDate = timeInDate;
	}
	public String getTimeInFormat() {
		return timeInFormat;
	}
	public void setTimeInFormat(String timeInFormat) {
		this.timeInFormat = timeInFormat;
	}
	   
	
	
	
}
