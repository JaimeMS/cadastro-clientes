package io.github.jaimems.clientes.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.jaimems.clientes.model.entity.Cliente;
import io.github.jaimems.clientes.model.entity.ServicoPrestado;
import io.github.jaimems.clientes.model.repository.ClienteRepository;
import io.github.jaimems.clientes.model.repository.ServicoPrestadoRepository;
import io.github.jaimems.clientes.rest.dto.ServicoPrestadoDTO;
import io.github.jaimems.clientes.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoController {
	
	//as variáveis com FINAL são obrigatórias no contrutor da aplicação
	//desta forma o Spring já faz a injeção sem precisar de annotations
	//@RequiredArgsConstructor vem do lombok e já cria um construtor com as variáveis
	//obrigatórias (que estão declaradas com FINAL)
	private final ClienteRepository clienteRepository;
	private final ServicoPrestadoRepository repository;
	private final BigDecimalConverter bigDecimalConverter;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto) {			
		LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Integer idCliente = dto.getIdCliente();	
		
		//o Optional pode estar nulo ou estar com um Objeto dentro
		//Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);
		Cliente cliente = clienteRepository.findById(idCliente)
					.orElseThrow(() -> 
					new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inexistente."));
		
		
		ServicoPrestado servicoPrestado = new ServicoPrestado();
		servicoPrestado.setDescricao(dto.getDescricao());
		servicoPrestado.setData(data);
		servicoPrestado.setCliente(cliente);
		servicoPrestado.setValor(bigDecimalConverter.converter(dto.getPreco()));
		
		return repository.save(servicoPrestado);
	}
	
	
	@GetMapping
	public List<ServicoPrestado> pesquisar(
			@RequestParam(value = "nome", required = false, defaultValue = "") String nome,
			@RequestParam(value = "mes", required = false) Integer mes
			){
		return repository.findByNomeClienteAndMes("%" + nome + "%", mes);
	}
	
}







