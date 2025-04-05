package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import org.apache.poi.ss.usermodel.*;
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
            elector.setNationalIdentificationNumber(this.getStringCellValue(row.getCell(0)));
            elector.setFirstName(this.getStringCellValue(row.getCell(1)));
            elector.setLastName(this.getStringCellValue(row.getCell(2)));
            elector.setDateOfBirth(row.getCell(3).getDateCellValue());
            elector.setPlaceOfBirth(this.getStringCellValue(row.getCell(4)));
            elector.setVoterNumber(this.getStringCellValue(row.getCell(5)));
            elector.setRegion(this.getStringCellValue(row.getCell(6)));
            elector.setDepartment(this.getStringCellValue(row.getCell(7)));
            elector.setBorough(this.getStringCellValue(row.getCell(8)));
            elector.setTown(this.getStringCellValue(row.getCell(9)));
            elector.setVotingPlace(this.getStringCellValue(row.getCell(10)));
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

    private String getStringCellValue(Cell cell) {
        if (cell == null) return "";

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}
