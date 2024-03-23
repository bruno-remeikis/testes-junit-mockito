package com.bruno.testesunitarios.controllers;

import com.bruno.testesunitarios.models.domain.User;
import com.bruno.testesunitarios.models.dto.UserSaveDTO;
import com.bruno.testesunitarios.services.UserService;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> search(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(
            userService.search(search)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        return ResponseEntity.ok(
            userService.findById(id)
        );
    }

    /*@GetMapping
    public ResponseEntity<List<User>> search() {
        return ResponseEntity.ok(
                userService.search(search)
        );
    }*/

    @PostMapping
    public ResponseEntity<User> save(@RequestBody UserSaveDTO dto) {
        try {
            return ResponseEntity.ok(userService.save(dto));
        }
        catch(DataIntegrityViolationException e)
        {
            return
                e.getCause() instanceof ConstraintViolationException
                    ? new ResponseEntity("Já existe um usuário cadastrado com o email " + dto.getEmail(), HttpStatus.CONFLICT) :
                e.getCause() instanceof PropertyValueException
                    ? new ResponseEntity("Insira todos os campos", HttpStatus.BAD_REQUEST) :
                ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id)
    {
        User user = userService.delete(id);

        if(user == null)
            return new ResponseEntity(
                "Não existe usuário com o ID " + id,
                HttpStatus.NOT_FOUND
            );

        return ResponseEntity.ok(user);
    }
}
