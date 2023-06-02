package com.example.project.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBConn {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			DriverManager.registerDriver(new org.h2.Driver());
			conn = DriverManager.getConnection("jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1",  "sa", "");
			System.out.println("DBConn : 데이터베이스 연결성공");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DBConn : 데이터베이스 연결실패");
			System.out.println("DBConn : Message : " + e.getMessage());
		}
		return null;
	}

	public static void close(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
			System.out.println("DB종료시 오류가 발생 : " + e.getMessage());
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstmt) {
		try {
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println("DB종료시 오류가 발생 : " + e.getMessage());
		}
	}

	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("DB종료시 오류가 발생 : " + e.getMessage());
		}
	}
	
	public static void close(Connection conn, List<PreparedStatement> pstmtList, List<ResultSet> rsList) {
		try {
			conn.close();
			for (PreparedStatement pstmt : pstmtList) {
				pstmt.close();
			}
			for (ResultSet rs : rsList) {
				rs.close();
			}
		} catch (Exception e) {
			System.out.println("DB종료시 오류가 발생 : " + e.getMessage());
		}
	}
	
	
	
	
	// 임시
	public static void init() throws SQLException {
		Connection conn = getConnection();
		System.out.println("DB 정보를 초기화 합니다.");
		PreparedStatement pstmt1 = conn.prepareStatement(
				" DROP TABLE IF EXISTS `todo`; "
				+ " CREATE TABLE `todo` ( "
				+ "    idx INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "    content VARCHAR(255) NOT NULL, "
				+ "    done_yn VARCHAR(1) NOT NULL DEFAULT 'N', "
				+ "    delete_yn VARCHAR(1) NOT NULL DEFAULT 'N', "
				+ "    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "
				+ "    update_date TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP, "
				+ "    delete_date TIMESTAMP NULL "
				+ " ); ");
		pstmt1.executeUpdate();
		
		PreparedStatement pstmt2 = conn.prepareStatement(
				" INSERT INTO `todo` (`content`, `done_yn`, `delete_yn`, `create_date`) VALUES "
				+ "    ('일어나기', 'Y', 'N', now()), "
				+ "    ('양치하기', 'Y', 'N', now()), "
				+ "    ('샤워하기', 'N', 'N', now()), "
				+ "    ('출근하기', 'N', 'N', now()), "
				+ "    ('퇴근하기', 'N', 'N', now()); ");
		pstmt2.executeUpdate();
		
		
		List<PreparedStatement> pstmtList = new ArrayList<>(Arrays.asList(pstmt1, pstmt2));
		List<ResultSet> reList = new ArrayList<>();
		close(conn, pstmtList, reList);
		
	}

}