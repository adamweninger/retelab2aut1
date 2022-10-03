package hu.bme.aut.retelab2.repository;
import hu.bme.aut.retelab2.domain.Ad;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AdRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Ad save(Ad ad) {
        return em.merge(ad);
    }

    public List<Ad> findAllByPriceRange(Integer minPrice, Integer maxPrice) {
        return em.createQuery("SELECT a FROM Ad a WHERE a.price BETWEEN ?1 AND ?2", Ad.class).setParameter(1, minPrice).setParameter(2, maxPrice).getResultList();
    }

    @Transactional
    public Ad update(Ad ad) {
        Ad adToUpdate = em.find(Ad.class, ad.getId());
        if(adToUpdate.getSecretCode().equals(ad.getSecretCode())){
            return save(ad);
        }
        else{
            throw new RuntimeException("Forbidden");
        }
    }

    public List<Ad> findAll() {
        return em.createQuery("SELECT n FROM Ad n", Ad.class).getResultList();
    }

    @Transactional
    public void bulkDelete(List<Ad> ads) {
        ads.forEach(x->em.remove(findById(x.getId())));
    }

    public Ad findById(Long id) {
        return em.find(Ad.class, id);
    }

}
