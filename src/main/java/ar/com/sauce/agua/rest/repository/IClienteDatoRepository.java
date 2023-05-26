/**
 * 
 */
package ar.com.sauce.agua.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.sauce.agua.rest.model.ClienteDato;

/**
 * @author daniel
 *
 */
@Repository
public interface IClienteDatoRepository extends JpaRepository<ClienteDato, Long> {

	public Optional<ClienteDato> findByClienteId(Long clienteId);

}
