/**
 * 파일명:MemberVO.java <br/>
 * 생성일:2025-04-18 
 */
package com.pcwk.ehr.member.vo;

import com.pcwk.ehr.cmn.DTO;

public class MemberVO extends DTO {

	private String id;
	private String pw;
	private String name;
	private String role;
	public MemberVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public MemberVO(String id, String pw, String name, String role) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.role = role;
	}
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", pw=" + pw + ", name=" + name + ", role=" + role + "]";
	}

}