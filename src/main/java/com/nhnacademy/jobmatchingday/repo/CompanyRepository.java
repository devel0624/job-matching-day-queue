package com.nhnacademy.jobmatchingday.repo;

import com.nhnacademy.jobmatchingday.domain.Company;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Repository
public class CompanyRepository {

    Map<Long, Company> companyMap;


    public CompanyRepository() {
        companyMap = new HashMap<>();
    }

    public Optional<Company> getCompany(Long id) {
        return Optional.ofNullable(companyMap.get(id));
    }

    public Company save(Company company) {

        int id = companyMap.size();
        Company newCompany = new Company((long) id, company.getName());

        companyMap.put(newCompany.getId(), newCompany);

        return newCompany;
    }

    public boolean remove(Long companyId) {
        return companyMap.remove(companyId) != null;
    }

    public Collection<Company> findAll() {
        return companyMap.values();
    }
}
