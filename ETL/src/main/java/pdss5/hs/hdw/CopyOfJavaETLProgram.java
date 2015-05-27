package pdss5.hs.hdw;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
//import java.awt.List;
import java.io.IOException;
import java.io.InputStream;
import java.security.acl.LastOwnerException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.ibatis.io.Resources;
/*import org.apache.ibatis.io.Resources;*/
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
/*import org.apache.ibatis.session.SqlSessionFactory;*/
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;


public class CopyOfJavaETLProgram {

	private static String err_msg = ""; // 에러메세지
	private static Properties props;
	private static Properties targetProps;
	private static Properties metaProps;
	private static String properties_home = "d:\\dev\\";
	private static int row_cnt;
	private static int fail_cnt;
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(CopyOfJavaETLProgram.class);

	
	public static void main(String[] args) throws IOException {
		
		LogTableVO logTable = new LogTableVO() ; // 로그VO 생성
		logTable.setPrgrmId("JavaETLProgram"); // 프로그램이름 입력		
		
		File default_file = null;
		//String meta_proerties = properties_home + "meta_properties.txt"; // oracle
 		String meta_proerties = properties_home + "meta_properties_db2.txt"; // db2
		
		
		String filename = System.getProperty(meta_proerties);
		
		metaProps = new Properties();
		
		filename = meta_proerties;   
		String resource = "config/mybatis-config.xml";
		
		default_file = new File(filename);
		
		FileReader fileReader = new FileReader(default_file);
		BufferedReader reader = new BufferedReader(fileReader);
		String line = null;
		
		while((line = reader.readLine()) != null){
			metaProps.put(line.substring(0, line.lastIndexOf("=")) , line.substring(line.indexOf("=")+1));
        }			
		
		// Master Table 접속 // 메타DB 
		InputStream inputStreamMeta = null;
		try {
			inputStreamMeta = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}

		final SqlSessionFactory sqlSessionFactoryMeta = new SqlSessionFactoryBuilder().build(inputStreamMeta, metaProps);
		final SqlSession sessionMeta = sqlSessionFactoryMeta.openSession(true);
		
		// Log DB 접속		
		String resource_Log = "config/mybatis-config_Log_Test.xml";
		InputStream inputStream_log = Resources.getResourceAsStream(resource_Log);		
		SqlSessionFactory sqlSessionFactory_log = new SqlSessionFactoryBuilder().build(inputStream_log);
		SqlSession session_log = sqlSessionFactory_log.openSession();
		
		Map m = new HashMap();
		m.put("EXE_GRP_ID", "1"); // Connection DB parameter input

		List<DBConnectionVO> list = sessionMeta.selectList("getConnectionList", m); // source connection 정보 List로 받기
						
		
		// source db connection 정보들 for문으로 루프
		for (DBConnectionVO dto : list) {	// 여기부터 전체 루프문 시작. DB의 개수만큼 반복
			
			// source_connection 정보 input			
			props = new Properties();
			props.put("driver", dto.getSrcDriver());
			props.put("url", dto.getSrcUrl());
			props.put("username", dto.getSrcUsername());
			props.put("password", dto.getSrcPassword());
			
			// target_connection 정보 input
			targetProps = new Properties();
			targetProps.put("driver", dto.getTgtDriver());
			targetProps.put("url", dto.getTgtUrl());
			targetProps.put("username", dto.getTgtUsername());
			targetProps.put("password", dto.getTgtPassword());
			
			// source_connection 정보 표시			
			//여기서부터 logger쩜debug -> system쩜out쩜println 으로 변경  
			System.out.println("================================================================");			
			System.out.println("DB_DRIVER=" + dto.getSrcDriver());
			System.out.println("DB_DRIVER=" + dto.getSrcUrl());
			System.out.println("DB_DRIVER=" + dto.getSrcUsername());
			System.out.println("DB_DRIVER=" + dto.getSrcPassword());			
			System.out.println("================================================================");			
			// target_connection 정보 표시
            System.out.println("================================================================");			
			System.out.println("DB_DRIVER=" + dto.getTgtDriver());
			System.out.println("DB_DRIVER=" + dto.getTgtUrl());
			System.out.println("DB_DRIVER=" + dto.getTgtUsername());
			System.out.println("DB_DRIVER=" + dto.getTgtPassword());			
			System.out.println("================================================================");		
			// 여기까지 변경 logger쩜debug -> system쩜out쩜println 으로 변경  
			
					
			// Source DB �묒냽
			InputStream inputStreamSource = null;
			try {
				inputStreamSource = Resources.getResourceAsStream(resource);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// source db �뺣낫 mybatis-config.xml濡�mapping
			final SqlSessionFactory sqlSessionFactorySource = new SqlSessionFactoryBuilder().build(inputStreamSource, props); // source db �뺣낫
			final SqlSession sessionSource = sqlSessionFactorySource.openSession(ExecutorType.BATCH, true);			
			
			Map m_1 = new HashMap();
			Map m_2 = new HashMap();
			m_1.put("EXE_GRP_ID", "2"); // master table parameter媛�input
			m_1.put("SRC_TBL_OWNR_ID", dto.getSrcUsername()); // master table parameter媛�input			
			
			// 로그용 직접입력정보 입력
			logTable.setExeGrpId("2"); //  실행그룹을 logTableVO에 입력
			logTable.setSrcTblOwnrId(dto.getSrcUsername()); // 소스테이블 소유자 입력
			logTable.setExeTypNm("JAVA"); // 실행 타입 입력
			
			// meta master table
			List<MasterTableVO> master = sessionMeta.selectList("getMetaTableList01", m_1); 	
			
			// 마스터 DB 관련 작업
			for (final MasterTableVO ms : master) { // table 개수 만큼 반복
				//로그용 시작시간
				MicroTimestamp startTime = new MicroTimestamp(); 
				String start_timstamp = startTime.getTimeInFormat();
				logTable.setExeStrtTmstmp(start_timstamp);	 //  실행시간 입력
				logTable.setExeSttsVal("ING");    // 실행상태 업데이트
				
				System.out.println("작업상태 		:"+logTable.getExeSttsVal());
				
				// Target DB 
				InputStream inputStreamTarget = null;
				try {
					inputStreamTarget = Resources.getResourceAsStream(resource);
				} catch (IOException e) {
					e.printStackTrace();
				}
 
				SqlSessionFactory sqlSessionFactoryTarget = new SqlSessionFactoryBuilder().build(inputStreamTarget, targetProps);
				final SqlSession sessionTarget = sqlSessionFactoryTarget.openSession(ExecutorType.BATCH, false);

				m_2.put("SRC_TBL_ID", ms.getSrcTblId());
				m_2.put("SQL_CONDTN_TXT", ms.getSqlCondtnTxt());
				
				// 로그용 소스테이블 정보 입력
				logTable.setSrcTblId(ms.getSrcTblId()); // 로그용 소스테이블 ID 입력
				logTable.setSqlCondtnTxt(ms.getSqlCondtnTxt()); // 로그용 조건문 입력
				logTable.setSrcTblNm(ms.getSrcTblNm());  // 로그용 소스테이블 이름 입력
				
				Map m_3 = new HashMap();
				m_3.put("TGT_TBL_ID", ms.getTgtTblId());
				m_3.put("EXTRCT_COL_ID",	ms.getExtrctColId());
				m_3.put("EXTRCT_COL", ms.getExtrctCol());
				
				// 로그용 타겟테이블 정보 입력
				logTable.setTgtTblId(ms.getTgtTblId()); // 로그용 타겟 테이블 ID 입력
				logTable.setTgtTblNm(ms.getTgtTblNm());  // 로그용 타겟 테이블 이름 입력
				
				
				// 실행화면용 logTableVO 내용 출력
				System.out.println("시작시간		 : "+logTable.getExeStrtTmstmp());
				System.out.println("실행 그룹 ID 	 : "+logTable.getExeGrpId());
				System.out.println("소스 테이블 소유자	: "+ logTable.getSrcTblOwnrId());
				System.out.println("실행 타입 		 : "+logTable.getExeTypNm());
				System.out.println("소트 테이블 ID	 : "+logTable.getSrcTblId());
				System.out.println("조건문		 : "+logTable.getSqlCondtnTxt());
				System.out.println("소스 테이블 이름	 : "+logTable.getSrcTblNm());
				System.out.println("타겟 테이블 ID 	 : "+logTable.getTgtTblId());
				System.out.println("타겟 테이블 이름  	: "+logTable.getTgtTblNm());					
				
				
				// 로그테이블에 insert 시작				
				try { // 로그 insert 에러를 대비한 트라이구문											
					session_log.insert("insertLog", logTable); //Log insert	
					System.out.println("로그DB insert !");
					session_log.commit();
					} catch (Exception e) {
						System.out.println("로그 insert 에러메세지 : "+e+" 로그 insert 에러메세지 여기까지!");
						err_msg = e.toString();						
					} 
				
				// 로그테이블 NO 출력
				try { // 로그 select 에러를 대비한 트라이구문	
					int LogNo = session_log.selectOne("selectLog", logTable);
					logTable.setLogNo(LogNo) ; //log select						
					System.out.println("로그DB select !");
					System.out.println("Log번호		 : "+logTable.getLogNo());
					} catch (Exception e) {
						System.out.println("로그 select 에러메세지 : "+e+" 로그 select 에러메세지 여기까지!");
						err_msg = e.toString();						
					} 
				row_cnt=0; // 대상 row 개수 초기화
				fail_cnt=0; // 실패 row 개수 초기화
				err_msg=""; // 에러메세지 초기화
				
				sessionSource.select("pdss5.hs.hdw.ETLSourceMapper.getSourceTableList", m_2,new ResultHandler(){
					public void handleResult(ResultContext context) { //타겟  인서트를 위한 반복구문. 인서트 행 숫자만큼 반복
						try { //타겟  인서트 에러를 대비한 트라이구문
							
							Map<String, Object> source_map = (Map<String, Object>) context.getResultObject();						
							source_map.put("TGT_TBL_ID", ms.getTgtTblId());
							source_map.put("EXTRCT_COL_ID",	ms.getExtrctColId());
							source_map.put("EXTRCT_COL", ms.getExtrctCol());
							row_cnt++;	// 대상 row +1						
							sessionTarget.insert("pdss5.hs.hdw.ETLTargetMapper.insertTargetTable", source_map); //Target insert
							
							} catch (Exception e) {
								System.out.println("insert 에러메세지 : "+e+" insert 에러메세지 여기까지!");
								err_msg = e.toString();
								fail_cnt++; // 실패시 실패 row +1
								
							} finally {	}						
						}	  // 타겟 인서트 끝		
					});
				sessionTarget.commit();
				sessionTarget.close();
				
				if (fail_cnt > 0 ) {
				logTable.setExeSttsVal("ERR"); // 에러가 발생시 에러메세지를 입력
				}
				
				// 로그용 입력행,실패행,에러메세지 입력
				logTable.setRowCnt(row_cnt); 
				logTable.setFailCnt(fail_cnt);
				logTable.setErrCntnt(err_msg);
				
				// 로그용 입력행,실패행,에러메세지 확인
				System.out.println("실행 row 개수 	: "+logTable.getRowCnt());
				System.out.println("실패 row 개수 	: "+logTable.getFailCnt());
				System.out.println("에러 메세지	: "+logTable.getErrCntnt());
				
				// 종료시간
				MicroTimestamp endTime = new MicroTimestamp();
				String end_timstamp = endTime.getTimeInFormat();
				System.out.println("작업 종료 시간	 : "+end_timstamp);
				logTable.setExeEndTmstmp(end_timstamp);
				
				// 시간계산식
				long diff = endTime.getTimeInMillis() - startTime.getTimeInMillis();
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				
				// 계산된 시간 xd xh xm x.xxxs 로 입력
				String ElpsdTime = diffDays + "d "+diffHours + "h "+diffMinutes + "m "+diffSeconds + "."+diff+"s";
				logTable.setElpsdTime(ElpsdTime);
				logTable.setExeSttsVal("End");

				// 실행결과 용 작업시간 출력
				System.out.println("작업시간 		: "+ElpsdTime);
				System.out.println("작업상태 		: "+logTable.getExeSttsVal());
				
				//로그 테이블 업데이트			
				try { // 로그 업데이트 에러를 대비한 트라이구문											
					session_log.update("updateLog", logTable); //Target insert						
					session_log.commit();
					System.out.println("로그DB update ! ");	
					} catch (Exception e) {
						System.out.println("로그 업데이트 에러메세지 : "+e+" 여기까지!");
						err_msg = e.toString();						
					} 			

				
				
				try {
					inputStreamTarget.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // 테이블 수 만큼 반복FOR문 종료

			props.clear();

			try {
				inputStreamSource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			sessionSource.close();
			
			
			
		} // for문 끝 - 모든 테이블 종료
						
		// 최종 업데이트 시간 
		MicroTimestamp endProgramTime = new MicroTimestamp();
		String up_timstamp = endProgramTime.getTimeInFormat();
		logTable.setUpTmstmp(up_timstamp);
		System.out.println("로그 업데이트 시간	 : "+up_timstamp);
		
		
		try { // 로그 업데이트 에러를 대비한 트라이구문											
			session_log.update("updateUpTmstamp", logTable); //Target insert	
			session_log.commit();
			System.out.println("로그DB updateUpTmstamp ! ");					
			} catch (Exception e) {
				System.out.println("로그 업데이트 에러메세지 : "+e+" 로그 업데이트 에러메세지 여기까지!");
				err_msg = e.toString();						
			} 
		
		// 로그 세션 클로즈
		session_log.close();
		try {
			//reader1.close();
			inputStream_log.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		
		sessionMeta.close();
		try {		
			inputStreamMeta.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		
	}
}
