package com.bruno.testesunitarios.services;

import com.bruno.testesunitarios.models.domain.User;
import com.bruno.testesunitarios.models.dto.UserSaveDTO;
import com.bruno.testesunitarios.repositories.UserRepository;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    /**
     * Realiza INSERT (se id == null) ou UPDATE (se id != null).
     * @param dto DTO do objeto a ser persistido.
     * @return {@code User}
     * @throws SQLIntegrityConstraintViolationException Caso algum campo UNIQUE seja violado (já exista)
     * @throws DataIntegrityViolationException Caso algum campo NOT NULL seja violado (não tenha sido valorado)
     */
    public User save(UserSaveDTO dto) throws DataIntegrityViolationException {
        return userRepository.save(dto.toUser());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id)
            .orElse(null);
    }

    public List<User> search(String search) {
        return search == null || search.isBlank()
            ? userRepository.findAll()
            : userRepository.search(search);
    }

    public User delete(int id) {
        User user = userRepository.findById(id).orElse(null);

        if(user != null)
            userRepository.deleteById(id);

        return user;
    }
}
