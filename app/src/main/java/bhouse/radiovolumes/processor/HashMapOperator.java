package bhouse.radiovolumes.processor;

import android.util.Log;

import java.util.List;


/**
 * The type Hash map operator.
 * Deals with the computation of CTV56N  and CTV56T target lymph nodes computation, from CANCER spread lymph nodes data and CATALOG of elementary target volumes
 * Computation of target volumes is assumed to be a linear combination of elementary target volumes associated to elementary spread lymph nodes areas.
 */
public class HashMapOperator {

    /**
     * C tv 56 n case ctv 56 n case.
     *
     * @param ctv56NUCaseList the ctv 56 nu case list
     * @param cancer          the cancer
     * @param cTV56NCase      the c tv 56 n case (empty)
     * @return the ctv 56 n case (stores map of computed target N volumes)
     */
    public void cTV56NCase(List<CTV56NUCase> ctv56NUCaseList, Cancer cancer, CTV56NCase cTV56NCase){
        // Sets the name of structure holding the lymph nodes to irradiate
        cTV56NCase.setCaseName("Lymph nodes to irradiate");
        // Iterates on all elementary cases to associate to spread volumes in actual cancer, then computes linear combination of target volumes and returns final CTV56NCase
        for (TumorAreaTemplate cancerTVolumes : cancer.getCancerTVolumes()){

            for (CTV56NUCase ctv56NUCase:ctv56NUCaseList) {
                if (cancer.getCancerNVolumes().isEmpty()) {
                    if (cancerTVolumes.getLeftContent().equals("1")){
                        if (cancerTVolumes.getLocation().equals(ctv56NUCase.getLocation()) && ctv56NUCase.getSide().equals("Gauche") && ctv56NUCase.getSpreadLocation().isEmpty()) {
                            cTV56NCase.addAllTVolumeToMap(ctv56NUCase.getuCaseTVolumes());
                            Log.i("touche", "coule");
                        }
                    }
                    if (cancerTVolumes.getRightContent().equals("1")){
                        if (cancerTVolumes.getLocation().equals(ctv56NUCase.getLocation()) && ctv56NUCase.getSide().equals("Droite") && ctv56NUCase.getSpreadLocation().isEmpty()) {
                            cTV56NCase.addAllTVolumeToMap(ctv56NUCase.getuCaseTVolumes());
                            Log.i("touche", "coule");
                        }
                    }

                } else {
                    for (NodeAreaTemplate nodeAreaTemplate : cancer.getCancerNVolumes()) {
                        if (nodeAreaTemplate.getLeftContent().equals("1")){
                            if (cancerTVolumes.getLeftContent().equals("1"))
                            {
                                if (cancerTVolumes.getLocation().equals(ctv56NUCase.getLocation()) && nodeAreaTemplate.getNodeLocation().equals(ctv56NUCase.getSpreadLocation()) && ctv56NUCase.getSide().equals("Gauche") && ctv56NUCase.getSpreadSide().equals("Gauche")) {
                                    cTV56NCase.addAllTVolumeToMap(ctv56NUCase.getuCaseTVolumes());
                                }
                            }
                            if (cancerTVolumes.getRightContent().equals("1"))
                            {
                                if (cancerTVolumes.getLocation().equals(ctv56NUCase.getLocation()) && nodeAreaTemplate.getNodeLocation().equals(ctv56NUCase.getSpreadLocation()) && ctv56NUCase.getSide().equals("Droite") && ctv56NUCase.getSpreadSide().equals("Gauche")) {
                                    cTV56NCase.addAllTVolumeToMap(ctv56NUCase.getuCaseTVolumes());
                                }
                            }


                        }
                        if (nodeAreaTemplate.getRightContent().equals("1")){
                            if (cancerTVolumes.getLeftContent().equals("1"))
                            {
                                if (cancerTVolumes.getLocation().equals(ctv56NUCase.getLocation()) && nodeAreaTemplate.getNodeLocation().equals(ctv56NUCase.getSpreadLocation()) && ctv56NUCase.getSide().equals("Gauche") && ctv56NUCase.getSpreadSide().equals("Droite")) {
                                    cTV56NCase.addAllTVolumeToMap(ctv56NUCase.getuCaseTVolumes());
                                }
                            }
                            if (cancerTVolumes.getRightContent().equals("1"))
                            {
                                if (cancerTVolumes.getLocation().equals(ctv56NUCase.getLocation()) && nodeAreaTemplate.getNodeLocation().equals(ctv56NUCase.getSpreadLocation()) && ctv56NUCase.getSide().equals("Droite") && ctv56NUCase.getSpreadSide().equals("Droite")) {
                                    cTV56NCase.addAllTVolumeToMap(ctv56NUCase.getuCaseTVolumes());
                                }
                            }
                        }

                    }

                }
            }
        }


    }

    public void cTV56TCase(List<CTV56TUCase> ctv56TUCaseList, Cancer cancer, CTV56TCase cTV56TCase){
        // Sets the name of structure holding the lymph nodes to irradiate
        cTV56TCase.setCaseName("Prophylactic peritumoral area to irradiate");
        // Iterates on all elementary cases to associate to spread volumes in actual cancer, then computes linear combination of target volumes and returns final CTV56NCase

        for (CTV56TUCase ctv56TUCase:ctv56TUCaseList){
                for (TumorAreaTemplate cancerTVolumes : cancer.getCancerTVolumes()){
                    if (cancerTVolumes.getLeftContent().equals("1")){
                        if (cancerTVolumes.getLocation().equals(ctv56TUCase.getLocation()) && ctv56TUCase.getSide().equals("Gauche")){
                            cTV56TCase.addAllTVolumeToMap(ctv56TUCase.getuCaseTVolumes());
                        }
                    }
                    if (cancerTVolumes.getRightContent().equals("1")){
                        if (cancerTVolumes.getLocation().equals(ctv56TUCase.getLocation()) && ctv56TUCase.getSide().equals("Droite")){
                            cTV56TCase.addAllTVolumeToMap(ctv56TUCase.getuCaseTVolumes());
                        }
                    }

                }

        }

    }

    public void update(List<CTV56TUCase> ctv56TUCaseList, List<CTV56NUCase> ctv56NUCaseList, Cancer cancer, CTV56TCase cTV56TCase, CTV56NCase cTV56NCase){
        this.cTV56TCase(ctv56TUCaseList, cancer, cTV56TCase);
        this.cTV56NCase(ctv56NUCaseList, cancer, cTV56NCase);
    }

}
