package fr.project.tondeuseauto.service;

import java.io.IOException;
import java.util.List;

import fr.project.tondeuseauto.exception.IllegalInstructionException;
import fr.project.tondeuseauto.model.Tondeuse;

public interface TondeuseService {

	List<Tondeuse> moveTondeuses(String filePath) throws IOException, IllegalInstructionException;
}
