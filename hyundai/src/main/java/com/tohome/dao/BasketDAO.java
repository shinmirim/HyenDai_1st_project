package com.tohome.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.tohome.dto.BasketDTO;
import com.tohome.dto.ProductDTO;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleType;
import oracle.jdbc.OracleTypes;
import util.JDBConnect;

public class BasketDAO extends JDBConnect {
	private BasketDAO() {
	}

	private static BasketDAO instance = new BasketDAO();

	public static BasketDAO getInstance() {
		return instance;
	}

	// Written by 여명, 승준
	public int CountBasketItem(int userNo) {
		int basketCount = 0;
		String query = "{call basket_pkg.count_basket(?,?)}"; // 쿼리문 템플릿

		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;

		try {
			con = JDBConnect.getConnection();
			// 쿼리 실행
			cstmt = con.prepareCall(query); // 동적 쿼리문 준비

			cstmt.setInt(1, userNo);
			cstmt.registerOutParameter(2, OracleType.NUMBER);
			cstmt.executeQuery();
			basketCount = cstmt.getInt(2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBConnect.close();
		}

		return basketCount;
	}

	// Written by 여명, 승준
	public ArrayList<BasketDTO> ImportUsersBasket(int userNo) {
		ArrayList<BasketDTO> userBasketList = new ArrayList<BasketDTO>();
		String query = "{call basket_pkg.import_user_basket(?,?)}"; // 쿼리문 템플릿

		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			con = JDBConnect.getConnection();
			// 쿼리 실행
			cstmt = con.prepareCall(query); // 동적 쿼리문 준비
			cstmt.setInt(1, userNo);

			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			// OracleTypes.CURSOR를 사용할려면 ojdbc8.jar 안에있는 거라 라이브러리 추가 같은 세팅 해야함

			// CallableStatement를 실행
			cstmt.executeQuery();

			// getCursor() 메서드를 사용하기 위해 OracleCallableStatement Object로 변환
			// CallableStatement는 getCursor() 메서드가 정의되어 있지 않기 때문
			OracleCallableStatement ocstmt = (OracleCallableStatement) cstmt;
			// ResultSet에 결과 데이터를 담은 Cursor를 저장
			rs = ocstmt.getCursor(2);

			while (rs.next()) {
				BasketDTO dto = new BasketDTO();

				dto.setBasket_detail_no(rs.getInt("detail_no"));
				dto.setProd_no(rs.getInt("prod_no"));
				dto.setProd_img(rs.getString("prod_img"));
				dto.setProd_name(rs.getString("prod_name"));
				dto.setPrice(rs.getInt("price"));
				dto.setProd_quantity(rs.getInt("prod_quantity"));
				dto.setSale_rate(rs.getInt("sale_rate"));

				userBasketList.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBConnect.close();
		}

		return userBasketList;
	}

	// Written by 여명, 승준
	public int InsertBasket(int userNo, int prodId, int prodQuant) {
		int result = 0;
		String query = "{call basket_pkg.insert_basket_detail(?,?,?)}"; // 쿼리문 템플릿

		Connection con = null;
		CallableStatement cstmt = null;

		ResultSet rs = null;
		try {
			con = JDBConnect.getConnection();
			// 쿼리 실행
			cstmt = con.prepareCall(query); // 동적 쿼리문 준비
			cstmt.setInt(1, userNo);
			cstmt.setInt(2, prodId);
			cstmt.setInt(3, prodQuant);

			// CallableStatement를 실행
//	          result = cstmt.executeQuery();
			cstmt.executeUpdate();

			result = CountBasketItem(userNo);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBConnect.close();
		}
		return result;
	}

	// Written by 여명, 승준
	public int basketDelete(int userNo, String prodIds) {
		// plsql에서 execute immadiate 에서 여러개의 id값을 받아 삭제하기위해 prodIds를 String으로 받는다
		// 그러면 pl/sql에서 문자를 int로 자동 형변환 해줌과 동시에 마치 배열처럼 받아 쓸 수 있게 된다
		int result = 0;
		String query = "{call basket_pkg.delete_basket_detail(?,?)}"; // 쿼리문 템플릿

		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			con = JDBConnect.getConnection();
			// 쿼리 실행

			cstmt = con.prepareCall(query); // 동적 쿼리문 준비
			cstmt.setInt(1, userNo);
			cstmt.setString(2, prodIds);

			// CallableStatement를 실행
			// 삭제해서 executeUpdate() 해야하는줄 알았는데 계속 반환값을 안줘서 무한 로딩이라 그냥 실행쿼리 해야했네
			cstmt.executeQuery();

			result = CountBasketItem(userNo);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBConnect.close();
		}
		return result;
	}

}
