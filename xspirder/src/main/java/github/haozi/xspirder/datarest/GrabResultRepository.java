package github.haozi.xspirder.datarest;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-16 14:31
 */
@RepositoryRestResource(collectionResourceRel = "grabResult", path = "grabResult")
public interface GrabResultRepository extends PagingAndSortingRepository<GrabResult, Long> {
}
