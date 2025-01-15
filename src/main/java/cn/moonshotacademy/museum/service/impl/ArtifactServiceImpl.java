package cn.moonshotacademy.museum.service.impl;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.entity.FileEntity;
import cn.moonshotacademy.museum.entity.UserEntity;
import cn.moonshotacademy.museum.dto.RequestDto;
import cn.moonshotacademy.museum.repository.ArtifactRepository;
import cn.moonshotacademy.museum.repository.UserRepository;
import cn.moonshotacademy.museum.repository.FileRepository;
import cn.moonshotacademy.museum.exception.BusinessException;
import cn.moonshotacademy.museum.exception.ExceptionEnum;
import cn.moonshotacademy.museum.service.ArtifactService;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ArtifactServiceImpl implements ArtifactService {

    @Autowired
    private ArtifactRepository artifactRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;
    @Transactional
    @Override
    public ArtifactEntity updateArtifactAndUser(Integer artifactId, RequestDto dto) {
        ArtifactEntity artifact = artifactRepository.findById(artifactId)
            .orElseThrow(() -> new BusinessException(ExceptionEnum.ARTIFACT_NOT_FOUND));

        artifact.setTitle(dto.getTitle());
        artifact.setIntro(dto.getIntro());
        artifact.setCompetency(dto.getCompetency());
        artifact.setCategory(dto.getCategory());
        artifact.setAvatarUrl(dto.getAvatar_url());
        artifact.setType(dto.getType());

        Set<UserEntity> currentUsers = artifact.getUserList();
        Set<Integer> newUserIds = dto.getUserIds();
        currentUsers.removeIf(user -> !newUserIds.contains(user.getId()));
        for (Integer userId : dto.getUserIds()) {
            UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));
           
            currentUsers.add(user);
            user.getArtifactList().add(artifact);
            
            userRepository.save(user);
        }

        for (Integer fileId : dto.getFileIds()) {
            FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() -> new BusinessException(ExceptionEnum.FILE_NOT_FOUND));
            file.setArtifact(artifact);
        }


        return artifactRepository.save(artifact);
    }
}
