public class Canaan {
    private static String [] fearSets = {"Poco","Medio","Alto", "Muy alto"};
    private static String [] regretSets = {"Muy poco", "Poco", "Regular", "Mucho"};
    private static String [] revengeSets = {"Poco", "Mucho"};
    private static String [] rejectSets = {"Poco", "Medio", "Alto"};
    private static String [] hateSets = {"Poco", "Mucho"};
    private static String [] willSets = {"Baja", "Fuerte"};
    private static String [] forgivenessSets = {"Imperdonable", "Indeciso", "Perdonable"};
    private static String [] afterlifeSets = {"Inconsciente Colectivo", "Gnosis", "Testamento"};

    private static double [] fear = new double[4];
    private static double [] regret = new double[4];
    private static double [] revenge = new double[2];
    private static double [] reject = new double[3];
    private static double [] hate = new double[2];
    private static double [] will = new double[2];
    private static double [] forgiveness = new double[3];
    private static double [] afterlife = new double[3];

    private static int posNivMemMay(double [] nivsMem){
        int posMay=0;
        for(int i=0; i<nivsMem.length; i++){
            if (nivsMem[i]>nivsMem[posMay]){
                posMay=i;
            }
        }
        return posMay;
    }

    public static void memberShipFear(double sliderFear){
        fear[0]=BiblioFuzzy.curvaZ(sliderFear, 25, 50);
        fear[1]=BiblioFuzzy.triangularSuave(sliderFear, 25, 50, 75);
        fear[2]=BiblioFuzzy.triangularSuave(sliderFear, 50, 75, 100);
        fear[3]=BiblioFuzzy.curvaS(sliderFear, 75, 100);
        //muestraNivMemMay(("Membresías Cuello="+datoNitidoCuello),nivsMemCuello);
    }

    public static void memberShipRegret(double sliderRegret){
        regret[0]=BiblioFuzzy.curvaZ(sliderRegret, 25, 50);
        regret[1]=BiblioFuzzy.triangularSuave(sliderRegret, 25, 50, 75);
        regret[2]=BiblioFuzzy.triangularSuave(sliderRegret, 50, 75, 100);
        regret[3]=BiblioFuzzy.curvaS(sliderRegret, 75, 100);

    }

    public static void memberShipRevenge(double sliderRevenge){
        revenge[0]=BiblioFuzzy.curvaZ(sliderRevenge, 50, 100);
        revenge[1]=BiblioFuzzy.curvaS(sliderRevenge, 50, 100);

    }

    public static void memberShipReject(double sliderReject){
        reject[0]=BiblioFuzzy.curvaZ(sliderReject, 33, 66);
        reject[1]=BiblioFuzzy.triangularSuave(sliderReject, 33, 66, 99);
        reject[2]=BiblioFuzzy.curvaS(sliderReject, 66, 99);

    }

    public static void memberShipHate(double sliderHate){
        hate[0]=BiblioFuzzy.curvaZ(sliderHate, 50, 100);
        hate[1]=BiblioFuzzy.curvaS(sliderHate, 50, 100);

    }

    public static void memberShipWill(double sliderWill){
        will[0]=BiblioFuzzy.curvaZ(sliderWill, 70, 100);
        will[1]=BiblioFuzzy.curvaS(sliderWill, 70, 100);

    }

    public static void memberShipForgiveness(double sliderForgiveness){
        forgiveness[0]=BiblioFuzzy.curvaZ(sliderForgiveness, 33, 66);
        forgiveness[1]=BiblioFuzzy.triangularSuave(sliderForgiveness, 33, 66, 99);
        forgiveness[2]=BiblioFuzzy.curvaS(sliderForgiveness, 66, 99);

    }

    public static String fuzzyFear(double sliderFear){
        String set = "";
        memberShipFear(sliderFear);
        set=fearSets[posNivMemMay(fear)];
        return set;
    }

    public static  String fuzzyRegret(double sliderRegret){
        String set = "";
        memberShipRegret(sliderRegret);
        set=regretSets[posNivMemMay(regret)];
        return set;
    }

    public static String fuzzyRevenge(double sliderRevenge){
        String set = "";
        memberShipRevenge(sliderRevenge);
        set=revengeSets[posNivMemMay(revenge)];
        return set;
    }

    public static String fuzzyReject(double sliderReject){
        String set = "";
        memberShipReject(sliderReject);
        set=rejectSets[posNivMemMay(reject)];
        return set;
    }

    public static String fuzzyHate(double sliderHate){
        String set = "";
        memberShipHate(sliderHate);
        set=hateSets[posNivMemMay(hate)];
        return set;
    }

    public static String fuzzyWill(double sliderWill){
        String set = "";
        memberShipWill(sliderWill);
        set=willSets[posNivMemMay(will)];
        return set;
    }

    public static String fuzzyForgiveness(double sliderForgiveness){
        String set = "";
        memberShipForgiveness(sliderForgiveness);
        set=forgivenessSets[posNivMemMay(forgiveness)];
        return set;
    }

    public static String inferAfterlife(String diffuseFear,
                                        String diffuseRegret,
                                        String diffuseRevenge,
                                        String diffuseReject,
                                        String diffuseHate,
                                        String diffuseWill,
                                        String diffuseForgiveness){
        String afterlifeResult = "Inconsciente colectivo";
        if(diffuseFear.equals("Alto") && (diffuseRegret.equals("Mucho") || diffuseReject.equals("Medio"))){
            afterlifeResult = "Gnosis";
        }
        if(diffuseFear.equals("Regular") && diffuseReject.equals("Alto") && diffuseRevenge.equals("Mucho") && diffuseHate.equals("Mucho")){
            afterlifeResult = "Gnosis";
        }
        if(diffuseFear.equals("Muy alto") && (diffuseReject.equals("Medio") || diffuseFear.equals("Alto"))){
            afterlifeResult = "Gnosis";
        }
        if(diffuseFear.equals("Muy alto") && diffuseHate.equals("Mucho") && diffuseRegret.equals("Mucho") && diffuseRevenge.equals("Mucho")){
            afterlifeResult = "Gnosis";
        }
        if(diffuseReject.equals("Alto") && (diffuseFear.equals("Alto") || diffuseFear.equals("Muy alto"))){
            afterlifeResult = "Gnosis";
        }
        if(diffuseReject.equals("Alto") && (diffuseHate.equals("Mucho") && diffuseRevenge.equals("Mucho"))){
            afterlifeResult = "Gnosis";
        }
        if(diffuseWill.equals("Fuerte")){
            if(diffuseFear.equals("Muy alto") && diffuseHate.equals("Mucho")){
                afterlifeResult = "Testamento";
            }
            else if(diffuseRegret.equals("Mucho") && diffuseHate.equals("Mucho") && diffuseReject.equals("Alto")){
                afterlifeResult = "Testamento";
            }
            else if(((diffuseFear.equals("Alto")) || diffuseFear.equals("Muy alto")) && diffuseRegret.equals("Mucho")){
                afterlifeResult = "Testamento";
            }
            else if(diffuseFear.equals("Muy alto") && diffuseRegret.equals("Alto") && diffuseReject.equals("Alto")){
                afterlifeResult = "Testamento";
            }
        }
        if(diffuseForgiveness.equals("Perdonable")){
            afterlifeResult = "Inconsciente colectivo";
        }
        return afterlifeResult;
    }

}
