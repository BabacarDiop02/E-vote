package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.ElectorDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper.ElectorMapper;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Elector;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.ElectorRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ElectorService {
    private final ElectorRepository electorRepository;
    private final ElectorMapper electorMapper;

    public ElectorService(ElectorRepository electorRepository, ElectorMapper electorMapper) {
        this.electorRepository = electorRepository;
        this.electorMapper = electorMapper;
    }

    public Set<ElectorDTO> getAllElector() {
        List<Elector> electors = this.electorRepository.findByEnabledTrue();
        return electors.stream().map(this.electorMapper::toElectorDTO).collect(Collectors.toSet());
    }

    public ElectorDTO getElectorById(int id) {
        return this.electorMapper.toElectorDTO(this.electorRepository.findByIdAndEnabledTrue(id).orElseThrow(() -> new RuntimeException("Elector not found")));
    }

    public ElectorDTO addElector(ElectorDTO electorDTO) {
        Elector elector = this.electorMapper.toElector(electorDTO);
        return this.electorMapper.toElectorDTO(this.electorRepository.save(elector));
    }

    public ElectorDTO updateElector(ElectorDTO electorDTO) {
        ElectorDTO electorToUpdate = this.getElectorById(electorDTO.getId());
        if (electorToUpdate != null) {
            Elector elector = this.electorMapper.toElector(electorDTO);
            return this.electorMapper.toElectorDTO(this.electorRepository.save(elector));
        } else {
            throw new RuntimeException("Elector not found");
        }
    }

    public void addElectorFromExcel(MultipartFile file) throws IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        List<Elector> electors = new ArrayList<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            Elector elector = new Elector();
            elector.setNationalIdentificationNumber(row.getCell(0).getStringCellValue());
            elector.setFirstName(row.getCell(1).getStringCellValue());
            elector.setLastName(row.getCell(2).getStringCellValue());
            elector.setDateOfBirth(row.getCell(3).getDateCellValue());
            elector.setPlaceOfBirth(row.getCell(4).getStringCellValue());
            elector.setVoterNumber(row.getCell(5).getStringCellValue());
            elector.setRegion(row.getCell(6).getStringCellValue());
            elector.setDepartment(row.getCell(7).getStringCellValue());
            elector.setBorough(row.getCell(8).getStringCellValue());
            elector.setTown(row.getCell(9).getStringCellValue());
            elector.setVotingPlace(row.getCell(10).getStringCellValue());
            elector.setPollingStation((long) row.getCell(11).getNumericCellValue());

            electors.add(elector);
        }

        this.electorRepository.saveAll(electors);
        workbook.close();
    }

    public void deleteElectorById(int id) {
        Elector elector = this.electorRepository.findByIdAndEnabledTrue(id).orElseThrow(() -> new RuntimeException("Elector not found"));
        elector.setEnabled(false);
        electorRepository.save(elector);
    }
}
