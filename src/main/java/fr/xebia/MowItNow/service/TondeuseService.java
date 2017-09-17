package fr.xebia.MowItNow.service;

import java.io.IOException;
import java.util.List;

import fr.xebia.MowItNow.exception.IllegalInstructionException;
import fr.xebia.MowItNow.model.Tondeuse;

public interface TondeuseService {

	List<Tondeuse> moveTondeuses(String filePath) throws IOException, IllegalInstructionException;
}
