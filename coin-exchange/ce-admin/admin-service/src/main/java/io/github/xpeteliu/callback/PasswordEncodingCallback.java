package io.github.xpeteliu.callback;

import io.github.xpeteliu.entity.SysUser;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordEncodingCallback implements BeforeConvertCallback<SysUser> {

    final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2[ayb]?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public SysUser onBeforeConvert(SysUser sysUser) {
        String password = sysUser.getPassword();
        if (!BCRYPT_PATTERN.matcher(password).matches()) {
            sysUser.setPassword(passwordEncoder.encode(password));
        }
        return sysUser;
    }
}