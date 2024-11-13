package eu.ase.ro.tema_2_android.services;

import eu.ase.ro.tema_2_android.R;
import eu.ase.ro.tema_2_android.domain.Category;

public class SurvivalGuideService {
    public int getImageForTitle(String title) {
        title = title.toLowerCase();

        if (title.contains("prim ajutor")) {
            return R.drawable.prim_ajutor_image;
        } else if (title.contains("inundatie")) {
            return R.drawable.inundatie_image;
        } else if (title.contains("cutremur")) {
            return R.drawable.cutremur_image;
        } else if (title.contains("incendiu")) {
            return R.drawable.incendiu_image;
        } else {
            return R.drawable.default_image;
        }
    }

    public Category getCategory(String title) {
        title = title.toLowerCase();

        if (title.contains("prim ajutor")) {

            return Category.FIRST_AID;
        } else if (title.contains("cutremur") || title.contains("incendiu") || title.contains("inundatie")) {
            return Category.DISASTERS;
        } else {
            return Category.DISASTERS;
        }
    }
}

