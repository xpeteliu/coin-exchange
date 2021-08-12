package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.UserAuthAuditRecord;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthAuditRecordRepository extends PagingAndSortingRepository<UserAuthAuditRecord, Long> {

    @Modifying
    @Query("update user_auth_audit_record ub set ub.status=:status where ub.id=:id;")
    void updateStatusById(Long id, Integer status);

    @Query("select * from user_auth_audit_record\n" +
            "where user_id=:userId\n" +
            "limit :pos,:size;")
    List<UserAuthAuditRecord> findWithPaging(Long userId, Integer pos, Integer size);

    @Query("select count(*) from user_auth_audit_record\n" +
            "where user_id=:userId;")
    Long countQualified(Long userId);

    List<UserAuthAuditRecord> findRecordsByUserId(Long userId, Sort sort);
}
