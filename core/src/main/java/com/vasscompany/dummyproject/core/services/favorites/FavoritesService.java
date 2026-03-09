package com.vasscompany.dummyproject.core.services.favorites;

import com.vasscompany.dummyproject.core.models.favorites.payloads.FavoritesRespModel;
import org.apache.sling.api.SlingHttpServletRequest;

/**
 * Service contract for Favorites operations.
 *
 * <p>Implementations read the required data from the incoming Sling request,
 * normalize/validate it, and delegate persistence to the configured backend.
 * This interface is intentionally request-oriented because callers are servlet
 * components in AEM.</p>
 */
public interface FavoritesService {

    /**
     * Creates a favorite entry from request parameters.
     *
     * <p>Expected request parameters are typically: {@code id}, {@code url},
     * {@code title}, {@code type}, {@code site}, and {@code user}.</p>
     *
     * @param request current Sling request containing favorite data.
     * @return response payload with operation result (or error metadata depending
     * on implementation strategy).
     */
    FavoritesRespModel addFavorite(SlingHttpServletRequest request);
}
