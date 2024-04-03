package com.github.ewoowe;

public class Main {

    public static void main(String[] args) {
        UniversalIdSystem uid = new UniversalIdSystem("/home/wangcheng/IdeaProjects/UniversalIdSystem/src/main/resources/uid.json");
        String nokia = uid.getUid(null, "NOKIA", null);
        String gnb = uid.getUid(nokia, "GNB", "7");
        String cucp = uid.getUid(gnb, "CUCP", "2");
        String cell = uid.getUid(cucp, "CELL", "12");
        System.out.println("pause");
    }
}
