package com.fpt.macm.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.News;

@Repository
public interface NewsRepository extends PagingAndSortingRepository<News, Integer>{
	
}
