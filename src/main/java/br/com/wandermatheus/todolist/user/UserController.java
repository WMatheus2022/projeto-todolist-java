package br.com.wandermatheus.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * modificador
 * public
 * private
 * protected
 * enum
 * interface
 */

@RestController
@RequestMapping("/users")
public class UserController {

   /**
    * String (texto)
    * Integer (int) números inteiros
    * Double (double) Números com casas decímas 0.000
    * Float (float) Números flutuantes 0.000
    * Char (caracteres)
    * Date (data)
    * Void (sem retorno) quando o método não tem retorno
    */

   @Autowired
   private IUserRepository userRepository;

   @PostMapping("/")
   public ResponseEntity create(@RequestBody UserModel userModel) {
      var user = this.userRepository.findByUsername(userModel.getUsername());

      if (user != null) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já existe!");
      }

      var passwordHashred = BCrypt.withDefaults()
            .hashToString(12, userModel.getPassword().toCharArray());

      userModel.setPassword(passwordHashred);

      var userCreated = this.userRepository.save(userModel);
      return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
   }
}
