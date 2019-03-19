package hr.ja.bio.service;

import hr.ja.bio.model.TaxName;
import hr.ja.bio.repository.TaxNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TaxNameService {

    @Autowired
    TaxNameRepository taxNameRepository;

    @Cacheable("taxName")
    public Integer taxNameToInt(String taxName) {
        Integer id = taxNameRepository.findIdByName(taxName);
        if (id == null) {
            TaxName t = new TaxName();
            t.setName(taxName);
            TaxName saved = taxNameRepository.save(t);
            return saved.getId();
        }
        return id;
    }


}
