package gr.adr.xplora.admin.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, gr.adr.xplora.admin.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, gr.adr.xplora.admin.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, gr.adr.xplora.admin.domain.User.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Authority.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.User.class.getName() + ".authorities");
            createCache(cm, gr.adr.xplora.admin.domain.Destination.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Destination.class.getName() + ".tours");
            createCache(cm, gr.adr.xplora.admin.domain.Destination.class.getName() + ".places");
            createCache(cm, gr.adr.xplora.admin.domain.Destination.class.getName() + ".images");
            createCache(cm, gr.adr.xplora.admin.domain.Destination.class.getName() + ".contents");
            createCache(cm, gr.adr.xplora.admin.domain.PlaceCategory.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.PlaceCategory.class.getName() + ".images");
            createCache(cm, gr.adr.xplora.admin.domain.PlaceCategory.class.getName() + ".contents");
            createCache(cm, gr.adr.xplora.admin.domain.PlaceCategory.class.getName() + ".places");
            createCache(cm, gr.adr.xplora.admin.domain.Place.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Place.class.getName() + ".tourSteps");
            createCache(cm, gr.adr.xplora.admin.domain.Place.class.getName() + ".images");
            createCache(cm, gr.adr.xplora.admin.domain.Place.class.getName() + ".contents");
            createCache(cm, gr.adr.xplora.admin.domain.Place.class.getName() + ".tags");
            createCache(cm, gr.adr.xplora.admin.domain.Place.class.getName() + ".categories");
            createCache(cm, gr.adr.xplora.admin.domain.TourCategory.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.TourCategory.class.getName() + ".children");
            createCache(cm, gr.adr.xplora.admin.domain.TourCategory.class.getName() + ".images");
            createCache(cm, gr.adr.xplora.admin.domain.TourCategory.class.getName() + ".menus");
            createCache(cm, gr.adr.xplora.admin.domain.TourCategory.class.getName() + ".contents");
            createCache(cm, gr.adr.xplora.admin.domain.TourCategory.class.getName() + ".tours");
            createCache(cm, gr.adr.xplora.admin.domain.Tour.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Tour.class.getName() + ".steps");
            createCache(cm, gr.adr.xplora.admin.domain.Tour.class.getName() + ".images");
            createCache(cm, gr.adr.xplora.admin.domain.Tour.class.getName() + ".extraInfos");
            createCache(cm, gr.adr.xplora.admin.domain.Tour.class.getName() + ".contents");
            createCache(cm, gr.adr.xplora.admin.domain.Tour.class.getName() + ".tourExtras");
            createCache(cm, gr.adr.xplora.admin.domain.Tour.class.getName() + ".tags");
            createCache(cm, gr.adr.xplora.admin.domain.Tour.class.getName() + ".promotions");
            createCache(cm, gr.adr.xplora.admin.domain.Tour.class.getName() + ".categories");
            createCache(cm, gr.adr.xplora.admin.domain.TourStep.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.TourStep.class.getName() + ".infos");
            createCache(cm, gr.adr.xplora.admin.domain.Promotion.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Promotion.class.getName() + ".titles");
            createCache(cm, gr.adr.xplora.admin.domain.Promotion.class.getName() + ".tours");
            createCache(cm, gr.adr.xplora.admin.domain.TourSchedule.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.TourSchedule.class.getName() + ".bookings");
            createCache(cm, gr.adr.xplora.admin.domain.TourExtraCategory.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.TourExtraCategory.class.getName() + ".contents");
            createCache(cm, gr.adr.xplora.admin.domain.TourExtraCategory.class.getName() + ".extras");
            createCache(cm, gr.adr.xplora.admin.domain.TourExtra.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.TourExtra.class.getName() + ".contents");
            createCache(cm, gr.adr.xplora.admin.domain.TourExtra.class.getName() + ".tags");
            createCache(cm, gr.adr.xplora.admin.domain.TourExtra.class.getName() + ".categories");
            createCache(cm, gr.adr.xplora.admin.domain.TourExtra.class.getName() + ".tours");
            createCache(cm, gr.adr.xplora.admin.domain.Driver.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Driver.class.getName() + ".images");
            createCache(cm, gr.adr.xplora.admin.domain.Vehicle.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Vehicle.class.getName() + ".images");
            createCache(cm, gr.adr.xplora.admin.domain.Booking.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Passenger.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Passenger.class.getName() + ".bookings");
            createCache(cm, gr.adr.xplora.admin.domain.ImageFile.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.ImageFile.class.getName() + ".captions");
            createCache(cm, gr.adr.xplora.admin.domain.WebPage.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.WebPage.class.getName() + ".menus");
            createCache(cm, gr.adr.xplora.admin.domain.WebPage.class.getName() + ".contents");
            createCache(cm, gr.adr.xplora.admin.domain.WebPage.class.getName() + ".tags");
            createCache(cm, gr.adr.xplora.admin.domain.Menu.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Menu.class.getName() + ".children");
            createCache(cm, gr.adr.xplora.admin.domain.Menu.class.getName() + ".names");
            createCache(cm, gr.adr.xplora.admin.domain.Language.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Content.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Tag.class.getName());
            createCache(cm, gr.adr.xplora.admin.domain.Tag.class.getName() + ".names");
            createCache(cm, gr.adr.xplora.admin.domain.Tag.class.getName() + ".places");
            createCache(cm, gr.adr.xplora.admin.domain.Tag.class.getName() + ".tours");
            createCache(cm, gr.adr.xplora.admin.domain.Tag.class.getName() + ".tourExtras");
            createCache(cm, gr.adr.xplora.admin.domain.Tag.class.getName() + ".webPages");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
