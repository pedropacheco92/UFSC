package main;

import java.io.Serializable;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Clone implements Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<Integer, Valor> map;

}
