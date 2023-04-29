package io.github.jaimems.clientes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.jaimems.clientes.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
}
