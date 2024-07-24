package com.lblandi.homebanking.api.entities;

import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lblandi.homebanking.api.enums.RoleUserEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "banking_users")
public class BankingUser {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	@NotBlank
	@Length(min = 3)
	private String username;

	@NotBlank
	@Length(min = 8)
	private String passwd;

	@Builder.Default
	private Long loginCount = Long.valueOf(0);

	@Builder.Default
	private Long badLoginCount = Long.valueOf(0);;

	@Builder.Default
	private Boolean blocked = Boolean.FALSE;

	@NotNull
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<RoleUserEnum> roles;

	@JsonIgnore
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private Person owner;
}
