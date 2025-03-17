package br.com.foursales.ordermanagement.repository.jpa;

import br.com.foursales.ordermanagement.model.auth.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {

    Permissions findByName(String name);

}
