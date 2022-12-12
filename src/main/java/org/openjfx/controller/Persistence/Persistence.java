package org.openjfx.controller.Persistence;

import org.openjfx.view.ColorTheme;
import org.openjfx.view.FontSize;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistence {
    private static final String DEFAULT_CITIES_LOCATION = System.getenv("APPDATA") + File.separator + "rememberedCities.ser";
    private static final int PERSISTENCE_COLOR_INDEX = 0;
    private static final int PERSISTENCE_FONT_SIZE_INDEX = 1;
    private static final int PERSISTENCE_FIRST_CITY_INDEX = 2;
    private static final int PERSISTENCE_SECOND_CITY_INDEX = 3;

    public static PersistenceState getPersistenceState(){
        List<String> persistenceList = loadFromPersistence();

        PersistenceState persistenceState = new PersistenceState();

        persistenceState.setColorTheme(ColorTheme.valueOf(persistenceList.get(PERSISTENCE_COLOR_INDEX)));
        persistenceState.setFontSize(FontSize.valueOf(persistenceList.get(PERSISTENCE_FONT_SIZE_INDEX)));

        if (persistenceList.size() != 0){
            if (numberOfCitiesSavedToPersistence(persistenceList) == 1){
                persistenceState.setLeftCity(persistenceList.get(PERSISTENCE_FIRST_CITY_INDEX));
                persistenceState.setNumberOfCitiesSavedToPersistence(1);
            } else if (numberOfCitiesSavedToPersistence(persistenceList) == 2){
                persistenceState.setLeftCity(persistenceList.get(PERSISTENCE_FIRST_CITY_INDEX));
                persistenceState.setRightCity(persistenceList.get(PERSISTENCE_SECOND_CITY_INDEX));
                persistenceState.setNumberOfCitiesSavedToPersistence(2);
            }
        }
        return persistenceState;
    }

    private static List<String> loadFromPersistence(){

        List<String> persistenceList = new ArrayList<>();

        try {
            File file = new File(DEFAULT_CITIES_LOCATION);
            if (file.exists()){
                FileInputStream fileInputStream = new FileInputStream(DEFAULT_CITIES_LOCATION);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                List<String> persistedList = (List<String>) objectInputStream.readObject();
                persistenceList.addAll(persistedList);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return persistenceList;
    }

    private static int numberOfCitiesSavedToPersistence(List<String> persistenceList){
        if (persistenceList.size() == 3){
            return 1;
        } else if (persistenceList.size() == 4){
            return 2;
        }
        return  0;
    }

    public static void saveToPersistence(List<String> citiesToBeSaved){
        try{
            File file = new File(DEFAULT_CITIES_LOCATION);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(citiesToBeSaved);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
