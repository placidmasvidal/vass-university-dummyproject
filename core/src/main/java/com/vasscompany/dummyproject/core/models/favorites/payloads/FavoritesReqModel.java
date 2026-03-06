package com.vasscompany.dummyproject.core.models.favorites.payloads;

import lombok.Data;

@Data
public class FavoritesReqModel {

    private String id;

    private String title;

    private String type;

    private String url;

    private String site;

    private String user;
}
