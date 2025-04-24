/**
 * Package Name : com.pcwk.ehr.service <br/>
 * Class Name: UserService.java <br/>
 * Description:  <br/>
 * Modification imformation : <br/> 
 * ------------------------------------------<br/>
 * 최초 생성일 : 2025-04-22<br/>
 *
 * ------------------------------------------<br/>
 * @author :Seung
 * @since  :2024-09-09
 * @version: 0.5
 */
package com.pcwk.ehr.service;

import java.util.List;
import java.util.Scanner;

import com.pcwk.ehr.member.dao.MemberDao;
import com.pcwk.ehr.member.vo.MemberVO;

public class LoginService {
	private MemberDao dao;
	private MemberVO loggedInUser;
	private AdminService adminService;
	private UserService userService;

	boolean run = true;

	public LoginService(MemberDao dao) {
		this.dao = dao; // DAO 생성
		this.adminService = new AdminService(dao, this);
		this.userService = new UserService(this);
	}

	/**
	 * 
	 */
	public LoginService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberVO getLoggedInUser() {
		return loggedInUser;
	}

	public void loginMenu() {
		Scanner s = new Scanner(System.in);
		while (run) {
			System.out.println("╔════════════════════════════════════════╗");
			System.out.println("║                AyCar \uD83D\uDE97     	 	 ║");
			System.out.println("╠════════════════════════════════════════╣");
			System.out.println("║  1. 회원가입                  		 ║");
			System.out.println("║  2. 로그인	                   	 ║");
			System.out.println("║  3. 종료	                   	 ║");
			System.out.println("╚════════════════════════════════════════╝");
			System.out.print("▶ 번호를 선택하세요: ");

			try {
				int select = Integer.parseInt(s.nextLine());
				switch (select) {
				case 1:
					signUp();
					break;
				case 2:
					login();
					break;
				case 3:
					System.out.println("프로그램을 종료합니다.");
					System.exit(0); // 프로그램 종료
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력 해주세요");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.out.println("잘못된 입력입니다. 숫자만 입력 해주세요.");
			}
		}
	}

	// 회원 가입
	public void signUp() {
		Scanner s = new Scanner(System.in);

		System.out.print("ID 입력: ");
		String id = s.nextLine();

		System.out.print("PW 입력: ");
		String pw = s.nextLine();

		System.out.print("이름 입력: ");
		String name = s.nextLine();

		MemberVO newMember = new MemberVO(id, pw, name, "일반");

		int result = dao.signUp(newMember); // Dao 안 signUP 호출

		if (result == 1) {
			System.out.println("회원가입 성공!");
		} else {
			System.out.println("회원가입 실패!");
		}

		while (true) {
			System.out.print("로그인 하시겠습니까? (Y/N): ");
			String answer = s.nextLine().trim().toUpperCase();

			if ("Y".equals(answer)) {
				login(); // 로그인 흐름으로 이동
				break;
			} else if ("N".equals(answer)) {
				System.out.println("프로그램을 종료합니다.");
				System.exit(0); // 프로그램 강제 종료
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}

	// 로그인
	public void login() {
		Scanner s = new Scanner(System.in);

		System.out.print("ID 입력: ");
		String id = s.nextLine();

		System.out.print("PW 입력: ");
		String pw = s.nextLine();

		MemberVO user = dao.logIn(id, pw);

		if (user != null) {
			loggedInUser = user; // 로그인 성공 → 저장
			System.out.println("로그인 성공! " + user.getName() + "님 환영합니다.");

			if ("관리자".equals(loggedInUser.getRole())) {
				adminService.adminMenu();
			} else if ("일반".equals(loggedInUser.getRole())) {
				userService.UserStart();
			}
		} else {
			System.out.println("로그인 실패!");
		}
	}

	// 로그 아웃
	public void logout() {
		if (this.loggedInUser != null) {
			System.out.println(this.loggedInUser.getName() + "님, 로그아웃 되었습니다.");
			this.loggedInUser = null;
		} else {
			System.out.println("로그인 상태가 아닙니다.");
		}
	}

	// 전체 조회
	public void showAllMembers() {
		if (loggedInUser == null) {
			System.out.println("먼저 로그인 해주세요.");
			return;
		}

		if (!"관리자".equals(loggedInUser.getRole())) {
			System.out.println("권한이 없습니다. 관리자만 접근 가능합니다.");
			return;
		}

		List<MemberVO> memberList = dao.doRetrieve(null);
		System.out.println("====== 회원 목록 ======");
		for (MemberVO member : memberList) {
			System.out.println("ID: " + member.getId() + ", 이름: " + member.getName() + ", 권한: " + member.getRole());
		}
	}
}
