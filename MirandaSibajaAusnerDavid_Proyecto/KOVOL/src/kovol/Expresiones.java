package kovol;

public class Expresiones {

    //Enum para validar las expresiones de palabras identificadoras del usuario y números.
    enum misExpresiones {
        nombresvalidos("[a-zA-Z][a-zA-Z0-9_]*"), //Palabras que no empiezen por números.
        numeros("[0-9]*"), //Números.
        DISPLAY("[\\s]*DISPLAY[\\s]*[\\\"][a-zA-Z0-9\\W]*[\\\"]{1}[\\s]*[a-zA-Z]*[\\W\\w]*"),
        IGUALDAD("[a-zA-Z0-9]*[\\s]*[=]{1}"),
        SUMA("[a-zA-Z0-9]*[\\s]*[+]{1}[\\s]*[a-zA-Z0-9]*"),
        RESTA("[a-zA-Z0-9]*[\\s]*[-]{1}[\\s]*[a-zA-Z0-9]*"),
        MULTIPLICACION("[a-zA-Z0-9]*[\\s]*[*]{1}[\\s]*[a-zA-Z0-9]*"),
        DIVISION("[a-zA-Z0-9]*[\\s]*[/]{1}[\\s]*[a-zA-Z0-9]*"),
        aperturaParentesis("[(]*[a-zA-Z0-9]"),
        cierreParentesis("[)]*[a-zA-Z0-9]"),
        PIC("[\\s]*PIC[\\s]*[\\W\\w]*"),
        puntoFinal("[a-zA-Z]*[.]{1}");
        

        public final String pattern; //String pattern.

        misExpresiones(String texto) { //Inicializa pattern.
            this.pattern = texto;
        }
    }
}
