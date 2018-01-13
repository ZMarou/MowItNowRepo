package fr.project.tondeuseauto.web;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.project.tondeuseauto.exception.IllegalInstructionException;
import fr.project.tondeuseauto.model.Tondeuse;
import fr.project.tondeuseauto.service.TondeuseService;

@RestController
public class TondeuseServiceWS {

	private static Logger log = Logger.getLogger(TondeuseServiceWS.class.getName());

	@Autowired
	private TondeuseService tondeuseService;

	@PutMapping("/tondeuse")
	public ResponseEntity<String> moveTondeuses(@RequestParam(value="path", required=true) String pathFile) {
		log.info("Start TondeuseServiceWS.moveTondeuses");
		try {
			List<Tondeuse> tondeuses = tondeuseService.moveTondeuses(pathFile);
			String result = formatResult(tondeuses);
			log.info("End TondeuseServiceWS.moveTondeuses");
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (IOException e) {
			log.info("End TondeuseServiceWS.moveTondeuses");
			return new ResponseEntity<>("IOException :: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (IllegalInstructionException e) {
			log.info("End TondeuseServiceWS.moveTondeuses");
			return new ResponseEntity<>("IllegalInstructionException :: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			log.info("End TondeuseServiceWS.moveTondeuses");
			return new ResponseEntity<>("Exception :: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String formatResult(List<Tondeuse> tondeuses) {
		String result ="";
		for (Tondeuse tondeuse : tondeuses) {
			result = result.concat(tondeuse.toReturn());
		}
		return result;
	}
}
