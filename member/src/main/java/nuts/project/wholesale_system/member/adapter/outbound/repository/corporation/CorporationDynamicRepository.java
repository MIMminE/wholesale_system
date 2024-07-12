package nuts.project.wholesale_system.member.adapter.outbound.repository.corporation;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import nuts.project.wholesale_system.member.domain.model.Grade;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.QCorporationEntity.corporationEntity;

@Component
public class CorporationDynamicRepository {

    private final JPAQueryFactory queryFactory;

    public CorporationDynamicRepository(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<CorporationEntity> search(String corporationId, String corporationName, String representative, String contactNumber, String businessNumber, Grade grade) {

        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(corporationId))
            builder.and(corporationEntity.corporationId.eq(corporationId));
        if (StringUtils.hasText(corporationName))
            builder.and(corporationEntity.corporationName.eq(corporationName));
        if (StringUtils.hasText(representative))
            builder.and(corporationEntity.representative.eq(representative));
        if (StringUtils.hasText(contactNumber))
            builder.and(corporationEntity.contactNumber.eq(contactNumber));
        if (StringUtils.hasText(businessNumber))
            builder.and(corporationEntity.businessNumber.eq(businessNumber));
        if (grade != null)
            builder.and(corporationEntity.grade.eq(grade));

        return queryFactory.select(corporationEntity)
                .from(corporationEntity)
                .where(builder).stream().toList();
    }

}
