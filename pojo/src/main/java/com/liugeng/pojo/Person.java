package com.liugeng.pojo;

import lombok.Data;

@Data
public class Person {

	private int id;
	private String name;
	private double height;

	public Person(){}

	public Person(String name, double height) {
		this.name = name;
		this.height = height;
	}
}
