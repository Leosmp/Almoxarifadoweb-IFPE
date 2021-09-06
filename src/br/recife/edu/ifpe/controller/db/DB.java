package br.recife.edu.ifpe.controller.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;

public class DB {
	
private static Connection conn = null;

private static final String URL = "jdbc:mysql://localhost:3306/almoxarifadoweb?useSSL=false&useTimezone=true&serverTimezone="+ TimeZone.getDefault().getID();
private static final String USER = "leonardo";
private static final String PASSWORD = "1234567";

	
	public static Connection getConnection() {
		if(conn == null) {
			try {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (SQLException e){
				throw new DbException(e.getMessage());
			}
		}
		
		return conn;
	}
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e){
				throw new DbException(e.getMessage());				
			}
		}
	}
	
	public static void closeStatement(Statement st) {
		if(st!=null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

}
