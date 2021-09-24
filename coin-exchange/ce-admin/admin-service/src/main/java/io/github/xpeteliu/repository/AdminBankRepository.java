package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.AdminBank;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminBankRepository extends PagingAndSortingRepository<AdminBank, Long> {

    @Query("select r.`code` " +
            "from sys_role r left join sys_user_role ur on r.id=ur.role_id " +
            "where ur.user_id=:userId")
    String findCodeByUserId(Long userId);

    List<AdminBank> findByBankCardContaining(String bankCard, Pageable pageable);

    Long countByBankCardContaining(String bankCard);

    @Modifying
    @Query("update admin_bank ab set ab.status=:status where ab.id=:id;")
    void updateStatusById(Long id, Integer status);

    List<AdminBank> findByStatus(Byte status);
}
