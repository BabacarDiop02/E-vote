package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.ElectorDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Elector;

@Component
public class ElectorMapper {
    public ElectorDTO toElectorDTO(Elector elector) {
        ElectorDTO electorDTO = new ElectorDTO();
        BeanUtils.copyProperties(elector, electorDTO);
        return electorDTO;
    }

    public Elector toElector(ElectorDTO electorDTO) {
        Elector elector = new Elector();
        BeanUtils.copyProperties(electorDTO, elector);
        return elector;
    }
}