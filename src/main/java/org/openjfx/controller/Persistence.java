package org.openjfx.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistence {
    private static final String LAST_CITIES_LOCATION = System.getenv("APPDATA") + File.separator + "rememberedCities.ser";

    public static List<String> loadFromPersistence(){
        List<String> persistenceList = new ArrayList<>();

        try {
            File file = new File(LAST_CITIES_LOCATION);
            if (file.exists()){
                FileInputStream fileInputStream = new FileInputStream(LAST_CITIES_LOCATION);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                List<String> persistedList = (List<String>) objectInputStream.readObject();
                persistenceList.addAll(persistedList);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return persistenceList;
    }

    public static void saveToPersistence(List<String> lastCities){
        try{
            File file = new File(LAST_CITIES_LOCATION);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(lastCities);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
