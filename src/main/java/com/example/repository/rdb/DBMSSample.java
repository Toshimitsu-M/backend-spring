package com.example.repository.rdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DBMSのサンプルクラス
public class DBMSSample {
	public static void DbmsSample()
	{
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:derby://localhost:1527/data/Sample");
			
			System.out.println(con);
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	};
}
