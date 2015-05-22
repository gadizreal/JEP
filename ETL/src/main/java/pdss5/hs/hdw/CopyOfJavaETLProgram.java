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
import org.slf4j.LoggerFactory;

public class CopyOfJavaETLProgram {

	//private static String row_cnt;
	private static String err_msg = ""; // 에러메세지
	private static long start; // 시작시간	
	private static Properties props;
	private static Properties targetProps;
	private static Properties metaProps;
//	private static String properties_home = System.getProperty("scheduler.property");
	private static String properties_home = "d:\\dev\\";
	private static int row_cnt;
	private static int fail_cnt;
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(CopyOfJavaETLProgram.class);

		
	public static void main(String[] args) throws IOException {
		LogTableVO logTable = new LogTableVO() ; // 로그VO 생성
		logTable.setPrgrmId("JavaETLProgram"); // 프로그램이름 입력
		logTable.setExeSttsVal("ING");
		
//      String resource = XML_HOME + "//mybatis-config.xml";
		File default_file = null;
//		String meta_proerties = properties_home + "//meta_properties"; 
		//String meta_proerties = properties_home + "meta_properties.txt"; // oracle
 		String meta_proerties = properties_home + "meta_properties_db2.txt"; // db2
		
		
		String filename = System.getProperty(meta_proerties);
		
		metaProps = new Properties();
		
		filename = meta_proerties;   // 이중선언? 위에서 get으로 받는내용이 안들어가서 새로 선언한듯.		
		
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
				
		start = System.currentTimeMillis(); // start_time 
		Date start_time = new Date(start); // start_time date 
				
		//로그용 시작시간
		String start_timstamp = MicroTimestamp.INSTANCE.get();
		System.out.println("시작시간 : "+start_timstamp);
		logTable.setExeStrtTmstmp(start_timstamp);
		
		
		
		// source db connection 정보들 for문으로 루프
		for (DBConnectionVO dto : list) {	// 여기부터 루프문 시작
			
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
			logTable.setExeTypNm("1");
			
			// meta master table
			List<MasterTableVO> master = sessionMeta.selectList("getMetaTableList01", m_1); 	
			
			// source db connection �뺣낫��for臾몄쑝濡�猷⑦봽
			for (final MasterTableVO ms : master) {
				
				// Target DB �묒냽
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
				logTable.setSrcTblId(ms.getSrcTblId()); // 로그용 소스테이블 이름 입력
				logTable.setSqlCondtnTxt(ms.getSqlCondtnTxt()); // 로그용 조건문 입력
				logTable.setSrcTblNm(ms.getSrcTblNm());
				
				Map m_3 = new HashMap();
				m_3.put("TGT_TBL_ID", ms.getTgtTblId());
				m_3.put("EXTRCT_COL_ID",	ms.getExtrctColId());
				m_3.put("EXTRCT_COL", ms.getExtrctCol());
				
				// 로그용 타겟테이블 정보 입력
				logTable.setTgtTblId(ms.getTgtTblId()); // 로그용 타겟 테이블 이름 입력
				logTable.setTgtTblNm(ms.getTgtTblNm());
				
				
				// 로그테이블에 인서트 시작				
				try { // 로그 인서트 에러를 대비한 트라이구문											
					session_log.insert("insertLog", logTable); //Log insert					
					} catch (Exception e) {
						System.out.println("로그 인서트 에러메세지 : "+e+" 여기까지!");
						err_msg = e.toString();						
					} 
				
				// 로그테이블의 NO 출력
				try { // 로그 셀렉트 에러를 대비한 트라이구문											
					logTable.setLogNo(session_log.insert("selectLog", logTable)) ; //log select					
					} catch (Exception e) {
						System.out.println("로그 셀렉트 에러메세지 : "+e+" 여기까지!");
						err_msg = e.toString();						
					} 
				
				
				sessionSource.select("pdss5.hs.hdw.ETLSourceMapper.getSourceTableList", m_2,new ResultHandler(){
					public void handleResult(ResultContext context) { //타겟  인서트를 위한 반복구문. 인서트 행 숫자만큼 반복
						try { //타겟  인서트 에러를 대비한 트라이구문
							
							Map<String, Object> source_map = (Map<String, Object>) context.getResultObject();						
							source_map.put("TGT_TBL_ID", ms.getTgtTblId());
							source_map.put("EXTRCT_COL_ID",	ms.getExtrctColId());
							source_map.put("EXTRCT_COL", ms.getExtrctCol());
							row_cnt++;							
							sessionTarget.insert("pdss5.hs.hdw.ETLTargetMapper.insertTargetTable", source_map); //Target insert
							
							} catch (Exception e) {
								System.out.println("에러메세지 : "+e+" 여기까지!");
								err_msg = e.toString();
								fail_cnt++;
								
							} finally {
								
							}						
						}	  // 타겟 인서트 끝		
					});
				sessionTarget.commit();
				sessionTarget.close();
				System.out.println("cnt = "+row_cnt);
				System.out.println("fail_cnt = "+fail_cnt);
				
				if (fail_cnt > 0 ) {
				logTable.setExeSttsVal("ERR");
				}
				
				// 로그용 입력행,실패행,에러메세지 입력
				logTable.setRowCnt(row_cnt);
				logTable.setFailCnt(fail_cnt);
				logTable.setErrCntnt(err_msg);
				
				try {
					inputStreamTarget.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			props.clear();

			try {
				inputStreamSource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			sessionSource.close();
			
			// 종료시간
			String end_timstamp = MicroTimestamp.INSTANCE.get();
			logTable.setExeEndTmstmp(end_timstamp);
			long end = System.currentTimeMillis(); // end_time �쒗쁽
			logTable.setElpsdTime("ELAPSED_DTM=" + ((end - start) / 1000)+"."+(end - start) % 1000 +"초") ;
			//로그 테이블 업데이트			
			try { // 로그 업데이트 에러를 대비한 트라이구문											
				session_log.update("insertLog", logTable); //Target insert				
				} catch (Exception e) {
					System.out.println("로그 업데이트 에러메세지 : "+e+" 여기까지!");
					err_msg = e.toString();						
				} 			
			
			logTable=new LogTableVO(); // VO 초기화
			
			// 로그 세션 클로즈
			session_log.close();
			try {
				//reader1.close();
				inputStream_log.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
			
		} // for문 끝 - 모든 테이블 종료
		
		long end2 = System.currentTimeMillis(); // end_time �쒗쁽
		System.out.println("ELAPSED_DTM=" + ((end2 - start) / 1000)+"."+(end2 - start) % 1000 +"초");
		String end_timstamp2 = MicroTimestamp.INSTANCE.get();
		System.out.println("종료시간 : "+end_timstamp2);
		
		String up_timstamp = MicroTimestamp.INSTANCE.get();
		System.out.println("로그 업데이트 시간 : "+up_timstamp);
		
		sessionMeta.close();
		try {		
			inputStreamMeta.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		
	}
}
