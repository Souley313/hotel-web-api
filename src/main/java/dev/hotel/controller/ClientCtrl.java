package dev.hotel.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@RestController
@RequestMapping("/clients")
public class ClientCtrl {

	@Autowired
	ClientRepository clientRepository;

	/**
	 * @param clientRepository
	 */
	public ClientCtrl(ClientRepository clientRepository) {
		super();
		this.clientRepository = clientRepository;
	}

	// GET /clients?start=X&size=Y
	@GetMapping
	public ResponseEntity<?> listerClient(@RequestParam("start") Integer start, @RequestParam("size") Integer size) {
		if (start == null || size == null || start < 0 || size <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(clientRepository.findAll(PageRequest.of(start, size)).toList());

	}

	// GET /clients/UUID
	@GetMapping("/{uuid}")
	public ResponseEntity<?> FindByUUID(@PathVariable UUID uuid) {
		if (uuid == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Valorisez un UUID.");
		} else {
			Optional<Client> client = clientRepository.findById(uuid);
			if (!client.isPresent()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé.");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(client);
			}
		}
	}

}
