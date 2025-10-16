package hsf302.he187383.phudd.carrental.config;// DataSeeder.java

import hsf302.he187383.phudd.carrental.model.*;
import hsf302.he187383.phudd.carrental.model.enums.DocumentStatus;
import hsf302.he187383.phudd.carrental.model.enums.DocumentType;
import hsf302.he187383.phudd.carrental.model.enums.VehicleStatus;
import hsf302.he187383.phudd.carrental.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Profile({"dev","default"}) // chỉ chạy ở môi trường dev/default
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final UserDocumentRepository documentRepo;
    private final LocationRepository locationRepo;
    private final VehicleRepository vehicleRepo;
    private final VehicleImageRepository vehicleImageRepo;
    private final PasswordEncoder encoder; // nếu không dùng encoder, có thể bỏ

    @Override
    public void run(String... args) {
        // Roles
        Role admin = roleRepo.findByName("Admin")
                .orElseGet(() -> roleRepo.save(Role.builder().name("Admin").description("System admin").build()));
        Role ownerRole = roleRepo.findByName("Owner")
                .orElseGet(() -> roleRepo.save(Role.builder().name("Owner").description("Vehicle owner").build()));
        Role customerRole = roleRepo.findByName("Customer")
                .orElseGet(() -> roleRepo.save(Role.builder().name("Customer").description("Customer").build()));

        if (userRepo.count() == 0) {
            User adminUser = userRepo.save(User.builder()
                    .email("admin@gmail.com")
                    .passwordHash(encoder.encode("123456"))
                    .fullName("Super Admin")
                    .phone("0900000000")
                    .role(admin)
                    .status("active")
                    .isVerified(true)
                    .createdAt(LocalDateTime.now())
                    .build());

            User owner = userRepo.save(User.builder()
                    .email("owner@gmail.com")
                    .passwordHash(encoder.encode("123456"))
                    .fullName("Owner Demo")
                    .phone("0900000001")
                    .role(ownerRole)
                    .status("active")
                    .isVerified(true)
                    .createdAt(LocalDateTime.now())
                    .build());

            User customer = userRepo.save(User.builder()
                    .email("customer@gmail.com")
                    .passwordHash(encoder.encode("123456"))
                    .fullName("Customer Demo")
                    .dob(LocalDate.of(2000, 5, 20))
                    .phone("0900000002")
                    .role(customerRole)
                    .status("active")
                    .isVerified(false)
                    .createdAt(LocalDateTime.now())
                    .build());

            // --- Documents for CUSTOMER ---
            documentRepo.save(UserDocument.builder()
                    .user(customer)
                    .type(DocumentType.ID_CARD)
                    .fileUrl("https://example.com/uploads/customer-idcard.jpg")
                    .status(DocumentStatus.APPROVED)
                    .uploadedAt(LocalDateTime.now())
                    .build());

            documentRepo.save(UserDocument.builder()
                    .user(customer)
                    .type(DocumentType.DRIVER_LICENSE)
                    .fileUrl("https://example.com/uploads/customer-driver-license.jpg")
                    .status(DocumentStatus.APPROVED)
                    .uploadedAt(LocalDateTime.now())
                    .build());

            // --- Documents for OWNER ---
            documentRepo.save(UserDocument.builder()
                    .user(owner)
                    .type(DocumentType.VEHICLE_REG)
                    .fileUrl("https://example.com/uploads/owner-vehicle-reg.jpg")
                    .status(DocumentStatus.APPROVED)
                    .uploadedAt(LocalDateTime.now())
                    .build());

            // ================== SEED LOCATIONS ==================
            Location locHN = locationRepo.save(Location.builder()
                    .country("Vietnam")
                    .province("Hà Nội")
                    .city("Hà Nội")
                    .district("Cầu Giấy")
                    .ward("Dịch Vọng Hậu")
                    .addressLine("Tòa nhà Keangnam, Phạm Hùng")
                    .lat(21.028511)
                    .lng(105.804817)
                    .build());

            Location locHCM = locationRepo.save(Location.builder()
                    .country("Vietnam")
                    .province("Hồ Chí Minh")
                    .city("Hồ Chí Minh")
                    .district("Quận 1")
                    .ward("Bến Nghé")
                    .addressLine("92-94 Nam Kỳ Khởi Nghĩa")
                    .lat(10.776889)
                    .lng(106.700806)
                    .build());

            Location locDN = locationRepo.save(Location.builder()
                    .country("Vietnam")
                    .province("Đà Nẵng")
                    .city("Đà Nẵng")
                    .district("Hải Châu")
                    .ward("Thạch Thang")
                    .addressLine("36 Bạch Đằng, Hải Châu 1")
                    .lat(16.0678)
                    .lng(108.2208)
                    .build());

// ================== SEED VEHICLES ==================
            Vehicle v1 = vehicleRepo.save(Vehicle.builder()
                    .owner(owner)
                    .title("Toyota Vios 2020 - Sạch đẹp")
                    .brand("Toyota")
                    .model("Vios")
                    .type("car")
                    .licensePlate("30G-123.45")
                    .year(2020)
                    .color("Silver")
                    .seats(5)
                    .dailyPrice(new BigDecimal("650000"))
                    .depositAmount(new BigDecimal("2000000"))
                    .location(locHN)
                    .status(VehicleStatus.AVAILABLE)
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .build());

            Vehicle v2 = vehicleRepo.save(Vehicle.builder()
                    .owner(owner)
                    .title("Honda SH 150i - 2021")
                    .brand("Honda")
                    .model("SH 150i")
                    .type("motorbike")
                    .licensePlate("59A3-678.90")
                    .year(2021)
                    .color("Black")
                    .seats(2)
                    .dailyPrice(new BigDecimal("250000"))
                    .depositAmount(new BigDecimal("1000000"))
                    .location(locHCM)
                    .status(VehicleStatus.AVAILABLE)
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .build());

            Vehicle v3 = vehicleRepo.save(Vehicle.builder()
                    .owner(owner)
                    .title("VinFast Lux A2.0 - Sang trọng")
                    .brand("VinFast")
                    .model("Lux A2.0")
                    .type("car")
                    .licensePlate("43A-456.78")
                    .year(2021)
                    .color("White")
                    .seats(5)
                    .dailyPrice(new BigDecimal("800000"))
                    .depositAmount(new BigDecimal("2500000"))
                    .location(locDN)
                    .status(VehicleStatus.AVAILABLE)
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .build());

            Vehicle v4 = vehicleRepo.save(Vehicle.builder()
                    .owner(owner)
                    .title("Mazda CX-5 - Đời 2019")
                    .brand("Mazda")
                    .model("CX-5")
                    .type("car")
                    .licensePlate("30H-888.88")
                    .year(2019)
                    .color("Red")
                    .seats(5)
                    .dailyPrice(new BigDecimal("550000"))
                    .depositAmount(new BigDecimal("1500000"))
                    .location(locHN)
                    .status(VehicleStatus.AVAILABLE)
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .build());

            Vehicle v5 = vehicleRepo.save(Vehicle.builder()
                    .owner(owner)
                    .title("Yamaha Grande - Nhẹ nhàng")
                    .brand("Yamaha")
                    .model("Grande")
                    .type("motorbike")
                    .licensePlate("59B1-999.99")
                    .year(2022)
                    .color("Blue")
                    .seats(2)
                    .dailyPrice(new BigDecimal("220000"))
                    .depositAmount(new BigDecimal("800000"))
                    .location(locHCM)
                    .status(VehicleStatus.AVAILABLE)
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .build());

            Vehicle v6 = vehicleRepo.save(Vehicle.builder()
                    .owner(owner)
                    .title("Ford Ranger - Bán tải mạnh mẽ")
                    .brand("Ford")
                    .model("Ranger")
                    .type("truck")
                    .licensePlate("43C-111.11")
                    .year(2018)
                    .color("Blue")
                    .seats(4)
                    .dailyPrice(new BigDecimal("900000"))
                    .depositAmount(new BigDecimal("3000000"))
                    .location(locDN)
                    .status(VehicleStatus.AVAILABLE)
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .build());

// ================== SEED IMAGES ==================
            vehicleImageRepo.save(VehicleImage.builder().vehicle(v1).url("/img/car-rent-1.png").isPrimary(true).uploadedAt(LocalDateTime.now()).build());
            vehicleImageRepo.save(VehicleImage.builder().vehicle(v2).url("/img/car-rent-2.png").isPrimary(true).uploadedAt(LocalDateTime.now()).build());
            vehicleImageRepo.save(VehicleImage.builder().vehicle(v3).url("/img/car-rent-3.png").isPrimary(true).uploadedAt(LocalDateTime.now()).build());
            vehicleImageRepo.save(VehicleImage.builder().vehicle(v4).url("/img/car-rent-4.png").isPrimary(true).uploadedAt(LocalDateTime.now()).build());
            vehicleImageRepo.save(VehicleImage.builder().vehicle(v5).url("/img/car-rent-5.png").isPrimary(true).uploadedAt(LocalDateTime.now()).build());
            vehicleImageRepo.save(VehicleImage.builder().vehicle(v6).url("/img/car-rent-6.png").isPrimary(true).uploadedAt(LocalDateTime.now()).build());

        }
    }
}
