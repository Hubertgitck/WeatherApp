package org.openjfx.model.Persistence;

import org.openjfx.view.ColorTheme;
import org.openjfx.view.FontSize;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistence {
    private final String DEFAULT_PERSISTENCE_LOCATION = System.getProperty("user.home") + File.separator + "WeatherAppPersistence.txt";
    private final int PERSISTENCE_COLOR_INDEX = 0;
    private final int PERSISTENCE_FONT_SIZE_INDEX = 1;
    private final int PERSISTENCE_FIRST_CITY_INDEX = 2;
    private final int PERSISTENCE_SECOND_CITY_INDEX = 3;

    public PersistenceState getPersistenceState(){

        List<String> persistenceList = loadFromPersistence();
        PersistenceState persistenceState = new PersistenceState();

        if (persistenceList.size() != 0){

            persistenceState.setColorTheme(ColorTheme.valueOf(persistenceList.get(PERSISTENCE_COLOR_INDEX)));
            persistenceState.setFontSize(FontSize.valueOf(persistenceList.get(PERSISTENCE_FONT_SIZE_INDEX)));

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

    private List<String> loadFromPersistence(){

        List<String> persistenceList = new ArrayList<>();

        File file = new File(DEFAULT_PERSISTENCE_LOCATION);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(DEFAULT_PERSISTENCE_LOCATION);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                List<String> persistedList = (List<String>) objectInputStream.readObject();
                persistenceList.addAll(persistedList);
                fileInputStream.close();
                objectInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return persistenceList;
    }

    private int numberOfCitiesSavedToPersistence(List<String> persistenceList){
        if (persistenceList.size() == 3){
            return 1;
        } else if (persistenceList.size() == 4){
            return 2;
        }
        return  0;
    }

    public void saveToPersistence(List<String> citiesToBeSaved){
        try{
            File file = new File(DEFAULT_PERSISTENCE_LOCATION);
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
