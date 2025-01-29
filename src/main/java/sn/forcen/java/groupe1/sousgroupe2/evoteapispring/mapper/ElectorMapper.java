package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper;

import org.springframework.web.bind.annotation.Mapping;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.ElectorDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Elector;

@Mapper(componentModel = "Spring")
public interface ElectorMapper {
    ElectorDTO toElectorDTO(Elector elector);
    @Mapping(target = "enabled", ignore = true)
    Elector toElector(ElectorDTO electorDTO);
}