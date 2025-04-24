/**
 * Package Name : com.pcwk.ehr.service <br/>
 * Class Name: UserService.java <br/>
 * Description:  <br/>
 * Modification imformation : <br/> 
 * ------------------------------------------<br/>
 * 최초 생성일 : 2025-04-23<br/>
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

public class UserService {
	private LoginService loginService;
	CarDao carDao = new CarDao();

	Scanner s = new Scanner(System.in);

	public UserService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void UserStart() {
		while (true) {
			System.out.println("╔════════════════════════════════════════╗");
			System.out.println("║         유저 메뉴 (User Mode)   	         ║");
			System.out.println("╠════════════════════════════════════════╣");
			System.out.println("║  1. 차량 조회                         	 ║");
			System.out.println("║  2. 차량 예약                         	 ║");
			System.out.println("║  3. 차량 비교                         	 ║");
			System.out.println("║  4. 회원 삭제                           	 ║");
			System.out.println("║  5. 로그아웃 / 이전 메뉴                  	 ║");
			System.out.println("╚════════════════════════════════════════╝");

			try {
				int select = Integer.parseInt(s.nextLine());

				switch (select) {
				case 1:
					carCheck();
					checkMenu();
					break;
				case 2:
					carReserve();
					break;
				case 3:
					System.out.println("차량 비교");
					break;
				case 4:
					System.out.println("회원 삭제");
					break;
				case 5:
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

	public void checkMenu() {
		while (true) {
			System.out.println("╔════════════════════════════════════════╗");
			System.out.println("║         조회 메뉴 (User Mode)   	         ║");
			System.out.println("╠════════════════════════════════════════╣");
			System.out.println("║  1. 브랜드별 조회                        	 ║");
			System.out.println("║  2. 가격별 조회                         	 ║");
			System.out.println("║  3. 차종별 조회                         	 ║");
			System.out.println("║  4. 이전 메뉴                        	 ║");
			System.out.println("╚════════════════════════════════════════╝");

			try {
				int select = Integer.parseInt(s.nextLine());

				switch (select) {
				case 1:
					String brand = "";

					System.out.println("조회할 브랜드를 입력해 주세요");
					brand = s.nextLine();

					brandCheck(brand);
					break;
				case 2:
					int price = 0;

					System.out.println("조회할 가격대를 입력해 주세요.");
					price = Integer.parseInt(s.nextLine());

					priceCheck(price);
					break;
				case 3:
					String shape = "";

					System.out.println("조회할 차종을 입력해 주세요. \n small, medium, Large");
					shape = s.nextLine();

					shapeCheck(shape);
					break;
				case 4:
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

	// 차량 예약
	public void carReserve() {
		Scanner sc = new Scanner(System.in);

		System.out.println("예약할 차량 번호 : ");
		String carNo = sc.nextLine();

		// 차량 정보 조회
		List<CarVO> carList = carDao.doRetrieve(null);

		boolean found = false;

		for (CarVO car : carList) {
			if (car.getCarNo().equals(carNo)) {
				car.setReserve(true);
				found = true;
				break;
			}
		}

		if (found) {
			carDao.reserve(carList); // 예약 내용 전체 저장
			System.out.println("예약이 완료 되었습니다.");
		} else {
			System.out.println("차량 정보가 없습니다.");
		}

	}

	// 1. 전체 차량 출력
	public void carCheck() {
		System.out.println("📋 전체 차량 목록:");
		System.out.printf("%-6s %-13s %-8s %-8s %-8s %-12s %-8s %-8s %-10s %-8s%n", "브랜드", "모델명", "차종", "가격", "색상",
				"차량번호", "연료", "연식", "주행거리", "예약여부");
		System.out.println(
				"-------------------------------------------------------------------------------------------------------");
		List<CarVO> carList = carDao.doRetrieve(null);
		for (CarVO car : carList) {
			String reserveStatus = car.getReserve() ? "예약" : "";
			System.out.printf("%-8s %-10s %-10s %-8d %-8s %-10s %-10s %-12d %-10d %-8s%n", car.getBrand(),
					car.getModel(), car.getSize(), car.getPrice(), car.getColor(), car.getCarNo(), car.getFuel(),
					car.getModelYear(), car.getDistance(), car.getReserve());
		}
	}

	// 2. 제조사별 차량 조회
	public void brandCheck(String brand) {
		List<CarVO> carList = carDao.doRetrieve(null);
		System.out.println("🚗 제조사 [" + brand + "] 차량 목록:");
		System.out.printf("%-6s %-10s %-8s %-6s %-8s %-10s %-8s %-8s %-8s %-8s%n", "브랜드", "모델명", "차종", "가격", "색상",
				"차량번호", "연료", "연식", "주행거리", "예약여부");
		System.out.println(
				"----------------------------------------------------------------------------------------------------");
		for (CarVO car : carList) {
			if (car.getBrand().equalsIgnoreCase(brand)) {

				System.out.printf("%-8s %-10s %-10s %-8d %-8s %-10s %-10s %-8d %-10d %-8b%n", car.getBrand(),
						car.getModel(), car.getSize(), car.getPrice(), car.getColor(), car.getCarNo(), car.getFuel(),
						car.getModelYear(), car.getDistance(), car.getReserve());

			}
		}

	}

	// 3. 가격대별 차량 조회
	public void priceCheck(int maxPrice) {
		List<CarVO> carList = carDao.doRetrieve(null);
		System.out.println("💰 " + maxPrice + "만원 이하 차량 목록:");
		System.out.printf("%-6s %-10s %-8s %-6s %-8s %-10s %-8s %-8s %-8s %-8s%n", "브랜드", "모델명", "차종", "가격", "색상",
				"차량번호", "연료", "연식", "주행거리", "예약여부");
		System.out.println(
				"----------------------------------------------------------------------------------------------------");
		for (CarVO car : carList) {
			if (car.getPrice() <= maxPrice) {
				System.out.printf("%-8s %-10s %-10s %-8d %-8s %-10s %-10s %-8d %-10d %-8b%n", car.getBrand(),
						car.getModel(), car.getSize(), car.getPrice(), car.getColor(), car.getCarNo(), car.getFuel(),
						car.getModelYear(), car.getDistance(), car.getReserve());
			}
		}

	}

	// 4. 차종(사이즈)별 차량 조회
	public void shapeCheck(String size) {
		List<CarVO> carList = carDao.doRetrieve(null);
		System.out.println("🚙 차종 [" + size + "] 차량 목록:");
		System.out.printf("%-6s %-10s %-8s %-6s %-8s %-10s %-8s %-8s %-8s %-8s%n", "브랜드", "모델명", "차종", "가격", "색상",
				"차량번호", "연료", "연식", "주행거리", "예약여부");
		System.out.println(
				"----------------------------------------------------------------------------------------------------");
		for (CarVO car : carList) {
			if (car.getSize().equalsIgnoreCase(size)) {
				System.out.printf("%-8s %-7s %-9s %-8d %-8s %-10s %-10s %-11d %-8d %-8b%n", car.getBrand(),
						car.getModel(), car.getSize(), car.getPrice(), car.getColor(), car.getCarNo(), car.getFuel(),
						car.getModelYear(), car.getDistance(), car.getReserve());
			}
		}

	}

}
