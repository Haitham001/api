package com.lelasoft.tools;

public enum Membership {

	ADMIN("System Admin"),
	HOSPITAL_TEAM("Medical Team"),
	USER("User");

	private String name;
	private Membership(String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
