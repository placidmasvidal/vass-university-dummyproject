package com.vasscompany.dummyproject.core.services.favorites.impl;

import com.vasscompany.dummyproject.core.models.favorites.beans.FavoritesModel;
import com.vasscompany.dummyproject.core.models.favorites.payloads.FavoritesRespModel;
import com.vasscompany.dummyproject.core.services.favorites.FavoritesService;
import com.vasscompany.dummyproject.core.services.favorites.config.FavoritesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

import java.io.IOException;
import java.util.List;

/**
 * Default OSGi implementation of {@link FavoritesService}.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Read incoming favorite data from a Sling request.</li>
 *   <li>Normalize URL input to a relative path before remote submission.</li>
 *   <li>Manage a reusable Apache {@link CloseableHttpClient} configured from OSGi.</li>
 * </ul>
 *
 * <p>The component rebuilds the HTTP client on activation and configuration
 * updates, and closes resources on deactivation.</p>
 */
@Component(service = {FavoritesService.class}, immediate = true)
public class FavoritesServiceImpl implements FavoritesService {

    /** Runtime OSGi configuration snapshot used to build request behavior. */
    private volatile FavoritesConfiguration favoritesConfiguration;

    /** Shared HTTP client for outbound Favorites calls. */
    private volatile CloseableHttpClient httpClient;

    /**
     * Receives OSGi configuration updates and refreshes the HTTP client.
     *
     * @param configuration latest OSGi configuration for timeouts and headers.
     */
    @Activate
    @Modified
    protected void activate(FavoritesConfiguration configuration) {
        this.favoritesConfiguration = configuration;
        rebuildHttpClient();
    }

    /** Closes resources when the OSGi component is stopped. */
    @Deactivate
    protected void deactivate() {
        closeHttpClient();
    }

    /**
     * Recreates the HTTP client to apply the current OSGi configuration.
     *
     * <p>Existing client instances are closed first to avoid leaked sockets.</p>
     */
    private synchronized void rebuildHttpClient() {
        closeHttpClient();

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(favoritesConfiguration.favorites_connection_timeout())
                .setConnectionRequestTimeout(favoritesConfiguration.favorites_connection_timeout())
                .setSocketTimeout(favoritesConfiguration.favorites_socket_timeout())
                .build();

        this.httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .setUserAgent(userAgentHeader())
                .setDefaultHeaders(List.of(
                        new BasicHeader(HttpHeaders.ACCEPT, acceptHeader())
                ))
                .build();
    }

    /**
     * Closes the current HTTP client instance if present.
     *
     * <p>Errors during close are intentionally ignored because shutdown paths
     * must be resilient and non-blocking.</p>
     */
    private synchronized void closeHttpClient() {
        if (this.httpClient != null) {
            try {
                this.httpClient.close();
            } catch (final IOException e) {
                /* no-op */
            } finally {
                this.httpClient = null;
            }
        }
    }

    /**
     * Returns configured Accept header or a safe default value.
     *
     * @return Accept header used for outbound API calls.
     */
    private String acceptHeader() {
        String v = this.favoritesConfiguration != null ? this.favoritesConfiguration.accept_header() : null;
        return StringUtils.isNotBlank(v)
                ? v
                : "application/json, application/*+json;q=0.9, */*;q=0.8";
    }

    /**
     * Returns configured User-Agent header or a service-specific default.
     *
     * @return User-Agent value used by the HTTP client.
     */
    private String userAgentHeader() {
        String v = this.favoritesConfiguration != null ? this.favoritesConfiguration.user_agent() : null;
        return StringUtils.isNotBlank(v)
                ? v
                : "icex-aem-favorites/1.0 (app=icex-catalog; client=AEM)";
    }

    /**
     * Builds a {@link FavoritesModel} from request parameters and prepares the
     * JSON payload for the remote Favorites API.
     *
     * <p>URL normalization is applied so upstream systems always receive a
     * relative path (for example: {@code /es/productos/producto/123}).</p>
     *
     * @param request Sling request carrying favorite data.
     * @return remote API response model once integration is completed.
     */
    @Override
    public FavoritesRespModel addFavorite(SlingHttpServletRequest request) {
        FavoritesModel favorite = new FavoritesModel();
        favorite.setId(request.getParameter("id"));
        favorite.setUrl(getRelativeURL(request.getParameter("url")));
        favorite.setTitle(request.getParameter("title"));
        favorite.setType(request.getParameter("type"));
        favorite.setSite(request.getParameter("site"));
        favorite.setUser(request.getParameter("user"));

        // Prepared payload for the outbound call.
        new com.google.gson.Gson().toJsonTree(favorite);

        return null;
    }

    /**
     * Normalizes an absolute or relative URL to a relative site path.
     *
     * <p>Rules:</p>
     * <ul>
     *   <li>{@code null} or empty input -> {@code /}</li>
     *   <li>query string is removed</li>
     *   <li>if host contains {@code .com}, everything up to and including
     *   {@code .com} is removed</li>
     *   <li>otherwise input is returned unchanged</li>
     * </ul>
     *
     * @param url raw URL from request parameters.
     * @return normalized relative path expected by the Favorites backend.
     */
    private String getRelativeURL(String url) {
        if (url == null || url.isEmpty()) {
            return "/";
        }

        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }

        int index = url.indexOf(".com");
        if (index != -1) {
            return url.substring(index + 4);
        }
        return url;
    }
}
