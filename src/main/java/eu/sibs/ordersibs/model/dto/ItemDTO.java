package eu.sibs.ordersibs.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
	
	@NotBlank(message="O nome deve ser informado")
	@Size(min=2, message="O nome deve ter no mínimo 2 caracteres")
	private String name;
}
