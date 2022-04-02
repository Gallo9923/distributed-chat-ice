import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args){
        
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3));
        
        System.out.println(numbers.toString());

        for (Integer integer : numbers){
            
            System.out.println(integer);

            if ((int)integer == 2){
                numbers.remove(integer);
            }

        }

        System.out.println(numbers.toString());

    }
    
}
