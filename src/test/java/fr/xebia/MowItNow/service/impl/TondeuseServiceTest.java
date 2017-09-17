package fr.xebia.MowItNow.service.impl;

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

import fr.xebia.MowItNow.constant.ParamConstant;
import fr.xebia.MowItNow.exception.IllegalInstructionException;
import fr.xebia.MowItNow.model.Instruction;
import fr.xebia.MowItNow.model.Pelouse;
import fr.xebia.MowItNow.model.Tondeuse;
import fr.xebia.MowItNow.service.AbstractServiceTest;
import fr.xebia.MowItNow.service.DataService;
import fr.xebia.MowItNow.service.TondeuseService;

public class TondeuseServiceTest extends AbstractServiceTest{
	
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
	    Pelouse pelouse = new Pelouse(5,5);
	    Tondeuse tondeuse1 = new Tondeuse(1,2,'N');
	    Tondeuse tondeuse2 = new Tondeuse(3,3,'E');
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
		//Given
		Mockito.when(dataService.readDataFomFile(Mockito.anyString())).thenReturn(data);
		Tondeuse tondeuseExpected1 = new Tondeuse(1,3,'N');
	    Tondeuse tondeuseExpected2 = new Tondeuse(5,1,'E');
	    String filePath = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
		//when
		List<Tondeuse> result = tondeuseService.moveTondeuses(filePath);
		//then
		assertNotNull(result);
		assertEquals(2,result.size());
		assertEquals(tondeuseExpected1, result.get(0));
		assertEquals(tondeuseExpected2, result.get(1));
	}

}
