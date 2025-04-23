/**
 * Package Name : com.pcwk.ehr.dao.test <br/>
 * Class Name: MainMemberDao.java <br/>
 * Description:  <br/>
 * Modification imformation : <br/> 
 * ------------------------------------------<br/>
 * 최초 생성일 : 2025-04-18<br/>
 *
 * ------------------------------------------<br/>
 * @author :user
 * @since  :2024-09-09
 * @version: 0.5
 */
package com.pcwk.ehr.dao.test;

import java.util.Scanner;

import com.pcwk.ehr.member.dao.MemberDao;
import com.pcwk.ehr.member.vo.MemberVO;
import com.pcwk.ehr.service.LoginService;

public class MainMemberDao {
	public static MemberVO inputMember() {
		Scanner s = new Scanner(System.in);

		System.out.print("ID 입력: ");
		String id = s.nextLine();

		System.out.print("PW 입력: ");
		String pw = s.nextLine();

		System.out.print("이름 입력: ");
		String name = s.nextLine();

		return new MemberVO(id, pw, name, "일반");
	}

	public static void main(String[] args) {
		MemberDao dao = new MemberDao();
		LoginService loginService = new LoginService(dao);

		loginService.loginMenu();

	}

}
