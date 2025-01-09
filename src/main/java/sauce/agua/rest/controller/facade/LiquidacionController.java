/**
 * 
 */
package sauce.agua.rest.controller.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sauce.agua.rest.service.facade.LiquidacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping({"/liquidacion", "/api/core/liquidacion"})
public class LiquidacionController {

	private final LiquidacionService service;

	public LiquidacionController(LiquidacionService service) {
		this.service = service;
	}

	@GetMapping("/generatePdf/{prefijoId}/{facturaId}")
	public ResponseEntity<Resource> generatePdf(@PathVariable Integer prefijoId, @PathVariable Long facturaId)
			throws FileNotFoundException {
		String filename = service.generatePdf(prefijoId, facturaId);
		File file = new File(filename);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=liquidacion.pdf");
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	@GetMapping("/sendLiquidacion/{prefijoId}/{facturaId}")
	public String sendLiquidacion(@PathVariable Integer prefijoId, @PathVariable Long facturaId)
			throws MessagingException {
		return service.sendLiquidacion(prefijoId, facturaId);
	}

}
