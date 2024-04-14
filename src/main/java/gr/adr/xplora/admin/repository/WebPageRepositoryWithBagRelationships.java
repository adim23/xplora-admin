package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.WebPage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface WebPageRepositoryWithBagRelationships {
    Optional<WebPage> fetchBagRelationships(Optional<WebPage> webPage);

    List<WebPage> fetchBagRelationships(List<WebPage> webPages);

    Page<WebPage> fetchBagRelationships(Page<WebPage> webPages);
}
