package db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class DB1 {
	private Statement stmt;
	private Connection conn;
	private ResultSet rs;

	//public static void main(String[] args) {
	//	DB1 d = new DB1();
	//}

	public DB1() {
		try {
			String url = "jdbc:mysql://localhost:3306/bank_db2";
			String username = "root";
			String password = "1234";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int update(String sql) {
		try {
			return stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int[] updateBatch(LinkedList<String> sql) {
		for (int i = 0; i < sql.size(); i++) {
			try {
				stmt.addBatch(sql.get(i));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			return stmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean transaction(LinkedList<String> sql) {
		try {
			conn.setAutoCommit(false);
			for (int i = 0; i < sql.size(); i++) {
				stmt.executeUpdate(sql.get(i));
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
				return false;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;

	}

	public LinkedList<HashMap<String, String>> queryToList(String sql) {
		ResultSet rs = query(sql);
		LinkedList<HashMap<String, String>> list = new LinkedList<HashMap<String, String>>();
		try {
			if(rs!=null){
				ResultSetMetaData mt = rs.getMetaData();
				while (rs.next()) {
					HashMap<String, String> map = new HashMap<String, String>();
					for (int i = 1; i <= mt.getColumnCount(); i++) {
						String colName = mt.getColumnName(i);
						String colValue = rs.getString(colName);
						map.put(colName, colValue);
					}
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet query(String sql) {
		try {
		
				rs = stmt.executeQuery(sql);
			return rs;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
