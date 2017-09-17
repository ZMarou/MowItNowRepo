package fr.xebia.MowItNow.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.xebia.MowItNow.constant.ParamConstant;
import fr.xebia.MowItNow.enums.DirectionEnum;
import fr.xebia.MowItNow.exception.IllegalInstructionException;
import fr.xebia.MowItNow.model.Instruction;
import fr.xebia.MowItNow.model.Pelouse;
import fr.xebia.MowItNow.model.Tondeuse;
import fr.xebia.MowItNow.service.DataService;
import fr.xebia.MowItNow.service.TondeuseService;

@Service
public class TondeuseServiceImpl implements TondeuseService {

	private static Logger log = Logger.getLogger(TondeuseServiceImpl.class.getName());

	@Autowired
	private DataService dataService;

	
	public List<Tondeuse> moveTondeuses(String filePath) throws IOException, IllegalInstructionException {
		log.info("Start TondeuseServiceImpl.moveTondeuse");
		List<Tondeuse> tondeuses = new ArrayList<>();
		List<Instruction> instructions = new ArrayList<>();
		Pelouse pelouse = null;
		extractData(filePath, tondeuses, instructions, pelouse);
		for (int i = 0; i < tondeuses.size(); i++) {
			String instruction = instructions.get(i).getInstruction();
			Tondeuse tondeuse = tondeuses.get(i);
			runInstruction(pelouse, instruction, tondeuse);
		}
		log.info("End TondeuseServiceImpl.moveTondeuse");
		return tondeuses;
	}

	private void runInstruction(Pelouse pelouse, String instruction, Tondeuse tondeuse)
			throws IllegalInstructionException {
		for (int j = 0; j < instruction.length(); j++) {
			char order = instruction.charAt(j);
			runOrder(pelouse, tondeuse, order);
			log.info("New tondeuse position:" + tondeuse.toString());
		}
	}

	@SuppressWarnings("unchecked")
	private void extractData(String filePath, List<Tondeuse> tondeuses, List<Instruction> instructions, Pelouse pelouse) throws IOException {
		Map<String, Object> data = dataService.readDataFomFile(filePath);
		tondeuses = (ArrayList<Tondeuse>) data.get(ParamConstant.TONDEUSE_COORDINATES_ENTRY);
		instructions = (ArrayList<Instruction>) data.get(ParamConstant.TONDEUSE_INSTRUCTIONS_ENTRY);
		pelouse = (Pelouse) data.get(ParamConstant.PELOUSE_DIMENSIONS_ENTRY);
	}

	private void runOrder(Pelouse pelouse, Tondeuse tondeuse, char order) throws IllegalInstructionException {
		if (!isTendenseOutsidePelouse(pelouse, tondeuse)) {
			switch (order) {
			case 'G':
				tondeuse.setDirection(turnLeft(tondeuse.getDirection()));
				break;
			case 'D':
				tondeuse.setDirection(turnRight(tondeuse.getDirection()));
				break;
			case 'A':
				calculateNewPosition(tondeuse);
				break;
			default:
				throw new IllegalInstructionException(
						"The instrction " + order + " is not valid !!");
			}
		}
	}

	private void calculateNewPosition(Tondeuse tondeuse) {
		switch (tondeuse.getDirection()) {
		case 'N':
			tondeuse.setY(tondeuse.getY() - 1);
		case 'E':
			tondeuse.setX(tondeuse.getX() + 1);
		case 'W':
			tondeuse.setX(tondeuse.getX() - 1);
		case 'S':
			tondeuse.setY(tondeuse.getY() + 1);
		}
	}

	private boolean isTendenseOutsidePelouse(Pelouse pelouse, Tondeuse tondeuse) {
		return tondeuse.getX() > pelouse.getLength() || tondeuse.getY() > pelouse.getWidth();
	}

	private Character turnRight(char direction) {
		switch (direction) {
		case 'N':
			return DirectionEnum.EST.getDirection();
		case 'E':
			return DirectionEnum.SUD.getDirection();
		case 'W':
			return DirectionEnum.NORD.getDirection();
		case 'S':
			return DirectionEnum.OUEST.getDirection();
		default:
			return null;
		}
	}

	private Character turnLeft(char direction) {
		switch (direction) {
		case 'N':
			return DirectionEnum.OUEST.getDirection();
		case 'E':
			return DirectionEnum.NORD.getDirection();
		case 'W':
			return DirectionEnum.SUD.getDirection();
		case 'S':
			return DirectionEnum.EST.getDirection();
		default:
			return null;
		}
	}

}
