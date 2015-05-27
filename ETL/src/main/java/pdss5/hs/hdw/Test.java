package pdss5.hs.hdw;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		long timeInMillis = System.currentTimeMillis();
		Date timeInDate = new Date(timeInMillis);
		String timeInFormat = sdf.format(timeInDate);	
		System.out.println(timeInFormat);
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
		}
		
		long timeInMillis2 = System.currentTimeMillis();
		Date timeInDate2 = new Date(timeInMillis2);
		String timeInFormat2 = sdf.format(timeInDate2);	
		System.out.println(timeInFormat2);
		System.out.println(timeInFormat);
		
		// 시간계산식은 이게 맞음.
		long diff = timeInMillis2 - timeInMillis;
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		// 여기까지 시간계싼식

		System.out.println("diff : " + diff);
		System.out.print(diffDays + "d ");
		System.out.print(diffHours + "h ");
		System.out.print(diffMinutes + "m ");
		System.out.print(diffSeconds + "."+diff+"s");
		
		
	}

}
