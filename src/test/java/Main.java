import com.github.ewoowe.UniversalIdSystem;
import org.apache.commons.lang3.tuple.Pair;

public class Main {

    public static void main(String[] args) {
        UniversalIdSystem uid = new UniversalIdSystem("src/test/resources/uid.json");
        String nokia = uid.getUid(null, "NOKIA", null);
        String gnb = uid.getUid(nokia, "GNB", "7");
        String cucp = uid.getUid(gnb, "CUCP", "2");
        String cell = uid.getUid(cucp, "CELL", "12");
        System.out.println("pause1");
        Pair<String, String> nokiaTypeId = uid.getTypeId("01");
        Pair<String, String> gnbTypeId = uid.getTypeId("0104007");
        Pair<String, String> cucpTypeId = uid.getTypeId("010400712");
        Pair<String, String> cellTypeId = uid.getTypeId("010400712112");
        System.out.println("pause2");
    }
}
