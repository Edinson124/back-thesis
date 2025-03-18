package com.yawarSoft;

import com.yawarSoft.Entities.*;
import com.yawarSoft.Enums.UserStatus;
import com.yawarSoft.Repositories.BloodBankRepository;
import com.yawarSoft.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class YawarSoftApplication {

	//Bean que se crea para ejecutar codigo al momento de iniciar la aplicacion
	public static void main(String[] args) {
		SpringApplication.run(YawarSoftApplication.class, args);
	}
//	@Bean
//	CommandLineRunner init(UserRepository userRepository, BloodBankRepository bloodBankRepository){
//		return args -> {
//			BloodBankEntity bloodBank1 = BloodBankEntity.builder()
//					.type("Tipo B")
//					.region("LIMA")
//					.province("LIMA")
//					.district("LA VICTORIA")
//					.address("Av. Grau 123")
//					.status("ACTIVE")
//					.name("Banco de sangre del Hospital Almenara")
//					.build();
//
//			BloodBankEntity bloodBank2 = BloodBankEntity.builder()
//					.type("Tipo IA")
//					.region("LIMA")
//					.province("LIMA")
//					.district("ATE")
//					.address("Av. Ate 222")
//					.status("ACTIVE")
//					.name("Banco de sangre del Hospital de Ate")
//					.build();
//
//			List<BloodBankEntity> savedBloodBanks = bloodBankRepository.saveAll(List.of(bloodBank1, bloodBank2));
//
//			BloodBankEntity bloodBank1Saved = savedBloodBanks.get(0);
//			BloodBankEntity bloodBank2Saved = savedBloodBanks.get(1);
//
//
//			/* Create PERMISSIONS */
//			PermissionEntity createPermission = PermissionEntity.builder()
//					.name("CREATE")
//					.build();
//
//			PermissionEntity readPermission = PermissionEntity.builder()
//					.name("READ")
//					.build();
//
//			PermissionEntity updatePermission = PermissionEntity.builder()
//					.name("UPDATE")
//					.build();
//
//			PermissionEntity deletePermission = PermissionEntity.builder()
//					.name("DELETE")
//					.build();
//
//			PermissionEntity refactorPermission = PermissionEntity.builder()
//					.name("REFACTOR")
//					.build();
//
//			/* Create ROLES */
//			RoleEntity roleAdmin = RoleEntity.builder()
//					.name(RoleEnum.ADMIN.toString())
//					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
//					.build();
//
//			RoleEntity roleUser = RoleEntity.builder()
//					.name(RoleEnum.USER.toString())
//					.permissionList(Set.of(createPermission, readPermission))
//					.build();
//
//			RoleEntity roleInvited = RoleEntity.builder()
//					.name(RoleEnum.INVITED.toString())
//					.permissionList(Set.of(readPermission))
//					.build();
//
//			RoleEntity roleDeveloper = RoleEntity.builder()
//					.name(RoleEnum.DEVELOPER.toString())
//					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
//					.build();
//
//			/* CREATE USERS */
//			UserEntity userSantiago = UserEntity.builder()
//					.username("santiago")
//					.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
//					.documentType("DNI")
//					.documentNumber("07590665")
//					.email("santiago@gmail.com")
//					.phone("999000111")
//					.address("Jr.Huamanga 234")
//					.firstName("Santiago Jose")
//					.lastName("Perez")
//					.secondLastName("Pinasco")
//					.status(UserStatus.ACTIVE)
//					.isEnabled(true)
//					.accountNoExpired(true)
//					.accountNoLocked(true)
//					.credentialNoExpired(true)
//					.region("LIMA")
//					.province("LIMA")
//					.district("LIMA")
//					.birthDate(LocalDate.of(2000,10,10))
//					.roles(Set.of(roleAdmin))
//					.bloodBank(bloodBank1Saved)
//					.build();
//
//			UserEntity userDaniel = UserEntity.builder()
//					.username("daniel")
//					.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
//					.documentType("DNI")
//					.documentNumber("77590111")
//					.email("daniel@gmail.com")
//					.phone("987000123")
//					.address("Av.Paseo de la Republica 2290")
//					.firstName("Daniel Rodrigo")
//					.lastName("Suarez")
//					.secondLastName("Abad")
//					.status(UserStatus.ACTIVE)
//					.isEnabled(true)
//					.accountNoExpired(true)
//					.accountNoLocked(true)
//					.credentialNoExpired(true)
//					.roles(Set.of(roleUser))
//					.region("LIMA")
//					.province("LIMA")
//					.district("RIMAC")
//					.birthDate(LocalDate.of(2000,1,20))
//					.bloodBank(bloodBank2Saved)
//					.build();
//
//			UserEntity userAndrea = UserEntity.builder()
//					.username("andrea")
//					.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
//					.documentType("DNI")
//					.documentNumber("76689201")
//					.email("andrea@gmail.com")
//					.phone("964890101")
//					.address("Calle Los Laureles 798")
//					.firstName("Andrea Susy")
//					.lastName("Oslo")
//					.secondLastName("Rondon")
//					.status(UserStatus.ACTIVE)
//					.isEnabled(true)
//					.accountNoExpired(true)
//					.accountNoLocked(true)
//					.credentialNoExpired(true)
//					.roles(Set.of(roleInvited))
//					.region("LIMA")
//					.province("LIMA")
//					.district("LINCE")
//					.birthDate(LocalDate.of(2000,5,22))
//					.bloodBank(bloodBank1Saved)
//					.build();
//
//			UserEntity userAnyi = UserEntity.builder()
//					.username("anyi")
//					.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
//					.documentType("CE")
//					.documentNumber("001043328")
//					.email("anyi@gmail.com")
//					.phone("987019567")
//					.address("Av.2 de Octubre 110")
//					.firstName("Anyi Susana")
//					.lastName("Pando")
//					.secondLastName("Alvarado")
//					.status(UserStatus.ACTIVE)
//					.isEnabled(true)
//					.accountNoExpired(true)
//					.accountNoLocked(true)
//					.credentialNoExpired(true)
//					.roles(Set.of(roleDeveloper))
//					.region("LIMA")
//					.province("LIMA")
//					.district("LA VICTORIA")
//					.birthDate(LocalDate.of(1990,9,25))
//					.bloodBank(bloodBank2Saved)
//					.build();
//
//			userRepository.saveAll(List.of(userSantiago, userDaniel, userAndrea, userAnyi));
//		};
//	}

}
