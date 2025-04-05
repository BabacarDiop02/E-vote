package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Elector;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElectorRepository extends JpaRepository<Elector, Integer> {
    Optional<Elector> findByNationalIdentificationNumberAndFirstNameAndLastName(String nationalIdentificationNumber, String firstName, String lastName);
    List<Elector> findByEnabledTrue();
    Optional<Elector> findByIdAndEnabledTrue(int id);
}
