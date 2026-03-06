package com.vasscompany.dummyproject.core.services.favorites.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vasscompany.dummyproject.core.models.favorites.beans.FavoritesModel;
import com.vasscompany.dummyproject.core.models.favorites.payloads.FavoritesRespModel;
import com.vasscompany.dummyproject.core.services.favorites.FavoritesService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;

@Component(service = {FavoritesService.class}, immediate = true)
public class FavoritesServiceImpl implements FavoritesService {

    @Override
    public FavoritesRespModel addFavorite(SlingHttpServletRequest request) {
        // Crea una instancia de FavoritesModel con los datos del request
        // Comprueba antes que el valor del campo url no contenga el dominio, solo la parte relativa
        // Como ejemplos Few-Shot Learning, puedes usar los siguientes casos:
        // - Si el campo url es "https://www.example.com/es/productos/producto/123", entonces el valor que se debe usar para la URL es "/es/productos/producto/123"
        // - Si el campo url es "https://www.example.com/en/categories/category/456", entonces el valor que se debe usar para la URL es "/en/categories/category/456"
        // - Si el valors es https://www.example.com/ entonces el valor que se debe usar para la URL es "/"
        FavoritesModel favorite = new FavoritesModel();
        favorite.setId(request.getParameter("id"));
        favorite.setUrl(getRelativeURL(request.getParameter("url")));
        favorite.setTitle(request.getParameter("title"));
        favorite.setType(request.getParameter("type"));
        favorite.setSite(request.getParameter("site"));
        favorite.setUser(request.getParameter("user"));

        // Serializa a JSON y haz la llamada a la API externa para agregar el favorito
        JsonElement jsonFavorite = new com.google.gson.Gson().toJsonTree(favorite);
        JsonObject jsonResponse = jsonFavorite.getAsJsonObject();

        return null;
    }

    private String getRelativeURL(String url) {
        if (url == null || url.isEmpty()) {
            return "/";
        }
        // si la url contiene parámetros de consulta, elimínalos
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        // Extrae la parte relativa de la URL
        int index = url.indexOf(".com");
        if (index != -1) {
            return url.substring(index + 4); // +4 para omitir ".com"
        }
        return url; // Si no contiene el dominio, devuelve la URL tal cual
    }
}
