/**
 * Package Name : com.pcwk.ehr.member.dao <br/>
 * Class Name: MemberDao.java <br/>
 * Description: 회원 DAO <br/>
 * Modification imformation : <br/> 
 * ------------------------------------------<br/>
 * 최초 생성일 : 2025-04-18<br/>
 *
 * ------------------------------------------<br/>
 * @author :user
 * @since  :2024-09-09
 * @version: 0.5
 */
package com.pcwk.ehr.member.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.car.dao.CarDao;
import com.pcwk.ehr.cmn.Cardiv;
import com.pcwk.ehr.member.vo.MemberVO;
import com.pcwk.ehr.service.AdminService;

public class MemberDao implements Cardiv<MemberVO>, PLog {
	public static final String MEMBER_DATA = ".\\data\\Member.csv";
	
	CarDao car = new CarDao();
	
	// 로그인
	public MemberVO logIn(String id, String pw) {
		List<MemberVO> memberList = doRetrieve(null); // 최신 회원 목록

		for (MemberVO member : memberList) {
			if (member.getId().equals(id) && member.getPw().equals(pw)) {
				LOG.debug("로그인 성공! 환영합니다 \u263A " + member.getName() + "님.");
				return member;
			}
		}

		System.out.println("로그인 실패! ID 또는 비밀번호를 확인하세요.");
		return null;
	}

	// 회원가입
	public int signUp(MemberVO input) {
		int result = doSave(input); // 회원 등록

		return result;
	}

	// 파일에서 회원정보 읽기
	public MemberDao() {

	}

	// 등록
	@Override
	public int doSave(MemberVO dto) {
		int flag = 0;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBER_DATA, true))) {
			String line = String.join(",", dto.getId(), dto.getPw(), dto.getName(), dto.getRole());

			writer.write(line);
			writer.newLine(); // 줄바꿈
			flag = 1;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;

	}

	@Override
	public MemberVO doSelectOne(MemberVO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	// 전체 조회
	@Override
	public List<MemberVO> doRetrieve(MemberVO dto) {
		List<MemberVO> list = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(MEMBER_DATA));) {
			String line;
			while ((line = reader.readLine()) != null) {

				String[] dataArr = line.split(",");
				if (dataArr.length < 4) {
					LOG.debug("형식이 잘못 되었습니다.: " + line);
					continue;
				}
				String id = dataArr[0];// 회원ID
				String pw = dataArr[1];// 비밀번호
				String name = dataArr[2];// 이름
				String role = dataArr[3];// 권한

				MemberVO member = new MemberVO(id, pw, name, role);
				list.add(member);
			}

		} catch (FileNotFoundException e) {
			LOG.debug("FileNotFoundException:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.debug("IOException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int doUpdate(List<MemberVO> dto) {
		int flag = 0;

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBER_DATA))) {
	        for (MemberVO m : dto) {
	            String line = String.join(",", m.getId(), m.getPw(), m.getName(), m.getRole());
	            writer.write(line);
	            writer.newLine();
	        }
	        flag = 1;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return flag;
	}

	@Override
	public int doDelete(List<MemberVO> dto) {
		int flag = 0;

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBER_DATA))) {
	        for (MemberVO m : dto) {
	            String line = String.join(",", m.getId(), m.getPw(), m.getName(), m.getRole());
	            writer.write(line);
	            writer.newLine();
	        }
	        flag = 1;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return flag;
	}

}
