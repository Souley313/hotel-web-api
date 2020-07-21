package dev.hotel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@WebMvcTest(ClientCtrl.class)
class ClientCtrlTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ClientRepository repo;
	
	protected List<Client> clients = new ArrayList<Client>();
	
	@BeforeEach
	void setUp() {
		Client client = new Client("Odd", "Ross");
		client.setUuid(UUID.fromString("dcf129f1-a2f9-47dc-8265-1d844244b192"));
		clients.add(client);
	}

	@Test
	void Should_ReturnAllClients() throws Exception {

		Page<Client> p = new PageImpl<>(this.clients);

		Mockito.when(repo.findAll(PageRequest.of(0, 1))).thenReturn(p);

		mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=0&size=1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].uuid").value("dcf129f1-a2f9-47dc-8265-1d844244b192"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Odd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].prenoms").value("Ross"));
	}

}
