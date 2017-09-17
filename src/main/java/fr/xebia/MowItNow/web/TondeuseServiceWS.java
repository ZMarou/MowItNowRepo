package fr.xebia.MowItNow.web;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.xebia.MowItNow.exception.IllegalInstructionException;
import fr.xebia.MowItNow.service.TondeuseService;

@RestController
public class TondeuseServiceWS {

	private static Logger log = Logger.getLogger(TondeuseServiceWS.class.getName());

	@Autowired
	private TondeuseService tondeuseService;

	@GetMapping("/move")
	public void moveTondeuses() {
		log.info("Start TondeuseServiceWS.moveTondeuses");
		try {
			tondeuseService.moveTondeuses("");
		} catch (IOException | IllegalInstructionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("End TondeuseServiceWS.moveTondeuses");
	}
}
