package com.superum.helper;

import com.superum.api.v1.account.AccountDAO;
import eu.goodlike.libraries.spring.gmail.GMail;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.*;

@Configuration
@Lazy
public class MockConfig {

    @Bean
    @Primary
    public GMail gmail() {
        GMail gmail = Mockito.mock(GMail.class);
        doNothing().when(gmail).send(any(), any(), any());
        return gmail;
    }

    @Bean
    @Primary
    public AccountDAO accountDAO() {
        AccountDAO accountDAO = Mockito.mock(AccountDAO.class);
        when(accountDAO.create(any())).thenReturn(null);
        when(accountDAO.read(any())).thenReturn(null);
        when(accountDAO.update(any())).thenReturn(null);
        when(accountDAO.delete(any())).thenReturn(null);
        return accountDAO;
    }

}
