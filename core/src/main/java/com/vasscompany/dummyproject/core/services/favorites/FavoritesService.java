package com.vasscompany.dummyproject.core.services.favorites;

import com.vasscompany.dummyproject.core.models.favorites.payloads.FavoritesRespModel;
import org.apache.sling.api.SlingHttpServletRequest;

public interface FavoritesService {

    FavoritesRespModel addFavorite(SlingHttpServletRequest request);
}
