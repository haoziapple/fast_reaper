package github.haozi.xspirder.datarest;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-16 14:28
 */
@RepositoryRestResource(collectionResourceRel = "grabRule", path = "grabRule")
public interface GrabRuleRepository extends PagingAndSortingRepository<GrabRule, Long> {
}
