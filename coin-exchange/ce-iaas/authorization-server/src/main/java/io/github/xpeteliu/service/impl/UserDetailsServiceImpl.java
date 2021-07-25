package io.github.xpeteliu.service.impl;

import io.github.xpeteliu.constant.LoginConstant;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final JdbcTemplate jdbcTemplate;

    public UserDetailsServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new IllegalStateException("No request attributes bound to current thread");
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String loginType = request.getParameter("login_type");
        if (!StringUtils.hasLength(loginType)) {
            throw new AuthenticationServiceException("Login type not specified");
        }
        try {
            String grantType = request.getParameter("grant_type");
            if (LoginConstant.REFRESH_TOKEN.equalsIgnoreCase(grantType)) {
                username = alterUsername(username, loginType);
            }

            switch (loginType) {
                case LoginConstant.ADMIN_LOGIN:
                    return loadAdminUserByUserName(username);
                case LoginConstant.MEMBER_LOGIN:
                    return loadMemberUserByUserName(username);
                default:
                    throw new AuthenticationServiceException("Login type not supported");
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                throw new UsernameNotFoundException("User does not exist");
            } else {
                throw e;
            }
        }
    }

    private String alterUsername(String username, String loginType) {
        if (LoginConstant.ADMIN_LOGIN.equalsIgnoreCase(loginType)) {
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_USER_WITH_ID, String.class, username);
        }

        if (LoginConstant.MEMBER_LOGIN.equalsIgnoreCase(loginType)) {
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_USER_WITH_ID, String.class, username);
        }

        return username;
    }

    private UserDetails loadAdminUserByUserName(String username) {
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL,
                (rs, rowNum) -> {
                    if (rs.wasNull()) {
                        throw new IncorrectResultSizeDataAccessException(1, 0);
                    }

                    long id = rs.getLong("id");
                    return new User(
                            String.valueOf(id),
                            rs.getString("password"),
                            rs.getInt("status") == 1,
                            true,
                            true,
                            true,
                            getAdminUserPermissions(id)
                    );
                },
                username);
    }

    private Set<GrantedAuthority> getAdminUserPermissions(long id) {
        String roleCode = jdbcTemplate.queryForObject(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
        List<String> permissions = null;
        if (LoginConstant.ADMIN_ROLE_CODE.equalsIgnoreCase(roleCode)) {
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_ALL_PERMISSIONS_SQL, String.class);
        } else {
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_PERMISSION_SQL, String.class, id);
        }
        if (permissions.isEmpty()) {
            return Collections.emptySet();
        }
        return permissions
                .stream()
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    private UserDetails loadMemberUserByUserName(String username) {
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_SQL,
                (rs, rowNum) -> {
                    if (rs.wasNull()) {
                        throw new IncorrectResultSizeDataAccessException(1, 0);
                    }

                    return new User(
                            String.valueOf(rs.getLong("id")),
                            rs.getString("password"),
                            rs.getInt("status") == 1,
                            true,
                            true,
                            true,
                            List.of(new SimpleGrantedAuthority("ROLE_USER"))
                    );
                },
                username, username);
    }
}
