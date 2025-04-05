package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.dto.CandidateDTO;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.mapper.CandidateMapper;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Candidate;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.CandidateRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private static final String UPLOAD_DIR = "C:/e-vote/uploads/";

    public CandidateService(CandidateRepository candidateRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    public Set<CandidateDTO> getAllCandidates() {
        List<Candidate> candidates = this.candidateRepository.findByEnabledTrue();
        return candidates.stream().map(this.candidateMapper::toDTO).collect(Collectors.toSet());
    }

    public CandidateDTO getCandidateById(int id) {
        return this.candidateMapper.toDTO(this.candidateRepository.findByIdAndEnabledTrue(id).orElseThrow(() -> new RuntimeException("Candidate not found")));
    }

    @Transactional
    public CandidateDTO createCandidate(CandidateDTO candidateDTO, MultipartFile programFile, MultipartFile profileImage) throws Exception {
        String profileImageName = saveFile(profileImage, "profile", "profile_", candidateDTO.getId(), candidateDTO.getFirstName(), candidateDTO.getLastName());
        String programFileName = saveFile(programFile, "program", "program_", candidateDTO.getId(), candidateDTO.getFirstName(), candidateDTO.getLastName());

        Candidate candidate = this.candidateMapper.toEntity(candidateDTO);
        candidate.setProgramNameFile(programFileName);
        candidate.setProfileNameImage(profileImageName);
        return this.candidateMapper.toDTO(this.candidateRepository.save(candidate));
    }

    @Transactional
    public CandidateDTO updateCandidate(CandidateDTO candidateDTO, MultipartFile programFile, MultipartFile profileImage) throws IOException {
        Candidate candidate = this.candidateRepository.findByIdAndEnabledTrue(candidateDTO.getId()).orElseThrow(() -> new RuntimeException("Candidate not found"));
        if (candidateDTO.getFirstName() != null) candidate.setFirstName(candidateDTO.getFirstName());
        if (candidateDTO.getLastName() != null) candidate.setLastName(candidateDTO.getLastName());
        if (candidateDTO.getAge() != null) candidate.setAge(candidateDTO.getAge());
        if (candidateDTO.getOccupation() != null) candidate.setOccupation(candidateDTO.getOccupation());
        if (candidateDTO.getPortrait() != null) candidate.setPortrait(candidateDTO.getPortrait());
        if (programFile != null) candidate.setProgramNameFile(saveFile(programFile, "program", "program_", candidate.getId(), candidate.getFirstName(), candidate.getLastName()));
        if (profileImage != null) candidate.setProfileNameImage(saveFile(profileImage, "profile", "profile_", candidate.getId(), candidate.getFirstName(), candidate.getLastName()));

        Candidate candidateUpdate = this.candidateRepository.save(candidate);
        return this.candidateMapper.toDTO(candidateUpdate);
    }

    @Transactional
    public void deleteCandidate(int id) {
        Candidate candidate = this.candidateRepository.findByIdAndEnabledTrue(id).orElseThrow(() -> new RuntimeException("Candidate not found"));
        candidate.setEnabled(false);
        this.candidateRepository.save(candidate);
    }

    public Resource getFile(String subDir, String fileName) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR, subDir, fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new IOException("Fichier introuvable : " + fileName);
        }
        return resource;
    }

    private static String saveFile(MultipartFile file, String subDir, String prefix, Integer id, String firstName, String lastName) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide ou null !");
        }

        // Construire le chemin du répertoire
        String directoryPath = Paths.get(UPLOAD_DIR, subDir).toString();
        File directory = new File(directoryPath);

        // Vérifier si le dossier existe, sinon le créer
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Impossible de créer le répertoire : " + directoryPath);
        }

        // Construire le nom du fichier
        String fileName = prefix + id + "_" + firstName.replaceAll("\\s+", "") + "_" + lastName.replaceAll("\\s+", "") + getFileExtension(file.getOriginalFilename());
        File fileToSave = new File(Paths.get(directoryPath, fileName).toString());

        // Sauvegarder le fichier
        file.transferTo(fileToSave);

        return fileName;
    }

    // Récupérer l'extension du fichier
    private static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return ""; // Pas d'extension
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
