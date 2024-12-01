package cia.northboat.auth.key;

import it.unisa.dia.gas.jpbc.Element;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchnorrKey {

    // Z=-xP / x
    Element Z;
    // P
    Element P;

}
