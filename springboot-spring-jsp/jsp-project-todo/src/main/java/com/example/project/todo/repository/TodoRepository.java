package com.example.project.todo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.project.common.DBConn;
import com.example.project.common.TimeConverter;
import com.example.project.todo.entity.TodoEntity;

public class TodoRepository {
	public static String FULL_COLUMNS = " idx, content, done_yn, delete_yn, create_date, update_date, delete_date ";
	
	public static List<TodoEntity> findByDeleteYn(String deleteYn) {
		
		final String sql = 
				" SELECT " + FULL_COLUMNS +
                " FROM todo " +
                " WHERE delete_yn = ?1 ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<TodoEntity> todoEntityList = new ArrayList<>();
		
		try {
			conn = DBConn.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, deleteYn);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				TodoEntity todoEntity = TodoEntity.builder()
						.idx(rs.getInt("idx"))
						.content(rs.getString("content"))
	                    .doneYn(rs.getString("done_yn"))
	                    .deleteYn(rs.getString("delete_yn"))
	                    .createDate(TimeConverter.fromTimestamp(rs.getTimestamp("create_date")))
	                    .updateDate(TimeConverter.fromTimestamp(rs.getTimestamp("update_date")))
	                    .deleteDate(TimeConverter.fromTimestamp(rs.getTimestamp("delete_date")))                    
	                    .build();
				
				
				todoEntityList.add(todoEntity);
			}
		} catch (Exception e) {
			System.out.println("findByDeleteYn 오류 발생 : " + e.getMessage());
		}
		
		DBConn.close(conn, pstmt, rs);
		
		return todoEntityList;

	}
	
	public static List<TodoEntity> findByfindByDeleteYnAndDoneYn(String deleteYn, String doneYn){
		
		final String sql = 
				" SELECT " + FULL_COLUMNS +
                " FROM todo " +
                " WHERE delete_yn = ?1 " +
                " AND done_yn = ?2 ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<TodoEntity> todoEntityList = new ArrayList<>();
		

		try {
			conn = DBConn.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, deleteYn);
			pstmt.setString(2, doneYn);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				TodoEntity todoEntity = TodoEntity.builder()
						.idx(rs.getInt("idx"))
						.content(rs.getString("content"))
	                    .doneYn(rs.getString("done_yn"))
	                    .deleteYn(rs.getString("delete_yn"))
	                    .createDate(TimeConverter.fromTimestamp(rs.getTimestamp("create_date")))
	                    .updateDate(TimeConverter.fromTimestamp(rs.getTimestamp("update_date")))
	                    .deleteDate(TimeConverter.fromTimestamp(rs.getTimestamp("delete_date")))                    
	                    .build();
				
				
				todoEntityList.add(todoEntity);
			}
		} catch (Exception e) {
			System.out.println("findByfindByDeleteYnAndDoneYn 오류 발생 : " + e.getMessage());
		}
		
		DBConn.close(conn, pstmt, rs);
		
		return todoEntityList;
		
	}
	
	public static TodoEntity findByIdx(Integer idx){
		
		final String sql = 
				" SELECT " + FULL_COLUMNS +
                " FROM todo " +
                " WHERE idx = ?1 ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		TodoEntity todoEntity = null;
		
		try {
			conn = DBConn.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, idx);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				todoEntity = TodoEntity.builder()
						.idx(rs.getInt("idx"))
						.content(rs.getString("content"))
	                    .doneYn(rs.getString("done_yn"))
	                    .deleteYn(rs.getString("delete_yn"))
	                    .createDate(TimeConverter.fromTimestamp(rs.getTimestamp("create_date")))
	                    .updateDate(TimeConverter.fromTimestamp(rs.getTimestamp("update_date")))
	                    .deleteDate(TimeConverter.fromTimestamp(rs.getTimestamp("delete_date")))                  
	                    .build();
				
			}
			
		} catch (Exception e) {
			System.out.println("findByIdx 오류 발생 : " + e.getMessage());
		}
		
		DBConn.close(conn, pstmt, rs);
		
		return todoEntity;
		
	}
	
	public static Integer insert(TodoEntity todoEntity){
		
		final String sql = 
				" INSERT INTO todo " +
                " ( content, done_yn, delete_yn, create_date ) " +
                " VALUES " +
                " ( ?1, ?2, ?3, ?4 ) ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		Integer count = 0;
		
		try {
			conn = DBConn.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, todoEntity.getContent());
			pstmt.setString(2, todoEntity.getDoneYn());
			pstmt.setString(3, todoEntity.getDeleteYn());
			pstmt.setTimestamp(4, TimeConverter.toTimestamp(todoEntity.getCreateDate()));
			
			count = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("insert 오류 발생 : " + e.getMessage());
		}
		
		DBConn.close(conn, pstmt);
		
		return count;
		
	}
	
	public static Integer update(TodoEntity todoEntity){
		
		final String sql = 
				" UPDATE todo " +
                " SET " +
                " content = ?1, " +
                " done_yn = ?2, " +
                " delete_yn = ?3, " +
                " create_date = ?4, " +
                " update_date = ?5, " +
                " delete_date = ?6 " +
                " WHERE	idx = ?7 ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		Integer count = 0;
		
		try {
			conn = DBConn.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, todoEntity.getContent());
			pstmt.setString(2, todoEntity.getDoneYn());
			pstmt.setString(3, todoEntity.getDeleteYn());
			pstmt.setTimestamp(4, TimeConverter.toTimestamp(todoEntity.getCreateDate()));
			pstmt.setTimestamp(5, TimeConverter.toTimestamp(todoEntity.getUpdateDate()));
			pstmt.setTimestamp(6, TimeConverter.toTimestamp(todoEntity.getDeleteDate()));
			pstmt.setInt(7, todoEntity.getIdx());
			
			count = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("update 오류 발생 : " + e.getMessage());
		}
		
		DBConn.close(conn, pstmt);
		
		return count;
		
	}
}





