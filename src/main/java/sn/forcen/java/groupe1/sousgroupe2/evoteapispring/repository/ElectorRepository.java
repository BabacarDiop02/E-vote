package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Elector;

import java.util.List;

@Repository
public interface ElectorRepository extends JpaRepository<Elector, Integer> {
    Elector findByNationalIdentificationNumber(String nationalIdentificationNumber);
    List<Elector> findByEnabledTrue();
    Elector findByIdAndEnabledTrue(int id);
}
