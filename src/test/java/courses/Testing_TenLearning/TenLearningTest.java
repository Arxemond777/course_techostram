package courses.Testing_TenLearning;

import com.sun.istack.NotNull;
import courses.ThreeLearning;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TenLearningTest
{
    static final Map<String, byte[]> testData = new HashMap<>();

    @BeforeClass
    public static void init() {
        System.out.println(1);
        testData.put("", new byte[0]);
        testData.put("01", new byte[]{1});
        testData.put("01020d112d7f", new byte[] {1,2,13,17,45,127});
        testData.put("00fff21180", new byte[] {0, -1, -14, 17, -128 });
    }

    @Before
    public void setup() {
        System.out.println(2);
    }

    //@Ignore
    @Test
    public void isEmpty() throws Exception {
//        Assert.assertEquals(1, TenLearning.a());
    }

    @Test
    public void toHexString() throws Exception {
        for (Map.Entry<String, byte[]> entry : testData.entrySet()) {
            //System.out.println(entry.getKey() + " | " + entry.getValue());
            String expected = entry.getKey();

            String actual = TenLearning.toHexString(entry.getValue());
            Assert.assertEquals(expected, actual);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException() {
        TenLearning.toHexString(null);
    }

}