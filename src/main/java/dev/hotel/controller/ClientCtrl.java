package dev.hotel.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.dto.ClientDto;
import dev.hotel.entite.Client;
import dev.hotel.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientCtrl {

	@Autowired
	ClientService clientSer;

	/**
	 * @param clientRepository
	 */
	public ClientCtrl(ClientService clientSer) {
		super();
		this.clientSer = clientSer;
	}

	// GET /clients?start=X&size=Y
	@GetMapping
	public ResponseEntity<?> listerClient(@RequestParam("start") Integer start, @RequestParam("size") Integer size) {
		if (start == null || size == null || start < 0 || size <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientSer.findAll(start, size));

	}

	// GET /clients/UUID
	@GetMapping("/{chaineUuid}")
	public ResponseEntity<?> FindByUUID(@PathVariable String chaineUuid) {
		UUID uuid = null;
		try {
			uuid = UUID.fromString(chaineUuid);
		} catch (IllegalArgumentException i) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Spécifiez un UUID valide.\n" + i.getMessage());
		}
		Optional<Client> client = clientSer.findByUuid(uuid);
		if (!client.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(client);
		}
	}
	
	/**
	 * methode qui permet de creer un client dans la BDD à partir de données
	 * comprises dans un JSON
	 * 
	 * @param client : le nom et le prenom du client, au format JSON
	 * @return : le client
	 */
	//POST /clients
	@PostMapping
	public ResponseEntity<?> postClients(@RequestBody @Valid ClientDto client, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le nom et le prénom doivent être valorisés");
		}
		Client clientBase = clientSer.creerClient(client.getNom(), client.getPrenoms());
		return ResponseEntity.status(HttpStatus.OK).body(clientBase);
	}

}
