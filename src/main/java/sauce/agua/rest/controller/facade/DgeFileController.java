/**
 * 
 */
package sauce.agua.rest.controller.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

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

import sauce.agua.rest.service.facade.DgeFileService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping({"/dgefile", "/api/core/dgefile"})
public class DgeFileController {

	private final DgeFileService service;

	public DgeFileController(DgeFileService service) {
		this.service = service;
	}

	@GetMapping("/generate/{periodoId}")
	public ResponseEntity<Resource> generate(@PathVariable Integer periodoId)
			throws IOException {
		List<Long> clienteIds = List.of(167L, 168L, 218L, 255L);
		String filename = service.generate(periodoId, clienteIds);
		File file = new File(filename);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

}
