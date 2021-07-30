package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.SysRole;
import io.github.xpeteliu.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRepository extends PagingAndSortingRepository<SysUser, Long> {

    List<SysUser> findByFullnameContainingAndMobileContaining(String fullname, String mobile, Pageable pageable);

    Long countByFullnameContainingAndMobileContaining(String fullname, String mobile);

    @Modifying
    @Query("delete from sys_user u where u.id in (:ids);")
    Long deleteAllById(List<Long> ids);
}
