package courses.SevenLearningAboutClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by arxemond777 on 07.02.17.
 */
public class CollectorsExample
{
    public static void main(String[] args) {
        List<Integer> s = new ArrayList<>();
        s.add(3);
        s.add(1);
        s.add(2);

        List<Number> l =  s.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(l );
    }
}
