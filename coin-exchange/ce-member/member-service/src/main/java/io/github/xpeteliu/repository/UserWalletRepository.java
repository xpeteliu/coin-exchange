package io.github.xpeteliu.repository;

import io.github.xpeteliu.entity.UserWallet;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWalletRepository extends CrudRepository<UserWallet, Long> {

//    @Modifying
//    @Query("update user_wallet ua set ua.status=:status where ua.id=:id;")
//    void updateStatusById(Long id, Integer status);

    @Query("select * from user_wallet\n" +
            "where user_id=:userId\n" +
            "order by last_update_time desc\n" +
            "limit :pos,:size;")
    List<UserWallet> findWithPaging(Long userId, Integer pos, Integer size);

    @Query("select count(*) from user_wallet\n" +
            "where user_id=:userId;")
    Long countQualified(Long userId);
}
