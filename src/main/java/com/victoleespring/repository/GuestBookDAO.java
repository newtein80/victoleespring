package com.victoleespring.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.victoleespring.exception.GuestbookException;
import com.victoleespring.vo.GuestBookVO;

@Repository
public class GuestBookDAO {
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost/guestbooks?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "root", "#skdlf12");
		} catch (ClassNotFoundException e) {
			System.out.println(" 드라이버 로딩 실패 ");
		}

		return conn;
	}

	// 방명록 게시글 리스트를 조회하는 메서드
	public List<GuestBookVO> getList() {
		List<GuestBookVO> list = new ArrayList<GuestBookVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT no, name, pwd, content, reg_date" + " FROM guestbook" + " ORDER BY no desc";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				GuestBookVO vo = new GuestBookVO();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setPwd(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setRegDate(rs.getString(5));

				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		}

		return list;
	}

	// 게시글을 등록하는 메서드
	public boolean insert(GuestBookVO vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			//String sql = "INSERT INTO guestbook VALUES (null, ?, password(?), ?, '2019-06-08' )";
			String sql = "INSERT INTO guestbook VALUES (null, ?, password(?), ?, (SELECT SYSDATE()) )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPwd());
			pstmt.setString(3, vo.getContent());
			int count = pstmt.executeUpdate();

			result = (count == 1);
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new GuestbookException();
		}
		return result;
	}

	// 게시글 삭제를 위해 게시글에 설정된 비밀번호를 조회하는 메서드
	public String getPwd(int no) {
		String pwd = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT pwd FROM guestbook WHERE no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();

			// 조회되는 데이터가 1개여도 rs.next() 메서드를 호출해야 한다.
			if (rs.next()) {
				pwd = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pwd;
	}

	// 게시글 삭제를 위해 삭제하려는 사용자가 입력한 비밀번호를 MySQL에서 암호화해서 조회하는 메서드
	public String getInputPwd(String pwd) {
		String parsePwd = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT password(?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, pwd);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				parsePwd = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return parsePwd;
	}

	// 게시글을 삭제하는 메서드
	public boolean delete(int no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "DELETE FROM guestbook WHERE no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, no);
			int count = pstmt.executeUpdate();

			result = (count == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

}