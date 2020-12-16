package dev.maxuz.kwh.database.service;

import dev.maxuz.kwh.database.repository.UserRepository;
import dev.maxuz.kwh.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DaoUserDetailsServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final DaoUserDetailsService service = new DaoUserDetailsService(userRepository);

    @Test
    void findUserByName_UserExist_ReturnUserDetails() {
        User user = new User();
        user.setName("username");
        user.setPassword("very strong password");
        when(userRepository.findByName(any()))
                .thenReturn(Optional.of(user));
        UserDetails actual = service.loadUserByUsername("username");
        assertThat(actual.getUsername(), is("username"));
        assertThat(actual.getPassword(), is("very strong password"));

        ArgumentCaptor<String> userNameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).findByName(userNameArgumentCaptor.capture());
        assertThat(userNameArgumentCaptor.getValue(), is("username"));
    }

    @Test
    void findUserByName_UserDoesNotExist_ThrowsException() {
        when(userRepository.findByName(any()))
                .thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("username"));
    }
}