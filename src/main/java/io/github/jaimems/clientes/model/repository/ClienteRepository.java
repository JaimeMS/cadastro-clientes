package io.github.jaimems.clientes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.jaimems.clientes.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
 