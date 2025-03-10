package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.dto.CompetencyDto;
import cn.moonshotacademy.museum.repository.CompetencyRepository;
import cn.moonshotacademy.museum.entity.CompetencyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetencyService {

    @Autowired
    private CompetencyRepository competencyRepository;

    public List<CompetencyDto> getCompetencies() {
        List<CompetencyEntity> competencies = competencyRepository.findAll();
        return competencies.stream()
                .map(competency -> new CompetencyDto(competency))
                .collect(Collectors.toList());
    }

    public CompetencyEntity getCompetencyById(Integer id) {
        CompetencyEntity competency = competencyRepository.findById(id).orElse(null);
        return competency;
    }
}
