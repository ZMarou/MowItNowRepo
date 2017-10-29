package fr.project.tendeuseauto.service;

import java.io.IOException;
import java.util.List;

import fr.project.tendeuseauto.exception.IllegalInstructionException;
import fr.project.tendeuseauto.model.Tondeuse;

public interface TondeuseService {

	List<Tondeuse> moveTondeuses(String filePath) throws IOException, IllegalInstructionException;
}
