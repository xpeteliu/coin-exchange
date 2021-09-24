package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.UserAddress;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends CrudRepository<UserAddress, Long> {

//    @Modifying
//    @Query("update user_address ua set ua.status=:status where ua.id=:id;")
//    void updateStatusById(Long id, Integer status);

    @Query("select * from user_address\n" +
            "where user_id=:userId\n" +
            "order by last_update_time desc\n" +
            "limit :pos,:size;")
    List<UserAddress> findWithPaging(Long userId, Integer pos, Integer size);

    @Query("select count(*) from user_address\n" +
            "where user_id=:userId;")
    Long countQualified(Long userId);
}
