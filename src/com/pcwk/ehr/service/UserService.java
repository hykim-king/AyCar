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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pcwk.ehr.car.dao.CarDao;
import com.pcwk.ehr.car.vo.CarVO;

public class UserService {
	private LoginService loginService;
	
	CarDao carDao = new CarDao();
	Logger LOG = LogManager.getLogger();

	Scanner s = new Scanner(System.in);

	public UserService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void UserStart() {
		while (true) {
			LOG.debug("╔════════════════════════════════════════╗");
			LOG.debug("║         유저 메뉴 (User Mode)   	         ║");
			LOG.debug("╠════════════════════════════════════════╣");
			LOG.debug("║  1. 차량 조회                         	 ║");
			LOG.debug("║  2. 차량 예약                         	 ║");
			LOG.debug("║  3. 차량 비교                         	 ║");
			LOG.debug("║  4. 로그아웃 / 이전 메뉴                  	 ║");
			LOG.debug("╚════════════════════════════════════════╝");

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
					carCompare();	
					break;
				case 4:
					loginService.logout();
					LOG.debug("로그 아웃하고 이전 메뉴로 돌아갑니다.");
					return;
				default:
					LOG.debug("잘못된 입력입니다. 다시 선택해주세요.");
				}
			} catch (NumberFormatException e) {
				LOG.debug("잘못된 입력입니다. 숫자만 입력 해주세요.");
			}

		}

	}

	public void checkMenu() {
		while (true) {
			LOG.debug("╔════════════════════════════════════════╗");
			LOG.debug("║         조회 메뉴 (User Mode)   	         ║");
			LOG.debug("╠════════════════════════════════════════╣");
			LOG.debug("║  1. 브랜드별 조회                        	 ║");
			LOG.debug("║  2. 가격별 조회                         	 ║");
			LOG.debug("║  3. 차종별 조회                         	 ║");
			LOG.debug("║  4. 이전 메뉴                        	 ║");
			LOG.debug("╚════════════════════════════════════════╝");

			try {
				int select = Integer.parseInt(s.nextLine());

				switch (select) {
				case 1:
					String brand = "";

					LOG.debug("조회할 브랜드를 입력해 주세요");
					brand = s.nextLine();

					brandCheck(brand);
					break;
				case 2:
					int price = 0;

					LOG.debug("조회할 가격대를 입력해 주세요.");
					price = Integer.parseInt(s.nextLine());

					priceCheck(price);
					break;
				case 3:
					String shape = "";

					LOG.debug("조회할 차종을 입력해 주세요. \n small, medium, Large");
					shape = s.nextLine();

					shapeCheck(shape);
					break;
				case 4:
					return;
				default:
					LOG.debug("잘못된 입력입니다. 다시 선택해주세요.");
				}
			} catch (NumberFormatException e) {
				LOG.debug("잘못된 입력입니다. 숫자만 입력 해주세요.");
			}
		}

	}

	// 차량 예약
	public void carReserve() {
		Scanner sc = new Scanner(System.in);

		LOG.debug("예약할 차량 번호 : ");
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
			LOG.debug("예약이 완료 되었습니다.");
		} else {
			LOG.debug("차량 정보가 없습니다.");
		}

	}

	// 1. 전체 차량 출력
	public void carCheck() {
		LOG.debug("📋 전체 차량 목록:");
		System.out.printf("%-8s %-11s %-8s %-8s %-7s %-12s %-7s %-8s %-10s %-8s%n", "브랜드", "모델명", "차종", "가격", "색상",
				"차량번호", "연료", "연식", "주행거리", "예약여부");
		LOG.debug(
				"-------------------------------------------------------------------------------------------------------");
		List<CarVO> carList = carDao.doRetrieve(null);
		for (CarVO car : carList) {
			String reserveStatus = car.getReserve() ? "예약" : "";
			System.out.printf("%-8s %-10s %-10s %-8d %-8s %-10s %-10s %-12d %-10d %-8s%n", car.getBrand(),
					car.getModel(), car.getSize(), car.getPrice(), car.getColor(), car.getCarNo(), car.getFuel(),
					car.getModelYear(), car.getDistance(), car.getReserve()?"예약" : "");
		}
	}

	// 2. 제조사별 차량 조회
	public void brandCheck(String brand) {
		List<CarVO> carList = carDao.doRetrieve(null);
		LOG.debug("🚗 제조사 [" + brand + "] 차량 목록:");
		System.out.printf("%-8s %-11s %-8s %-8s %-7s %-10s %-8s %-6s %-10s %-8s%n", "브랜드", "모델명", "차종", "가격", "색상",
				"차량번호", "연료", "연식", "주행거리", "예약여부");
		LOG.debug(
				"----------------------------------------------------------------------------------------------------");
		for (CarVO car : carList) {
			if (car.getBrand().equalsIgnoreCase(brand)) {

				System.out.printf("%-8s %-10s %-10s %-8d %-8s %-10s %-10s %-8d %-10d %-8s%n", car.getBrand(),
						car.getModel(), car.getSize(), car.getPrice(), car.getColor(), car.getCarNo(), car.getFuel(),
						car.getModelYear(), car.getDistance(), car.getReserve()?"예약" : "");

			}
		}

	}

	// 3. 가격대별 차량 조회
	public void priceCheck(int maxPrice) {
		List<CarVO> carList = carDao.doRetrieve(null);
		LOG.debug("💰 " + maxPrice + "만원 이하 차량 목록:");
		System.out.printf("%-8s %-11s %-8s %-8s %-7s %-10s %-8s %-6s %-10s %-8s%n", "브랜드", "모델명", "차종", "가격", "색상",
				"차량번호", "연료", "연식", "주행거리", "예약여부");
		LOG.debug(
				"----------------------------------------------------------------------------------------------------");
		for (CarVO car : carList) {
			if (car.getPrice() <= maxPrice) {
				System.out.printf("%-8s %-10s %-10s %-8d %-8s %-10s %-10s %-8d %-10d %-8s%n", car.getBrand(),
						car.getModel(), car.getSize(), car.getPrice(), car.getColor(), car.getCarNo(), car.getFuel(),
						car.getModelYear(), car.getDistance(), car.getReserve()?"예약" : "");
			}
		}

	}

	// 4. 차종(사이즈)별 차량 조회
	public void shapeCheck(String size) {
		List<CarVO> carList = carDao.doRetrieve(null);
		LOG.debug("🚙 차종 [" + size + "] 차량 목록:");
		System.out.printf("%-8s %-8s %-8s %-7s %-6s %-10s %-8s %-7s %-11s %-8s%n", "브랜드", "모델명", "차종", "가격", "색상",
				"차량번호", "연료", "연식", "주행거리", "예약여부");
		LOG.debug(
				"----------------------------------------------------------------------------------------------------");
		for (CarVO car : carList) {
			if (car.getSize().equalsIgnoreCase(size)) {
				System.out.printf("%-8s %-7s %-9s %-8d %-8s %-10s %-10s %-11d %-8d %-8s%n", car.getBrand(),
						car.getModel(), car.getSize(), car.getPrice(), car.getColor(), car.getCarNo(), car.getFuel(),
						car.getModelYear(), car.getDistance(), car.getReserve()?"예약" : "");
			}
		}

	}
	public void carCompare() {
		Scanner v = new Scanner(System.in);
		LOG.debug("비교할 차량 번호를 입력하세요.");
		LOG.debug("1. 차량 번호를 입력하세요.");

		String carNo01 = v.nextLine();
		LOG.debug("2. 차량 번호를 입력하세요.");
		String carNo02 = v.nextLine();
		List<CarVO> carList = carDao.doRetrieve(null);

		for (CarVO car : carList) {
			if (car.getCarNo().equals(carNo01)) {
				System.out.printf("%-8s %-11s %-8s %-8s %-7s %-12s %-7s %-8s %-10s %-8s%n", "브랜드", "모델명", "차종", "가격", "색상",
						"차량번호", "연료", "연식", "주행거리km", "예약여부");
				System.out.printf("%-6s %-10s %-10s %-8d %-8s %-10s %-10s %-12d %-10d %-8s%n", car.getBrand(),
						car.getModel(), car.getSize(), car.getPrice(), car.getColor(), car.getCarNo(), car.getFuel(),
						car.getModelYear(), car.getDistance(), car.getReserve() ?  "예약" : "");

			}
			if (car.getCarNo().equals(carNo02)) {
				System.out.printf("%-6s %-10s %-10s %-8d %-8s %-10s %-10s %-12d %-10d %-8s%n", car.getBrand(),
						car.getModel(), car.getSize(), car.getPrice(), car.getColor(), car.getCarNo(), car.getFuel(),
						car.getModelYear(), car.getDistance(), car.getReserve() ?  "예약" : "");

			}

		}
		v.nextLine();

	}

}
