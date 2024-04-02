package agora.postman.assertion;

import agora.postman.assertion.model.Variable;

/**
 * @author Juan C. Alonso
 */
public class Draft {

    // TODO: Convert all this into test cases
    public static void main(String[] args) {
//        Variable variable1 = new Variable("size(return.names[])");
//        System.out.println(variable1);

//        Variable variable2 = new Variable("size(return.names[])-1");
//        System.out.println(variable2);


        String regex = ".*[+-]{1}[0-9]{1,}$";

        String test = "return.user.age+10";

        System.out.println(test.matches(regex));


    }
}
