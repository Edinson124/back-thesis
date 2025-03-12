package com.yawarSoft;

import com.yawarSoft.Entities.PermissionEntity;
import com.yawarSoft.Entities.RoleEntity;
import com.yawarSoft.Entities.RoleEnum;
import com.yawarSoft.Entities.UserEntity;
import com.yawarSoft.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class YawarSoftApplication {

	public static void main(String[] args) {
		SpringApplication.run(YawarSoftApplication.class, args);
	}
	//Bean que se crea para ejecutar codigo al momento de iniciar la aplicacion
//	@Bean
//	CommandLineRunner init(UserRepository userRepository){
//		return args -> {
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
//					.roleEnum(RoleEnum.ADMIN)
//					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
//					.build();
//
//			RoleEntity roleUser = RoleEntity.builder()
//					.roleEnum(RoleEnum.USER)
//					.permissionList(Set.of(createPermission, readPermission))
//					.build();
//
//			RoleEntity roleInvited = RoleEntity.builder()
//					.roleEnum(RoleEnum.INVITED)
//					.permissionList(Set.of(readPermission))
//					.build();
//
//			RoleEntity roleDeveloper = RoleEntity.builder()
//					.roleEnum(RoleEnum.DEVELOPER)
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
//					.firstNames("Santiago Jose")
//					.lastName("Perez")
//					.secondLastName("Pinasco")
//					.status("ACTIVE")
//					.isEnabled(true)
//					.accountNoExpired(true)
//					.accountNoLocked(true)
//					.credentialNoExpired(true)
//					.roles(Set.of(roleAdmin))
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
//					.firstNames("Daniel Rodrigo")
//					.lastName("Suarez")
//					.secondLastName("Abad")
//					.status("ACTIVE")
//					.isEnabled(true)
//					.accountNoExpired(true)
//					.accountNoLocked(true)
//					.credentialNoExpired(true)
//					.roles(Set.of(roleUser))
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
//					.firstNames("Andrea Susy")
//					.lastName("Oslo")
//					.secondLastName("Rondon")
//					.status("ACTIVE")
//					.isEnabled(true)
//					.accountNoExpired(true)
//					.accountNoLocked(true)
//					.credentialNoExpired(true)
//					.roles(Set.of(roleInvited))
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
//					.firstNames("Anyi Susana")
//					.lastName("Pando")
//					.secondLastName("Alvarado")
//					.status("ACTIVE")
//					.isEnabled(true)
//					.accountNoExpired(true)
//					.accountNoLocked(true)
//					.credentialNoExpired(true)
//					.roles(Set.of(roleDeveloper))
//					.build();
//
//			userRepository.saveAll(List.of(userSantiago, userDaniel, userAndrea, userAnyi));
//		};
//	}

}
