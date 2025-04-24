/**
 * Package Name : com.pcwk.ehr.service <br/>
 * Class Name: AdminService.java <br/>
 * Description:  <br/>
 * Modification imformation : <br/> 
 * ------------------------------------------<br/>
 * 최초 생성일 : 2025-04-22<br/>
 *
 * ------------------------------------------<br/>
 * @author :user
 * @since  :2024-09-09
 * @version: 0.5
 */
package com.pcwk.ehr.service;

import java.util.List;
import java.util.Scanner;

import com.pcwk.ehr.car.dao.CarDao;
import com.pcwk.ehr.car.vo.CarVO;
import com.pcwk.ehr.member.dao.MemberDao;
import com.pcwk.ehr.member.vo.MemberVO;

public class AdminService {
	private LoginService loginService;
	private MemberDao dao;
	private CarDao carDao = new CarDao();

	/**
	 * @param dao
	 */
	public AdminService(MemberDao dao, LoginService loginService) {
		super();
		this.dao = dao;
		this.loginService = loginService;
	}

	public void backMenu() {
		Scanner s = new Scanner(System.in);

		while (true) {
			System.out.println("▶ 1. 뒤로가기");
			System.out.println("▶ 2. 종료");

			try {
				int select = Integer.parseInt(s.nextLine());

				switch (select) {
				case 1:
					return;
				case 2:
					System.exit(0);
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.out.println("잘못된 입력입니다. 숫자만 입력 해주세요.");
			}

		}
	}

	// 관리자 메뉴
	public void adminMenu() {
		Scanner s = new Scanner(System.in);

		while (true) {
			System.out.println();
			System.out.println("╔════════════════════════════════════════╗");
			System.out.println("║         관리자 메뉴 (Admin Mode)   	 ║");
			System.out.println("╠════════════════════════════════════════╣");
			System.out.println("║  1. 회원 전체 조회                    	 ║");
			System.out.println("║  1. 회원 단건 조회                    	 ║");
			System.out.println("║  3. 회원 수정                         	 ║");
			System.out.println("║  4. 회원 삭제                           	 ║");
			System.out.println("║  5. 예약 차량 조회                    	 ║");
			System.out.println("║  6. 차량 예약 취소                    	 ║");
			System.out.println("║  7. 로그아웃 / 이전 메뉴                  	 ║");
			System.out.println("╚════════════════════════════════════════╝");
			System.out.print("▶ 번호를 선택하세요: ");

			try {
				int select = Integer.parseInt(s.nextLine());

				switch (select) {
				case 1:
					showAllMembers();
					break;
				case 2:
					showSingleMember();
					break;
				case 3:
					updateMember();
					break;
				case 4:
					deleteMember();
					break;
				case 5:
					carReserveCheck();
					break;
				case 6:
					carReserveCancel();
					break;
				case 7:
					loginService.logout();
					System.out.println("로그 아웃하고 이전 메뉴로 돌아갑니다.");
					return;
				default:
					System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.out.println("잘못된 입력입니다. 숫자만 입력 해주세요.");
			}
		}
	}

	// 회원 전체 조회
	public void showAllMembers() {
		List<MemberVO> members = dao.doRetrieve(null);

		System.out.println("\n====== 회원 전체 목록 ======");
		for (MemberVO member : members) {
			System.out.println("ID: " + member.getId() + ", 이름: " + member.getName() + ", 권한: " + member.getRole());
		}
		System.out.println("===========================");

		backMenu();
	}

	public void showSingleMember() {
		Scanner s = new Scanner(System.in);
		System.out.print("조회할 회원의 ID를 입력하세요: ");
		String inputId = s.nextLine();

		List<MemberVO> memberList = dao.doRetrieve(null); // 전체 조회

		MemberVO foundMember = null;
		for (MemberVO member : memberList) {
			if (member.getId().equals(inputId)) {
				foundMember = member;
				break;
			}
		}

		if (foundMember != null) {
			System.out.println("===== 회원 정보 =====");
			System.out.println("ID: " + foundMember.getId());
			System.out.println("이름: " + foundMember.getName());
			System.out.println("권한: " + foundMember.getRole());
		} else {
			System.out.println("해당 ID를 가진 회원이 존재하지 않습니다.");
		}
		backMenu();

	}

	public void updateMember() {
		Scanner sc = new Scanner(System.in);

		System.out.print("비밀번호를 변경할 회원 ID를 입력하세요: ");
		String id = sc.nextLine();

		List<MemberVO> memberList = dao.doRetrieve(null);

		boolean found = false;

		for (MemberVO m : memberList) {
			if (m.getId().equals(id)) {
				System.out.print("새 비밀번호 입력: ");
				String newPw = sc.nextLine();

				m.setPw(newPw); // 비밀번호 변경
				found = true;
				break;
			}
		}

		if (found) {
			dao.doUpdate(memberList); // 변경 내용 전체 저장
			System.out.println("비밀번호가 성공적으로 변경되었습니다.");
		} else {
			System.out.println("해당 ID의 회원을 찾을 수 없습니다.");
		}
	}

	public void deleteMember() {
		Scanner sc = new Scanner(System.in);

		System.out.print("삭제할 아이디 : ");
		String id = sc.nextLine();

		// 1. 현재 회원 목록 조회
		List<MemberVO> memberList = dao.doRetrieve(null);
		boolean found = false;

		// 2. 삭제 대상 회원 제거
		for (int i = 0; i < memberList.size(); i++) {
			if (memberList.get(i).getId().equals(id)) {
				memberList.remove(i);
				found = true;
				break;
			}
		}
		// 3. 회원 목록 저장
		if (found) {
			dao.doDelete(memberList); // 변경 내용 전체 저장
			System.out.println("회원을 성공적으로 삭제 했습니다.");
		} else {
			System.out.println("회원 삭제를 실패했습니다.");
		}
	}
	
	//예약 차량 조회
	public void carReserveCheck() {
		List<CarVO> carList = carDao.doRetrieve(null);
		for (CarVO vo : carList) {
			if (vo.getReserve() == true) {
				System.out.println(vo);
			}

		}
		backMenu();
	}
	
	
	//차량 예약 취소
	public void carReserveCancel() {
		Scanner sc = new Scanner(System.in);

		System.out.println("예약 취소할 차량 번호 : ");
		String carNo = sc.nextLine();

		// 차량 정보 조회
		List<CarVO> carList = carDao.doRetrieve(null);

		boolean found = false;

		for (CarVO car : carList) {
			if (car.getCarNo().equals(carNo)&&car.getReserve() == true) {
				car.setReserve(false);
				found = true;
				break;
			}
		}

		if (found) {
			carDao.carReserveCancel(carList); // 예약 내용 전체 저장
			System.out.println("예약 취소가 완료 되었습니다.");
		} else {
			System.out.println("예약된 차량이 아닙니다.");
		}
	}

}
