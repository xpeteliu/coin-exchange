package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long>, QuerydslPredicateExecutor<Config> {

    Config findByCode(String code);
}