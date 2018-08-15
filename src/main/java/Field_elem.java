import java.util.HashMap;
import java.util.Map;

public class Field_elem {
    private Map<String, String> map = new HashMap<>();
    private String type;

    public void putt(String elem, String attr) {
        map.put(elem, attr);
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

}
