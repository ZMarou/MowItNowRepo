package fr.project.tendeuseauto.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import fr.project.tendeuseauto.constant.ParamConstant;
import fr.project.tendeuseauto.model.Instruction;
import fr.project.tendeuseauto.model.Pelouse;
import fr.project.tendeuseauto.model.Tondeuse;
import fr.project.tendeuseauto.service.DataService;

@Service
public class DataServiceImpl implements DataService {

	private static Logger log = Logger.getLogger(DataServiceImpl.class.getName());

	private static int index;
	
	private static Pelouse pelouse;

	@Override
	public Map<String, Object> readDataFomFile(String filePath) throws IOException {
		log.info("Start DataServiceImpl.readPosition");
		Map<String, Object> result = new HashMap<>();
		List<Tondeuse> tondeuses = new ArrayList<>();
		List<Instruction> instructions = new ArrayList<>();
		pelouse = null;
		index = 0;
		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
			stream.forEach(line -> {
				if (index == 0) {
					pelouse = addPelouse(line);
				}else {
					processData(line, tondeuses, instructions);
				}
				index++;
			});
		}
		buildResult(result,pelouse, tondeuses, instructions);
		log.info("End DataServiceImpl.readPosition");
		return result;
	}

	private void buildResult(Map<String, Object> result, Pelouse pelouse, List<Tondeuse> tondeuses,
			List<Instruction> instructions) {
		result.put(ParamConstant.PELOUSE_DIMENSIONS_ENTRY, pelouse);
		result.put(ParamConstant.TONDEUSE_COORDINATES_ENTRY, tondeuses);
		result.put(ParamConstant.TONDEUSE_INSTRUCTIONS_ENTRY, instructions);
	}

	private void processData(String line, List<Tondeuse> tondeuses, List<Instruction> instructions) {
		if (index % 2 != 0) {
			addTondeuse(line, tondeuses);
		} else {
			addInstruction(line, instructions);
		}
	}

	private void addInstruction(String line, List<Instruction> instructions) {
		Instruction instruction = new Instruction(line);
		log.info(instruction.toString());
		instructions.add(instruction);
	}

	private void addTondeuse(String line, List<Tondeuse> tondeuses) {
		List<String> coordinates = Pattern.compile(" ").splitAsStream(line).map(s-> s.trim()).collect(Collectors.toList());
		Tondeuse tondeuse = new Tondeuse(Integer.valueOf(coordinates.get(0)), Integer.valueOf(coordinates.get(1)),
				coordinates.get(2).charAt(0));
		log.info(tondeuse.toString());
		tondeuses.add(tondeuse);
	}

	private Pelouse addPelouse(String line) {
		List<String> dimensions = Pattern.compile(" ").splitAsStream(line).map(s->s.trim()).collect(Collectors.toList());
		Pelouse pelouse = new Pelouse(Integer.valueOf(dimensions.get(0)), Integer.valueOf(dimensions.get(1)));
		log.info(pelouse.toString());
		return pelouse;
	}

}
