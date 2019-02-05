package hr.ja.bio.service;

import hr.ja.bio.repository.TaxNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxNameService {

    @Autowired
    TaxNameRepository taxNameRepository;

    public int taxNameToInt(String taxName) {
        return taxNameRepository.findIdByName(taxName);
    }




}
