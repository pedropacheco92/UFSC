package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import model.enums.Tipo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Constante implements CategoriaId {

	protected Object value;

	protected Tipo tipo;

}
