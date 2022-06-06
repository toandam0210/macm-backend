package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer>{

}
