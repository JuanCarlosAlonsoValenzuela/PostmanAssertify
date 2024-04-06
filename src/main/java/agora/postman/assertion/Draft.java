package agora.postman.assertion;

import agora.postman.assertion.model.Variable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static agora.postman.assertion.testScript.PostmanVariableName.getPostmanVariableName;

/**
 * @author Juan C. Alonso
 */
public class Draft {

    public static void main(String[] args) {
        try {
            // String a codificar
//            String originalString = "Better Call Saul";
//            String originalString = "Don't Look Up";
            String originalString = "Tech-Phantoms";

            // Codificar
            String encodedString = URLEncoder.encode(originalString, "UTF-8");
            System.out.println("String codificado: " + encodedString);

            // Decodificar
            String decodedString = URLDecoder.decode(encodedString, "UTF-8");
            System.out.println("String decodificado: " + decodedString);
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // TODO: Convert all this into test cases
//    public static void main(String[] args) {
//        Variable variable1 = new Variable("size(return.names[])");
//        System.out.println(variable1);

//        Variable variable2 = new Variable("size(return.names[])-1");
//        System.out.println(variable2);


//        String regex = ".*[+-]{1}[0-9]{1,}$";
//        String test = "return.user.age+10";
//        System.out.println(test.matches(regex));

//        int shift = 0;
//        String result = String.format("%+3d", shift);
//        String result = String.format(" %+d", shift);
//        System.out.println(result);

//        String result = String.format(
//                "%s[%s%s]",
//                "thirdString", "secondString", " +1");
//        System.out.println(result);

//        String test = "return.array[return.user.age -1]";
//        System.out.println(test.matches(".* [+-]{1}[0-9]{1,}$"));

        // Test array shift
//        Variable variable1 = new Variable("return.user.age -1");
//        System.out.println(variable1.getPostmanVariableName());

//        Variable variable2 = new Variable("return.user.age +1");
//        System.out.println(variable2.getPostmanVariableName());

//        Variable variable3 = new Variable("size(return.array[]) +1");
//        System.out.println(variable3.getPostmanVariableName());

//        Variable variable4 = new Variable("return.user.age");
//        System.out.println(variable4.getPostmanVariableName());

//        System.out.println(getPostmanVariableName("return.data.results[0]"));

//    }
}
