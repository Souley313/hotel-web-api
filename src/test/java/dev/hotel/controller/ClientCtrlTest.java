package dev.hotel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.hotel.entite.Client;
import dev.hotel.service.ClientService;

@WebMvcTest(ClientCtrl.class)
class ClientCtrlTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ClientService ser;

	@Test
	void listTest() throws Exception {
		List<Client> p = new ArrayList<>();
		Client c1 = new Client();
		c1.setUuid(UUID.fromString("dcf129f1-a2f9-47dc-8265-1d844244b192"));
		c1.setNom("Odd");
		c1.setPrenoms("Ross");
		Client c2 = new Client();
		c2.setUuid(UUID.fromString("f9a18170-9605-4fe6-83c8-d03a53e08bfe"));
		c2.setNom("Don");
		c2.setPrenoms("Duck");

		p.add(c1);
		p.add(c2);

		Mockito.when(ser.findAll(0, 1)).thenReturn(p);

		mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=0&size=1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].uuid").value("dcf129f1-a2f9-47dc-8265-1d844244b192"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Odd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].prenoms").value("Ross"));
	}

	@Test
	public void findByUuidTest() throws Exception {
		Client c1 = new Client();
		c1.setNom("Odd");
		c1.setPrenoms("Ross");
		c1.setUuid(UUID.fromString("dcf129f1-a2f9-47dc-8265-1d844244b192"));
		Optional<Client> c2 = Optional.of(c1);
		Mockito.when(ser.findByUuid(UUID.fromString("dcf129f1-a2f9-47dc-8265-1d844244b192"))).thenReturn(c2);
		mockMvc.perform(MockMvcRequestBuilders.get("/clients/dcf129f1-a2f9-47dc-8265-1d844244b192"))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.uuid").value("dcf129f1-a2f9-47dc-8265-1d844244b192"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Odd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.prenoms").value("Ross"));
	}

	@Test
	public void CreerClientTest() throws Exception {
		Client ct = new Client();
		ct.setNom("test");
		ct.setPrenoms("test");
		ct.setUuid(UUID.fromString("abc1235d-1a23-a2f9-83c8-44b192d03a53"));
		// mockito
		Mockito.when(ser.creerClient("test", "test")).thenReturn(ct);
		// json body
		String jsonBody = "{ \"nom\": \"test\", \"prenoms\": \"test\" }";
		mockMvc.perform(MockMvcRequestBuilders.post("/clients").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(jsonBody)).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.uuid").value("abc1235d-1a23-a2f9-83c8-44b192d03a53"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("test"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.prenoms").value("test"));
	}

}
