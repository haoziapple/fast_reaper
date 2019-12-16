package github.haozi.xspirder.datarest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * 参考：https://spring.io/guides/gs/accessing-data-rest/
 * @author wanghao
 * @Description http://localhost:8080/site
 * @date 2019-12-14 15:00
 */
@RepositoryRestResource(collectionResourceRel = "targetSite", path = "site")
public interface TargetSiteRepository extends PagingAndSortingRepository<TargetSite, Long> {
    /**
     * http://localhost:8080/site/search/findByName?name=1"
     * @param name
     * @return
     */
    List<TargetSite> findByName(@Param("name") String name);

    Page<TargetSite> findByNameOrderByIdDesc(@Param("name") String name, Pageable pageable);
}
