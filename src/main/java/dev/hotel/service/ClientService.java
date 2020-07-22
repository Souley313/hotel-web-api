package dev.hotel.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@Service
public class ClientService {

	private ClientRepository clientRep;

	/**
	 * @param clientRep
	 */
	public ClientService(ClientRepository clientRep) {
		super();
		this.clientRep = clientRep;
	}

	public List<Client> findAll(Integer start, Integer size) {
		// TODO Auto-generated method stub
		return clientRep.findAll(PageRequest.of(start, size)).toList();
	}

	public Optional<Client> findByUuid(UUID uuid) {
		// TODO Auto-generated method stub
		return clientRep.findById(uuid);
	}

	@Transactional
	public Client creerClient(String nom, String prenom) {
		Client cl = new Client();
		cl.setNom(nom);
		cl.setPrenoms(prenom);
		return this.clientRep.save(cl);

	}
}
