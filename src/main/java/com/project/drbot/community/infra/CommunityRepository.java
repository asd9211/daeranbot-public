package com.project.drbot.community.infra;

import com.project.drbot.community.domain.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {
    List<CommunityEntity> findAllByOrderByRegDateDesc();

}
