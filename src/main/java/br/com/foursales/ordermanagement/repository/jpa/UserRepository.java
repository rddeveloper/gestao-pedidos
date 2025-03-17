package br.com.foursales.ordermanagement.repository.jpa;

import br.com.foursales.ordermanagement.model.auth.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByUsername(String username);
    UserInfo findByIdentification(String identification);
    UserInfo findByEmail(String email);

}
