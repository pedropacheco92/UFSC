package main;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Valor {

    VAZIO("-"), 
    X("X"), 
    O("O");

    private String value;

}
