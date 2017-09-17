package fr.xebia.MowItNow.model;

public class Instruction {

	private String instruction;

	public Instruction() {
		super();
	}

	public Instruction(String instruction) {
		super();
		this.instruction = instruction;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instruction == null) ? 0 : instruction.hashCode());
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
		Instruction other = (Instruction) obj;
		if (instruction == null) {
			if (other.instruction != null)
				return false;
		} else if (!instruction.equals(other.instruction))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Instruction [instruction=" + instruction + "]";
	}

}
