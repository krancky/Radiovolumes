package bhouse.radiovolumes;


        import java.util.ArrayList;
        import java.util.LinkedHashMap;
        import java.util.List;

        import bhouse.radiovolumes.processor.NodeAreaTemplate;
        import bhouse.radiovolumes.processor.TumorAreaTemplate;

public class ExpandableListDataPump {
    public static LinkedHashMap<String, List<String>> getNData(List<NodeAreaTemplate> nodeAreaTemplateList) {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();


        List<String> nList = new ArrayList<String>();
        for (NodeAreaTemplate nodeAreaTemplate : nodeAreaTemplateList){
            nList.add(nodeAreaTemplate.getNodeLocation());
        }
        expandableListDetail.clear();
        expandableListDetail.put("N EXTENSION", nList);
        return expandableListDetail;
    }

    public static LinkedHashMap<String, List<String>> getTData(List<TumorAreaTemplate> tumorAreaTemplateList) {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();


        List<String> tList = new ArrayList<String>();
        for (TumorAreaTemplate tumorAreaTemplate : tumorAreaTemplateList){
            tList.add(tumorAreaTemplate.getLocation());
        }
        expandableListDetail.clear();
        expandableListDetail.put("T EXTENSION", tList);
        return expandableListDetail;
    }


}