package hsf302.he187383.phudd.carrental.config;// DataSeeder.java

import hsf302.he187383.phudd.carrental.model.Role;
import hsf302.he187383.phudd.carrental.model.User;
import hsf302.he187383.phudd.carrental.repository.RoleRepository;
import hsf302.he187383.phudd.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile({"dev","default"}) // chỉ chạy ở môi trường dev/default
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder; // nếu không dùng encoder, có thể bỏ

    @Override
    public void run(String... args) {
        // 1) Seed Roles
        Role admin = roleRepo.findByName("Admin").orElseGet(() ->
                roleRepo.save(Role.builder().name("Admin").description("System admin").build()));
        Role owner = roleRepo.findByName("Owner").orElseGet(() ->
                roleRepo.save(Role.builder().name("Owner").description("Vehicle owner").build()));
        Role customer = roleRepo.findByName("Customer").orElseGet(() ->
                roleRepo.save(Role.builder().name("Customer").description("Customer").build()));

        if (userRepo.count() == 0) {
            // 2) Seed Users
            userRepo.save(User.builder()
                    .email("admin@gmail.com")
                    .passwordHash(encoder.encode("123456")) // hoặc để plain nếu chưa cần
                    .fullName("Super Admin")
                    .phone("0900000000")
                    .role(admin)
                    .status("active")
                    .isVerified(true)
                    .createdAt(LocalDateTime.now())
                    .build());

            userRepo.save(User.builder()
                    .email("owner@gmail.com")
                    .passwordHash(encoder.encode("123456"))
                    .fullName("Owner Demo")
                    .phone("0900000001")
                    .role(owner)
                    .status("active")
                    .isVerified(true)
                    .createdAt(LocalDateTime.now())
                    .build());

            userRepo.save(User.builder()
                    .email("customer@gmail.com")
                    .passwordHash(encoder.encode("123456"))
                    .fullName("Customer Demo")
                    .phone("0900000002")
                    .role(customer)
                    .status("active")
                    .isVerified(false)
                    .createdAt(LocalDateTime.now())
                    .build());
        }
    }
}
