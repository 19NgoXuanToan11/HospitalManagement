package menu;

import java.util.ArrayList;

import util.Validation;

public class Menu {
    private String title;
    private ArrayList<String> optionList = new ArrayList();

    public Menu(String title) {
        this.title = title;
    }

    public void addNewOption(String newOption) {
        optionList.add(newOption);
    }

    public void printMenu() {
        System.out.println("\nWelcome to " + title);
        for (int i = 0; i < optionList.size(); i++) {
            System.out.println(optionList.get(i));
        }
    }

    public int getChoice() {
        int maxOption = optionList.size();
        String inputMsg = "Choose [1..." + maxOption + "]: ";
        String errorMsg = "You are required to choose the option 1..." + maxOption;
        return Validation.getAnInteger(inputMsg, errorMsg, 1, maxOption);
    }
}
