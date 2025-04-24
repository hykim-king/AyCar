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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pcwk.ehr.car.dao.CarDao;
import com.pcwk.ehr.car.vo.CarVO;
import com.pcwk.ehr.member.dao.MemberDao;
import com.pcwk.ehr.member.vo.MemberVO;

public class AdminService {
	private LoginService loginService;
	private MemberDao dao;
	private CarDao carDao = new CarDao();
	Logger LOG = LogManager.getLogger();

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
			LOG.debug("▶ 1. 뒤로가기");
			LOG.debug("▶ 2. 종료");

			try {
				int select = Integer.parseInt(s.nextLine());

				switch (select) {
				case 1:
					return;
				case 2:
					System.exit(0);
					break;
				default:
					LOG.debug("잘못된 입력입니다. 다시 선택해주세요.");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				LOG.debug("잘못된 입력입니다. 숫자만 입력 해주세요.");
			}

		}
	}

	// 관리자 메뉴
	public void adminMenu() {
		Scanner s = new Scanner(System.in);

		while (true) {
			LOG.debug("");
			LOG.debug("╔════════════════════════════════════════╗");
			LOG.debug("║         관리자 메뉴 (Admin Mode)   	 ║");
			LOG.debug("╠════════════════════════════════════════╣");
			LOG.debug("║  1. 회원 전체 조회                    	 ║");
			LOG.debug("║  2. 회원 단건 조회                    	 ║");
			LOG.debug("║  3. 회원 수정                         	 ║");
			LOG.debug("║  4. 회원 삭제                           	 ║");
			LOG.debug("║  5. 전체 차량 조회                    	 ║");
			LOG.debug("║  6. 예약 차량 조회                    	 ║");
			LOG.debug("║  7. 차량 예약 취소                    	 ║");
			LOG.debug("║  8. 차량 정보 수정                        	 ║");
			LOG.debug("║  9. 차량 정보 삭제                        	 ║");
			LOG.debug("║  10. 로그아웃 / 이전 메뉴                  	 ║");
			LOG.debug("╚════════════════════════════════════════╝");
			LOG.debug("▶ 번호를 선택하세요: ");

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
					carCheck();
					break;
				case 6:
					carReserveCheck();
					break;
				case 7:
					carReserveCancel();
					break;
				case 8:
					carUpdate();
					break;
				case 9:
					carDelete();
					break;
				case 10:
					loginService.logout();
					LOG.debug("로그 아웃하고 이전 메뉴로 돌아갑니다.");
					return;
				default:
					LOG.debug("잘못된 입력입니다. 다시 선택해주세요.");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				LOG.debug("잘못된 입력입니다. 숫자만 입력 해주세요.");
			}
		}
	}

	// 회원 전체 조회
	public void showAllMembers() {
		List<MemberVO> members = dao.doRetrieve(null);

		LOG.debug("\n====== 회원 전체 목록 ======");
		for (MemberVO member : members) {
			LOG.debug("ID: " + member.getId() + ", 이름: " + member.getName() + ", 권한: " + member.getRole());
		}
		LOG.debug("===========================");

		backMenu();
	}

	public void showSingleMember() {
		Scanner s = new Scanner(System.in);
		LOG.debug("조회할 회원의 ID를 입력하세요: ");
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
			LOG.debug("===== 회원 정보 =====");
			LOG.debug("ID: " + foundMember.getId());
			LOG.debug("이름: " + foundMember.getName());
			LOG.debug("권한: " + foundMember.getRole());
		} else {
			LOG.debug("해당 ID를 가진 회원이 존재하지 않습니다.");
		}
		backMenu();

	}

	public void updateMember() {
		Scanner sc = new Scanner(System.in);

		LOG.debug("비밀번호를 변경할 회원 ID를 입력하세요: ");
		String id = sc.nextLine();

		List<MemberVO> memberList = dao.doRetrieve(null);

		boolean found = false;

		for (MemberVO m : memberList) {
			if (m.getId().equals(id)) {
				LOG.debug("새 비밀번호 입력: ");
				String newPw = sc.nextLine();

				m.setPw(newPw); // 비밀번호 변경
				found = true;
				break;
			}
		}

		if (found) {
			dao.doUpdate(memberList); // 변경 내용 전체 저장
			LOG.debug("비밀번호가 성공적으로 변경되었습니다.");
		} else {
			LOG.debug("해당 ID의 회원을 찾을 수 없습니다.");
		}
	}

	public void deleteMember() {
		Scanner sc = new Scanner(System.in);

		LOG.debug("삭제할 아이디 : ");
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
			LOG.debug("회원을 성공적으로 삭제 했습니다.");
		} else {
			LOG.debug("회원 삭제를 실패했습니다.");
		}
	}
	// 1. 전체 차량 출력
	public void carCheck() {
		LOG.debug("📋 전체 차량 목록:");
		System.out.printf("%-6s %-13s %-8s %-8s %-8s %-12s %-8s %-8s %-10s %-8s%n", "브랜드", "모델명", "차종", "가격", "색상",
				"차량번호", "연료", "연식", "주행거리", "예약여부");
		LOG.debug(
				"-------------------------------------------------------------------------------------------------------");
		List<CarVO> carList = carDao.doRetrieve(null);
		for (CarVO car : carList) {
			String reserveStatus = car.getReserve() ? "예약" : "";
			System.out.printf("%-8s %-10s %-10s %-8d %-8s %-10s %-10s %-12d %-10d %-8s%n", car.getBrand(),
					car.getModel(), car.getSize(), car.getPrice(), car.getColor(), car.getCarNo(), car.getFuel(),
					car.getModelYear(), car.getDistance(), car.getReserve());
		}
	}
	

	// 예약 차량 조회
	public void carReserveCheck() {
		List<CarVO> carList = carDao.doRetrieve(null);
		System.out.printf("%-6s %-13s %-8s %-8s %-8s %-12s %-8s %-8s %-10s %-8s%n", "브랜드", "모델명", "차종", "가격", "색상",
				"차량번호", "연료", "연식", "주행거리", "예약여부");
		for (CarVO car : carList) {
			if (car.getReserve() == true) {
				System.out.printf("%-8s %-10s %-10s %-8d %-8s %-10s %-10s %-12d %-10d %-8s%n", car.getBrand(),
						car.getModel(), car.getSize(), car.getPrice(), car.getColor(), car.getCarNo(), car.getFuel(),
						car.getModelYear(), car.getDistance(), car.getReserve() ?  "예약" : "");
			}

		}
		backMenu();
	}

	// 차량 예약 취소
	public void carReserveCancel() {
		Scanner sc = new Scanner(System.in);

		LOG.debug("예약 취소할 차량 번호 : ");
		String carNo = sc.nextLine();

		// 차량 정보 조회
		List<CarVO> carList = carDao.doRetrieve(null);

		boolean found = false;

		for (CarVO car : carList) {
			if (car.getCarNo().equals(carNo) && car.getReserve() == true) {
				car.setReserve(false);
				found = true;
				break;
			}
		}

		if (found) {
			carDao.carReserveCancel(carList); // 예약 내용 전체 저장
			LOG.debug("예약 취소가 완료 되었습니다.");
		} else {
			LOG.debug("예약된 차량이 아닙니다.");
		}
	}

	// 차량 정보 삭제
	public void carDelete() {
		Scanner c = new Scanner(System.in);

		LOG.debug("삭제할 차량 번호(carNo)를 입력하세요.");
		String carNo = c.nextLine();

		List<CarVO> carList = carDao.doRetrieve(null);

		boolean found = false;

		for (CarVO car : carList) {
			if (car.getCarNo().equals(carNo) == true) {
				found = true;
				carList.remove(car);
				break;
			}
		}

		if (found) {
			carDao.doDelete(carList);
			LOG.debug("차량 삭제 되었습니다.");
		} else {
			LOG.debug("해당 차량이 존재하지 않습니다.");
		}
	}

	// 차량 정보 수정
	public void carUpdate() {
		Scanner s = new Scanner(System.in);
		LOG.debug("수정할 차량 번호를 입력하세요.");
		String carNo = s.nextLine();
		List<CarVO> carList = carDao.doRetrieve(null);
		boolean found = false;

		for (CarVO car : carList) {
			if (car.getCarNo().equals(carNo) == true) {
				LOG.debug("수정할 금액을 입력하세요.");
				int rePrice = s.nextInt();
				car.setPrice(rePrice);
				found = true;
				break;
			}
		}
		if (found) {
			carDao.doUpdate(carList);
			LOG.debug("차량 금액이 변경 되었습니다.");
		} else {
			LOG.debug("금액을 수정할 수 없습니다.");
		}
	}

	

}
