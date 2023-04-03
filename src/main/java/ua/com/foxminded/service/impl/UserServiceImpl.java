package ua.com.foxminded.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.repository.UserRepository;
import ua.com.foxminded.security.SecurityUser;
import ua.com.foxminded.security.enums.Role;
import ua.com.foxminded.security.enums.Status;
import ua.com.foxminded.DTO.UserDTO;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.model.User;
import ua.com.foxminded.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    public static final String SYSTEM_USER_LOGIN = "$System";
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Value("${system.user.password}")
    private String systemUserPassword;

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('access_to_users')")
    public Optional<User> findById(Long id) {
        log.debug("Trying to find User by id = {}", id);
        try {
            Optional<User> entity = repository.findById(id);

            if (entity.isPresent()) {
                log.info("Success, User by id = {} was found", id);
                return entity;
            }

            return entity;

        } catch (EmptyResultDataAccessException e) {
            log.error("Unable find User by id = {} due: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByLogin(String login) {
        log.debug("Trying to find User by login = {}", login);
        try {
            Optional<User> entity = repository.findByLogin(login);

            if (entity.isPresent()) {
                log.info("Success, User by login = {} was found", login);
                return entity;
            }

            return entity;

        } catch (EmptyResultDataAccessException e) {
            log.error("Unable find User by login = {} due: {}", login, e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('access_to_users')")
    public List<User> findByName(String name) {
        log.debug("Trying to find User by name = {}", name);
        try {
            List<User> entity = repository.findByName(name);

            log.info("Success, User by name = {} was found", name);
            return entity;

        } catch (EmptyResultDataAccessException e) {
            log.error("Unable find User by name = {} due: {} ", name, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('access_to_users')")
    public List<User> findAll() {
        log.debug("Trying to find all Users");
        List<User> list;
        try {
            list = repository.findAll();

            log.info("Success, all Users was found");
            return list;

        } catch (Exception e) {
            log.error("Unable find all Users due: {} ", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('delete')")
    public void deleteById(Long id) {
        log.debug("Trying to delete User by id = {}", id);

        try {
            repository.deleteById(id);
            log.info("Success, User by id = {} was deleted", id);

        } catch (Exception e) {
            log.error("Unable delete User by id = {} due: {}", id, e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('access_to_users')")
    public UserDTO save(UserDTO entity) {
        log.debug("Trying to save User by name = {} ", entity.getName());

        try {
            User user = new User();
            user.setName(entity.getName());
            user.setLogin(entity.getLogin());
            user.setPassword(passwordEncoder.encode(entity.getPassword()));
            user.setRole(entity.getRole());
            user.setStatus(Status.ACTIVE);
            User newUser = repository.save(user);

            log.info("Success, User by name = {} was created", entity.getName());
            return new UserDTO(
                    newUser.getId(),
                    newUser.getName(),
                    newUser.getLogin(),
                    newUser.getPassword(),
                    newUser.getRole());

        } catch (Exception e) {
            log.error("Unable create User by name = {} due: {} ", entity.getName(), e.getMessage(), e);
            return entity;
        }
    }

    @Override
    @Transactional
    public UserDTO register(UserDTO entity) {
        log.debug("Trying to register User by name = {} ", entity.getName());

        try {
            User user = new User();
            user.setName(entity.getName());
            user.setLogin(entity.getLogin());
            user.setPassword(passwordEncoder.encode(entity.getPassword()));
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            User newUser = repository.save(user);

            log.info("Success, User by name = {} was registered", entity.getName());
            return new UserDTO(
                    newUser.getId(),
                    newUser.getName(),
                    newUser.getLogin(),
                    newUser.getPassword(),
                    newUser.getRole());

        } catch (Exception e) {
            log.error("Unable register User by name = {} due: {} ", entity.getName(), e.getMessage(), e);
            return entity;
        }
    }

    @Override
    @Transactional
    @PreAuthorize("#entity.login == authentication.principal.username or hasAuthority('access_to_users')")
    public UserDTO update(UserDTO entity) {
        log.debug("Trying to update User by name = {} ", entity.getName());

        try {
            User user;

            if (entity.getRole() == Role.TEACHER) {
                user = new Teacher();
            } else if (entity.getRole() == Role.STUDENT) {
                user = new Student();
            } else {
                user = new User();
            }

            user.setId(entity.getId());
            user.setName(entity.getName());
            user.setLogin(entity.getLogin());
            user.setPassword(entity.getPassword());
            user.setRole(entity.getRole());
            user.setStatus(Status.ACTIVE);

            User newUser = repository.save(user);
            

            log.info("Success, User by name = {} was updated", entity.getName());
            return new UserDTO(
                    newUser.getId(),
                    newUser.getName(),
                    newUser.getLogin(),
                    newUser.getPassword(),
                    newUser.getRole());

        } catch (Exception e) {
            log.error("Unable update User by name = {} due: {} ", entity.getName(), e.getMessage(), e);
            return entity;
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('access_to_users')")
    public UserDTO changeUserType(UserDTO entity) {
        log.debug("Trying to change User type = {} ", entity.getName());

        try {

            if (entity.getRole() == Role.USER) {
                repository.changeUserType("User", Role.USER.name(), entity.getId());
            } else if (entity.getRole() == Role.STUDENT) {
                repository.changeUserType("Student", Role.STUDENT.name(), entity.getId());
            } else if (entity.getRole() == Role.TEACHER) {
                repository.changeUserType("Teacher", Role.TEACHER.name(), entity.getId());
            } else if (entity.getRole() == Role.ADMIN) {
                repository.changeUserType("User", Role.ADMIN.name(), entity.getId());
            }

            return entity;

        } catch (Exception e) {
            log.error("Unable change User type = {} due: {} ", entity.getName(), e.getMessage(), e);
            return entity;
        }
    }

    @Override
    @Transactional
    public void doWithSystemUser(Runnable action) {
        try {
            User systemUser = getSystemUser();
            UserDetails userDetails = SecurityUser.fromUser(systemUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, systemUserPassword);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            action.run();
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private User getSystemUser() {
        return findByLogin(SYSTEM_USER_LOGIN).orElseGet(() ->
                repository.save(
                        new User(SYSTEM_USER_LOGIN, SYSTEM_USER_LOGIN, passwordEncoder.encode(systemUserPassword), Role.ADMIN, Status.ACTIVE)
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = repository.findByLogin(login)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User doesn't exists"));
        return SecurityUser.fromUser(user);
    }

}





