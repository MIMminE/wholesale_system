package nuts.project.wholesale_system.member.adapter.outbound.repository.corporation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporationRepository extends JpaRepository<CorporationEntity, String> {

}
