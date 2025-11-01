package com.corneille.product.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class SortField {
    /***
     *
     * @param allowedFields : un objet clé-valeur pour récupérer les champs à trier
     * @param defaultField : Le champ sur lequel le trie va se porter par défaut
     * @return String : renvoie le champ sur lequel portera le trie
     */
    public static String chooseFieldToSort(Map<String, String> allowedFields, String defaultField) {
        return allowedFields.getOrDefault(defaultField.toLowerCase(), defaultField);
    }

    //Renvoie la direction du trie : soit asc ou desc
    public static Sort.Direction directionOfSort(String direction) {
        if (!Set.of("asc", "desc").contains(direction.toLowerCase())) {
            direction = "asc"; // Valeur par défaut
        }

        return Sort.Direction.fromString(direction);
    }
}
