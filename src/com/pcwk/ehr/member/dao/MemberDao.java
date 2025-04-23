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
	private AdminService adminService;
	public static final String MEMBER_DATA = ".\\data\\Member.csv";
	private List<MemberVO> members = new ArrayList<MemberVO>();
	CarDao car = new CarDao();
	
	// 로그인
	public MemberVO logIn(String id, String pw) {
		List<MemberVO> memberList = doRetrieve(null); // 최신 회원 목록

		for (MemberVO member : memberList) {
			if (member.getId().equals(id) && member.getPw().equals(pw)) {
				System.out.println("로그인 성공! 환영합니다 \u263A " + member.getName() + "님.");
				return member;
			}
		}

		System.out.println("로그인 실패! ID 또는 비밀번호를 확인하세요.");
		return null;
	}

	// 로그아웃
	public void logOut() {

	}

	// 회원가입
	public int signUp(MemberVO input) {
		int result = doSave(input); // 회원 등록

		return result;
	}

	// 파일에서 회원정보 읽기
	public MemberDao() {
		car.carDao();
		getMemberReadFile(MEMBER_DATA);
	}

	// 회원정보 파일을 읽고 -> List<MemberVO>
	/**
	 * 회원정보 파일 to List<MemberVO>
	 * 
	 * @param path
	 * @return
	 */
	public List<MemberVO> getMemberReadFile(String path) {

		// 1. file read
		// 2. 읽은 한줄 -> List<MemberVO>
		// 3. List<MemberVO> members에 추가
		try (BufferedReader memberReader = new BufferedReader(new FileReader(path));) {
			String line;
			while ((line = memberReader.readLine()) != null) {
				// System.out.println(line);
				// pcwk01,이상무01,4321a,yejiad12@gmail.com,0,2025-04-18,일반 to MamberVO

				String[] dataArr = line.split(",");

				String id = dataArr[0];// 회원ID
				String pw = dataArr[1];// 비밀번호
				String name = dataArr[2];// 이름
				String role = dataArr[3];// 권한

				MemberVO memberVO = new MemberVO(id, pw, name, role);

				// System.out.println(memberVO);
				members.add(memberVO);
			}

			for (MemberVO vo : members) {
				System.out.println(vo);
			}

		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			e.printStackTrace();
		}

		return members;
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
	public int doUpdate(MemberVO dto) {
		Scanner sc = new Scanner(System.in);
		String pw;
		System.out.println("변경할 비밀번호 : ");
		pw = sc.nextLine();
		dto.setPw(pw);

		System.out.println("변경이 완료되었습니다.");

		adminService.backMenu();
		return 0;
	}

	@Override
	public int doDelete(MemberVO dto) {
		Scanner sc = new Scanner(System.in);

		System.out.println("삭제할 아이디 : ");
		String id = sc.nextLine();

		for (MemberVO vo : members) {

			if (vo.getId().equals(id)) {
				members.remove(vo);
				System.out.println("삭제되었습니다.");
				break;
			}
		}

		String next = sc.nextLine();
		return 0;
	}

}
