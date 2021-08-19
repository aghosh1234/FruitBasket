package com.mycode.fruit.basket;

public class FruitDTO {
	private String type;
	private int ageInDays;
	private String characteristic1;
	private String characteristic2;

	public FruitDTO() {
		super();
	}

	public FruitDTO(String type, int ageInDays, String characteristic1, String characteristic2) {
		super();
		this.type = type;
		this.ageInDays = ageInDays;
		this.characteristic1 = characteristic1;
		this.characteristic2 = characteristic2;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAgeInDays() {
		return ageInDays;
	}

	public void setAgeInDays(int ageInDays) {
		this.ageInDays = ageInDays;
	}

	public String getCharacteristic1() {
		return characteristic1;
	}

	public void setCharacteristic1(String characteristic1) {
		this.characteristic1 = characteristic1;
	}

	public String getCharacteristic2() {
		return characteristic2;
	}

	public void setCharacteristic2(String characteristic2) {
		this.characteristic2 = characteristic2;
	}

	@Override
	public String toString() {
		return "FruitDTO [type=" + type + ", ageInDays=" + ageInDays + ", characteristic1=" + characteristic1
				+ ", characteristic2=" + characteristic2 + "]";
	}
}
