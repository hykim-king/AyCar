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

import java.util.Scanner;

public class UserService {
	private LoginService loginService;

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

			int select = Integer.parseInt(s.nextLine());

			switch (select) {
			case 1:
				System.out.println("차량조회");
				break;
			case 2:
				System.out.println("차량 예약");
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
		}

	}

}
