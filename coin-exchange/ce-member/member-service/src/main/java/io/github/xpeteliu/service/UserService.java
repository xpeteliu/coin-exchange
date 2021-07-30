package io.github.xpeteliu.service;

import io.github.xpeteliu.entity.User;
import io.github.xpeteliu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Page<User> findWithPaging(String mobile, String username, String realName, Long userId, Byte status, int page, int size) {
        List<User> resultList = userRepository.findWithPaging(mobile, username, realName, userId, status, page*size, size);
        Long cnt = userRepository.countQualified(mobile, username, realName, userId, status);
        PageRequest request = PageRequest.of(page, size, Sort.Direction.DESC, "lastUpdateTime");
        return new PageImpl<>(resultList, request, cnt);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void updateStatus(Long id, Integer status) {
        userRepository.updateStatusById(id, status);
    }

}
