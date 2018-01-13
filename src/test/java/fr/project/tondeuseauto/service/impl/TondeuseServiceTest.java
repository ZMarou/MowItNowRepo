package fr.project.tondeuseauto.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import fr.project.tondeuseauto.constant.ParamConstant;
import fr.project.tondeuseauto.exception.IllegalInstructionException;
import fr.project.tondeuseauto.model.Instruction;
import fr.project.tondeuseauto.model.Pelouse;
import fr.project.tondeuseauto.model.Tondeuse;
import fr.project.tondeuseauto.service.AbstractServiceTest;
import fr.project.tondeuseauto.service.DataService;
import fr.project.tondeuseauto.service.TondeuseService;

public class TondeuseServiceTest extends AbstractServiceTest {

	private static String FILE_NAME = "FileMocks.txt";

	@InjectMocks
	@Autowired
	private TondeuseService tondeuseService;

	@Mock
	private DataService dataService;

	private Map<String, Object> data;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		data = new HashMap<>();
		Pelouse pelouse = new Pelouse(5, 5);
		Tondeuse tondeuse1 = new Tondeuse(1, 2, 'N');
		Tondeuse tondeuse2 = new Tondeuse(3, 3, 'E');
		Instruction instruction1 = new Instruction("GAGAGAGAA");
		Instruction instruction2 = new Instruction("AADAADADDA");
		List<Tondeuse> tondeuses = new ArrayList<>();
		tondeuses.add(tondeuse1);
		tondeuses.add(tondeuse2);
		List<Instruction> instructions = new ArrayList<>();
		instructions.add(instruction1);
		instructions.add(instruction2);
		data.put(ParamConstant.PELOUSE_DIMENSIONS_ENTRY, pelouse);
		data.put(ParamConstant.TONDEUSE_COORDINATES_ENTRY, tondeuses);
		data.put(ParamConstant.TONDEUSE_INSTRUCTIONS_ENTRY, instructions);
	}

	@Test
	public void test_move_tendeuses() throws IOException, URISyntaxException, IllegalInstructionException {
		// Given
		Mockito.when(dataService.readDataFomFile(Mockito.anyString())).thenReturn(data);
		Tondeuse tondeuseExpected1 = new Tondeuse(1, 3, 'N');
		Tondeuse tondeuseExpected2 = new Tondeuse(5, 1, 'E');
		String filePath = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
		// when
		List<Tondeuse> result = tondeuseService.moveTondeuses(filePath);
		// then
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(tondeuseExpected1, result.get(0));
		assertEquals(tondeuseExpected2, result.get(1));
	}

	@Test(expected=IOException.class)
	public void test_throw_exception_if_file_not_exists()
			throws IOException, URISyntaxException, IllegalInstructionException {
		// Given
		Mockito.doThrow(new IOException()).when(dataService).readDataFomFile(Mockito.anyString());
		String filePath = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
		// when
		try {
			tondeuseService.moveTondeuses(filePath);
		} catch (IOException e) {
			//then
			Mockito.verify(dataService, Mockito.times(1)).readDataFomFile(Mockito.anyString());
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=IllegalInstructionException.class)
	public void test_throw_exception_if_instruction_not_valid() throws IOException, URISyntaxException, IllegalInstructionException {
		//Given
		Tondeuse tondeuse3 = new Tondeuse(3, 3, 'E');
		Instruction instructionInvalid = new Instruction("AADAADADDAY");
		((ArrayList<Tondeuse>) data.get(ParamConstant.TONDEUSE_COORDINATES_ENTRY)).add(tondeuse3);
		((ArrayList<Instruction>) data.get(ParamConstant.TONDEUSE_INSTRUCTIONS_ENTRY)).add(instructionInvalid);
		Mockito.when(dataService.readDataFomFile(Mockito.anyString())).thenReturn(data);
		String filePath = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
		// when
		try {
			tondeuseService.moveTondeuses(filePath);
		} catch (IllegalInstructionException e) {
			//then
			Mockito.verify(dataService, Mockito.times(1)).readDataFomFile(Mockito.anyString());
			assertEquals("The instrction Y is not valid !!", e.getMessage());
			throw e;
		}
	}

}
