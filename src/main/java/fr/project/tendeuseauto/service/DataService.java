package fr.project.tendeuseauto.service;

import java.io.IOException;
import java.util.Map;

public interface DataService {

	Map<String, Object> readDataFomFile(String filePath) throws IOException;
}
