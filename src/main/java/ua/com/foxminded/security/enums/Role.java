package ua.com.foxminded.security.enums;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {

	USER(Set.of(Permission.READ)),
	STUDENT(Set.of(Permission.READ)),
	TEACHER(Set.of(Permission.READ, Permission.WRITE)),
	ADMIN(Set.of(Permission.READ, Permission.WRITE, Permission.DELETE, 
			Permission.ACCESS_TO_TEACHERS, Permission.ACCESS_TO_USERS));
	
	private final Set<Permission> permissions;

	private Role(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}
	
	public Set<SimpleGrantedAuthority> getAuthorities(){
		return getPermissions().stream()
				.map(per -> new SimpleGrantedAuthority(per.getPermission()))
				.collect(Collectors.toSet());
	}

}