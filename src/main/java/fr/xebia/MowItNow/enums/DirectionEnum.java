package fr.xebia.MowItNow.enums;

public enum DirectionEnum {
	NORD('N'), EST('E'), OUEST('W'), SUD('S');

	private char direction;

	DirectionEnum(char direction) {
		this.direction = direction;
	}

	public char getDirection() {
		return direction;
	}

}
