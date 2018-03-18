package br.com.pedrosa.arquitetura.ribbon;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RibbonClient( name = "ping-a-server", configuration = RibbonConfiguration.class)
public class RibbontApplication {
	
	@LoadBalanced
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
 
    @Autowired
    RestTemplate restTemplate;
 
    @RequestMapping("/myclient")
    public String serverLocation(HttpServletRequest request) throws UnknownHostException {
    	String cliente = restTemplate.getForObject("http://ping-server/clientes", String.class);
    	System.out.println("host" + InetAddress.getLocalHost().getHostAddress());
    	System.out.println("host external" + InetAddress.getLoopbackAddress().getHostAddress());
    	
        return cliente;
    }
 
    
	public static void main(String[] args) {
		SpringApplication.run(RibbontApplication.class, args);
	}
}

