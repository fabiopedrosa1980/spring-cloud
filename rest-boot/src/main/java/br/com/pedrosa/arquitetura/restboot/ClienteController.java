package br.com.pedrosa.arquitetura.restboot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/clientes")
class ClienteController {
	
	@GetMapping
	@HystrixCommand(fallbackMethod = "fallbackCliente")
	public String getClientes() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		clientes.add(new Cliente(1l, "TOKIO"));
		//return clientes.get(0).getNome();
      throw new RuntimeException("Simulating downstream system failure");
	}
	 
	public String fallbackCliente() {
		//List<Cliente> clientes = new ArrayList<Cliente>();
		//clientes.add(new Cliente(1l, "Cliente de Outro BD"));
		return "circuit breaker acionado";
	    //return new ArrayList<>();
	}

}