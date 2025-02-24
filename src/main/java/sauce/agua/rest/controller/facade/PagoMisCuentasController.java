/**
 * 
 */
package sauce.agua.rest.controller.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sauce.agua.rest.service.facade.PagoMisCuentasService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping({"/pagomiscuentas", "/api/core/pagomiscuentas"})
public class PagoMisCuentasController {

	private final PagoMisCuentasService service;

	public PagoMisCuentasController(PagoMisCuentasService service) {
		this.service = service;
	}

	@GetMapping("/generate/{filename_return}/{desde}/{hasta}")
	public ResponseEntity<Resource> generate(@PathVariable String filename_return,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime desde,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime hasta) throws IOException {
		String filename = service.generate(desde, hasta);
		File file = new File(filename); 
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename_return);
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

}
