package fr.project.tondeuseauto.service.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import fr.project.tondeuseauto.constant.ParamConstant;
import fr.project.tondeuseauto.model.Instruction;
import fr.project.tondeuseauto.model.Tondeuse;
import fr.project.tondeuseauto.service.AbstractServiceTest;
import fr.project.tondeuseauto.service.DataService;

public class DataServiceTest extends AbstractServiceTest {

	@InjectMocks
	@Autowired
	private DataService dataService;

	private static String FILE_NAME = "FileMocks.txt";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_read_data_from_file() throws URISyntaxException, IOException {
		// Given
		String filePath = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
		// when
		Map<String, Object> result = dataService.readDataFomFile(filePath);
		// Then
		assertTrue("data not found", isDataFound(result));
		assertTrue("pelouse dimensions not found", isPelouseDimensionsFound(result));
		assertTrue("tondeuse coordinates not found", isTondeuseCoordinatesExists(result));
		assertTrue("tondeuse instructions not found", isTondeuseInstructionsExists(result));
		assertTrue("data is incorrect", isDataCorrect(result));
	}

	@Test(expected = IOException.class)
	public void test_file_if_not_exists() throws URISyntaxException, IOException {
		// Given
		String filePath = "nothing.txt";
		// when
		dataService.readDataFomFile(filePath);
	}

	private boolean isDataFound(Map<String, Object> result) {
		return result != null && !result.isEmpty();
	}

	private boolean isPelouseDimensionsFound(Map<String, Object> result) {
		return result.get(ParamConstant.PELOUSE_DIMENSIONS_ENTRY) != null;
	}

	private boolean isTondeuseCoordinatesExists(Map<String, Object> result) {
		return result.get(ParamConstant.TONDEUSE_COORDINATES_ENTRY) != null;
	}

	private boolean isTondeuseInstructionsExists(Map<String, Object> result) {
		return result.get(ParamConstant.TONDEUSE_INSTRUCTIONS_ENTRY) != null;
	}

	@SuppressWarnings("unchecked")
	private boolean isDataCorrect(Map<String, Object> result) {
		List<Tondeuse> tondeuses = (ArrayList<Tondeuse>) result.get(ParamConstant.TONDEUSE_COORDINATES_ENTRY);
		List<Instruction> instructions = (ArrayList<Instruction>) result.get(ParamConstant.TONDEUSE_INSTRUCTIONS_ENTRY);
		return tondeuses.size() == instructions.size();
	}

}
