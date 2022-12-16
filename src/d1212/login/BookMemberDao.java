package d1212.login;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.common.hash.Hashing;

import d1208.test.Member;
import jdbc.util.OracleUtil;

public class BookMemberDao {
	
		private static BookMemberDao bmdao = new BookMemberDao();
		private BookMemberDao() { }
		public static BookMemberDao getBookMemberDao() {
			return bmdao;
		}
		
		public boolean login(String id, String pw) throws SQLException {
			boolean b = true;
			
			Connection conn = OracleUtil.getConnection();
			String sql = "select * from book_member\r\n"
					+ "where email = trim(?) and password64 = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = null;
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
				
			rs = pstmt.executeQuery();
			if(rs.next())
				b=true;
			else 
				b=false;
			pstmt.close();
			conn.close();
			
			return b ;
		}
		
		public void updatePassword (String id, String pw) throws SQLException {
			Connection conn = OracleUtil.getConnection();
			String sql = "update book_member set password64=?\r\n"
					+ "where mem_idx=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, Hashing.sha256().hashString(pw, StandardCharsets.UTF_8).toString());
			pstmt.setString(2, id);
			
//			ResultSet rs = pstmt.executeQuery();
			// 행 값을 불러올때 사용
			
			pstmt.close();
			conn.close();
		}
		
		public void insert (int mem_idx, String name, String email, String pw) throws SQLException {
			Connection conn = OracleUtil.getConnection();
			String sql = "INSERT INTO BOOK_MEMBER (MEM_IDX, NAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, mem_idx);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			pstmt.setString(4, pw);
			
			pstmt.execute();
			pstmt.close();
			conn.close();
		}
	}
	