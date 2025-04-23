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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.car.dao.CarDao;
import com.pcwk.ehr.cmn.Cardiv;
import com.pcwk.ehr.member.vo.MemberVO;

public class MemberDao implements Cardiv<MemberVO>, PLog {

	public static final String MEMBER_DATA = ".\\data\\member.csv";
	private List<MemberVO> members = new ArrayList<MemberVO>();
	CarDao car = new CarDao();

	public void start(MemberVO dto) {
		Scanner sc = new Scanner(System.in);
		System.out.println("╔════════════════════════════════════════╗");
		System.out.println("║             AyCar         		 ║");
		System.out.println("╠════════════════════════════════════════╣");
		System.out.println("║  1. 회원 정보 수정                	    	 ║");
		System.out.println("║  2. 회원 정보 삭제                	      	 ║");
		System.out.println("║  3. 차량 정보 조회                	      	 ║");
		System.out.println("║  4. 로그아웃	               	 	 ║");
		System.out.println("╚════════════════════════════════════════╝");

		System.out.println(dto.toString());
		int answer = sc.nextInt();
		if (answer == 1) {
			doUpdate(dto);
		} else if (answer == 2) {
			doDelete(dto);
		} else if( answer == 3) {
			
		}

	}

	// 로그인
	public void logIn() {

	}

	// 로그아웃
	public void logOut() {

	}

	// 회원가입
	public void signUp() {

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

		start(members.getFirst());
		return members;
	}

	@Override
	public int doSave(MemberVO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MemberVO doSelectOne(MemberVO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberVO> doRetrieve(MemberVO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int doUpdate(MemberVO dto) {
		Scanner sc = new Scanner(System.in);
		String pw;
		System.out.println("변경할 비밀번호 : ");
		pw = sc.nextLine();
		dto.setPw(pw);

		System.out.println("변경이 완료되었습니다.");

		start(dto);
		return 0;
	}

	@Override
	public int doDelete(MemberVO dto) {
		Scanner sc = new Scanner(System.in);

		System.out.println("삭제할 아이디 : ");
		String id = sc.nextLine();
				
		for (MemberVO vo : members) {
			
			if(vo.getId().equals(id)) {
				members.remove(vo);
				System.out.println("삭제되었습니다.");
				break;
			}
		}

		String next = sc.nextLine();
		start(dto);
		return 0;
	}

}
