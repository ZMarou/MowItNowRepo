package fr.xebia.MowItNow.model;

public class Pelouse {

	private int length, width;

	public Pelouse() {
		super();
	}

	public Pelouse(int length, int width) {
		super();
		this.length = length;
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + length;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pelouse other = (Pelouse) obj;
		if (length != other.length)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pelouse [length=" + length + ", width=" + width + "]";
	}

}
