package cn.com.taiji.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

	private static Logger log = LoggerFactory.getLogger(SimpleController.class);
	
	@RequestMapping("/")
	public String home() {
		log.info("home get");
		return "home page";
	}
	
}
