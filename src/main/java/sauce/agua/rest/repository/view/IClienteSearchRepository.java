/**
 * 
 */
package sauce.agua.rest.repository.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.view.ClienteSearch;

/**
 * @author daniel
 *
 */
@Repository
public interface IClienteSearchRepository extends JpaRepository<ClienteSearch, Long> {

	public List<ClienteSearch> findTop50BySearchLike(String search, Sort sort);

}
