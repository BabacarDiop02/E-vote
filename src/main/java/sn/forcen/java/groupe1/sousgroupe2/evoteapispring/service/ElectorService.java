package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Elector;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.ElectorRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ElectorService {
    private final ElectorRepository electorRepository;

    public ElectorService(ElectorRepository electorRepository) {
        this.electorRepository = electorRepository;
    }

    public List<Elector> getAllElector() {
        return electorRepository.findByEnabledTrue();
    }

    public Elector getElectorById(Long id) {
        return electorRepository.findByIdAndEnabledTrue(id);
    }

    public Elector addElector(Elector elector) {
        return electorRepository.save(elector);
    }

    public Elector updateElector(Elector elector) {
        Elector electorToUpdate = this.getElectorById(elector.getId());
        if (electorToUpdate != null) {
            return electorRepository.save(elector);
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
            elector.setTown(row.getCell(8).getStringCellValue());
            elector.setVotingPlace(row.getCell(9).getStringCellValue());
            elector.setPollingStation((long) row.getCell(10).getNumericCellValue());

            electors.add(elector);
        }

        electorRepository.saveAll(electors);
        workbook.close();
    }

    public void deleteElectorById(Long id) {
        Elector elector = this.getElectorById(id);
        elector.setEnabled(false);
    }
}
