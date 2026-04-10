package edu.eci.tdd;

import edu.eci.tdd.model.Role;
import edu.eci.tdd.persistence.UserEntity;
import edu.eci.tdd.persistence.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DoswLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoswLibraryApplication.class, args);
    }

    
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verificamos si ya existe el usuario para no duplicarlo en cada reinicio
            if (userRepository.findByUsername("admin").isEmpty()) {
                UserEntity admin = new UserEntity();
                admin.setUsername("admin");
                
                //  La contraseña debe guardarse encriptada con BCrypt
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.LIBRARIAN);
                
                userRepository.save(admin);
                
                System.out.println("*****************************************************");
                System.out.println("USUARIO DE PRUEBA CREADO EXITOSAMENTE");
                System.out.println("Username: admin");
                System.out.println("Password: admin123");
                System.out.println("Rol: LIBRARIAN");
                System.out.println("*****************************************************");
            }
        };
    }
}