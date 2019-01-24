package com.part.project.projectsettingspart;

public class CategoryModel {

    String word, translate, language, cardTheme;

    public CategoryModel(){

    }

    public CategoryModel(String word, String translate, String language, String cardTheme) {
        this.word = word;
        this.translate = translate;
        this.language = language;
        this.cardTheme = cardTheme;
    }
}