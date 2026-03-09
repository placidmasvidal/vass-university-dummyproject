package com.vasscompany.dummyproject.core.models.favorites.payloads;

import com.vasscompany.dummyproject.core.models.favorites.beans.FavoritesModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FavoritesRespModel {
    private List<FavoritesModel> favorites = new ArrayList<>();

    private int statusCode;

    private String message;

    private String responseBody;
}
