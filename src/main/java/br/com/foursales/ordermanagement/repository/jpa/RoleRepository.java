package br.com.foursales.ordermanagement.repository.jpa;

import br.com.foursales.ordermanagement.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String nome);

}
