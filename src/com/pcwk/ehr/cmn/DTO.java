/**
 * Package Name : com.pcwk.ehr.cmn <br/>
 * Class Name: DTO.java <br/>
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
package com.pcwk.ehr.cmn;

/**
 * Value Object 조상 
 */
public class DTO {
	
	private String searchDiv; //검색구문
	private String searchWord; //검색어
	
	/**
	 * @return the searchDiv
	 */
	public String getSearchDiv() {
		return searchDiv;
	}
	/**
	 * @param searchDiv the searchDiv to set
	 */
	public void setSearchDiv(String searchDiv) {
		this.searchDiv = searchDiv;
	}
	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}
	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
}
