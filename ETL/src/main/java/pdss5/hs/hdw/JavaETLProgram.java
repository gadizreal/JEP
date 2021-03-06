package pdss5.hs.hdw;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

public class JavaETLProgram {

	private static String row_cnt;
	private static String err_msg;
	private static long start;
	private static Properties props;
	private static Properties targetProps;
	private static Properties metaProps;
//	private static String properties_home = System.getProperty("scheduler.property");
	private static String properties_home = "d:\\dev\\";
	private static int cnt;
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(JavaETLProgram.class);

	public static void main(String[] args) throws IOException {
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
			System.out.println("line.substring : "+(line.substring(0, line.lastIndexOf("=")) + line.substring(line.indexOf("=")+1)));
        }			
		
		// Master Table 접속 // 메타DB ? 
		InputStream inputStreamMeta = null;
		try {
			inputStreamMeta = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}

		final SqlSessionFactory sqlSessionFactoryMeta = new SqlSessionFactoryBuilder().build(inputStreamMeta, metaProps);
		final SqlSession sessionMeta = sqlSessionFactoryMeta.openSession(true);

		
		
//		if(args[0].equals("PDSS5DM")){
//			logger.debug("배치전체_변경적재_수행");
//			sqlSessionFactoryMeta.openSession().selectOne("pdss5.hs.hdw.etl.ETLTargetMapper.callProcBatchTot", args[3]);
//			logger.debug("배치전체_변경적재_종료");
//		}		

		Map m = new HashMap();
		m.put("EXE_GRP_ID", "1"); // Connection DB parameter input

		List<DBConnectionVO> list = sessionMeta.selectList("getConnectionList", m); // source connection 정보 List로 받기
				
		start = System.currentTimeMillis(); // start_time 
		Date s_time = new Date(start); // start_time date 
		
		// source db connection 정보들 for문으로 루프
		for (DBConnectionVO dto : list) {	
			
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
			
			// connection �뺣낫 �쒖떆			
//			String db_connect;
//			db_connect = "db_driver=" + dto.getSrcDriver() + "\n" + "db_url="
//					+ dto.getSrcUrl() + "\n" + "db_username="
//					+ dto.getSrcUsername() + "\n" + "db_password="
//					+ dto.getSrcPassword();
//			System.out.println(db_connect);
			
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

			List<MasterTableVO> master = sessionMeta.selectList("getMetaTableList01", m_1); // meta master table	
			
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
				
				Map m_3 = new HashMap();
				m_3.put("TGT_TBL_ID", ms.getTgtTblId());
				m_3.put("EXTRCT_COL_ID",	ms.getExtrctColId());
				m_3.put("EXTRCT_COL", ms.getExtrctCol());
				
				sessionSource.select("pdss5.hs.hdw.ETLSourceMapper.getSourceTableList", m_2,new ResultHandler(){
					public void handleResult(ResultContext context) {
						Map<String, Object> source_map = (Map<String, Object>) context.getResultObject();						
						source_map.put("TGT_TBL_ID", ms.getTgtTblId());
						source_map.put("EXTRCT_COL_ID",	ms.getExtrctColId());
						source_map.put("EXTRCT_COL", ms.getExtrctCol());
						cnt ++;
						
						sessionTarget.insert("pdss5.hs.hdw.ETLTargetMapper.insertTargetTable", source_map); //Target insert
						}			
					});
				sessionTarget.commit();
				sessionTarget.close();
				System.out.println("cnt = "+cnt);
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
		}
		
		long end = System.currentTimeMillis(); // end_time �쒗쁽
		Date e_time = new Date(end); // end_time date �뺤떇�쇰줈 蹂�솚
		System.out.println("ELAPSED_DTM=" + (end - start) / 1000);
		
		sessionMeta.close();
		try {		
			inputStreamMeta.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
