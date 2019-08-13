package com.softwareag.testing.TCTraining.models;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int age;
	private int weight;
	private int height;
	private double bmi;

	public Person() {
	}

	public Person(int id, String name, int age, int weight, int height) {
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.height = height;
		//this.bmi = bmi;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getWeight() {
		return weight;
	}

	public int getHeight() {
		return height;
	}

	public double getBmi() {
		return bmi;
	}

	public void setBmi(double bmi) {
		this.bmi = bmi;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
