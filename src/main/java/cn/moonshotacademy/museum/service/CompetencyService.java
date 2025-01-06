package cn.moonshotacademy.museum.service;

import cn.moonshotacademy.museum.entity.CompetencyEntity;
import cn.moonshotacademy.museum.repository.CompetencyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetencyService {

    @Autowired private CompetencyRepository competencyRepository;

    public List<CompetencyEntity> getCompetencies() {
        List<CompetencyEntity> competencies = competencyRepository.findAll();
        return competencies;
    }
}
