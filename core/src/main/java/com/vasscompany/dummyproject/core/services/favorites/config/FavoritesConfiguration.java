package com.vasscompany.dummyproject.core.services.favorites.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * OSGi configuration for the Favorites integration client.
 *
 * <p>This configuration is consumed by {@code FavoritesServiceImpl} to build
 * the HTTP client used for remote Favorites API calls.</p>
 */
@ObjectClassDefinition(name = "ICEX - Favorites Configuration", description = "Configure the Favorites Details")
public @interface FavoritesConfiguration {

    /**
     * Socket timeout in milliseconds.
     *
     * <p>Maximum idle wait time for response data once the connection is open.</p>
     */
    @AttributeDefinition(name = "favorites_socket_timeout", description = "Socket timeout for waiting for data", type = AttributeType.INTEGER)
    int favorites_socket_timeout() default 70000;

    /**
     * Connection timeout in milliseconds.
     *
     * <p>Maximum time allowed to establish the TCP connection.</p>
     */
    @AttributeDefinition(name = "favorites_connection_timeout", description = "Timeout until a connection is established", type = AttributeType.INTEGER)
    int favorites_connection_timeout() default 10000;

    /**
     * Value used in outbound {@code User-Agent} header.
     *
     * <p>A specific value helps avoid being classified as a generic bot by
     * some edge protections.</p>
     */
    @AttributeDefinition(
            name = "User-Agent header",
            description = "User-Agent sent to vass-favorites-service (avoid BotManager hits with Apache-HttpClient)",
            type = AttributeType.STRING
    )
    String user_agent() default "vass-aem-favorites/1.0 (app=vass-catalog; client=AEM)";

    /**
     * Value used in outbound {@code Accept} header.
     *
     * <p>Sending this explicitly avoids WAF rules that reject requests without
     * an Accept header.</p>
     */
    @AttributeDefinition(
            name = "Accept header",
            description = "Accept header sent to appportalservices (avoid CRS 920300 Missing Accept Header)",
            type = AttributeType.STRING
    )
    String accept_header() default "application/json, application/*+json;q=0.9, */*;q=0.8";
}
