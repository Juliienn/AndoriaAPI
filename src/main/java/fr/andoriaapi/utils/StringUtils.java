package fr.andoriaapi.utils;

public class StringUtils {

    public static boolean isNumeric(String sequence){
        return sequence.matches("^-?\\d+(?:\\.\\d+)*$");
    }

    public static boolean isNumeric(char car){ return String.valueOf(car).matches("\\d");}
}
