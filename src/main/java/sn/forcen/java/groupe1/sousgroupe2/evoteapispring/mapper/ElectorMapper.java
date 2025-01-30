package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.ElectorDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Elector;

@Mapper(componentModel = "spring")
public interface ElectorMapper {
    ElectorDTO toElectorDTO(Elector elector);
    @Mapping(target = "enabled", ignore = true)
    Elector toElector(ElectorDTO electorDTO);
}