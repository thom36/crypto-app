package fr.messina.api_gateway.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import fr.messina.api_gateway.util.JwtUtil;
import reactor.core.publisher.Mono;

@Component
public class JwtToHeaderFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    public JwtToHeaderFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Extraction du userId de manière réactive
            return jwtUtil.extractUserIdReactive(token)
                    .flatMap(userId -> {
                        // Ajoute le header X-User-Id à la requête
                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(builder -> builder.header("X-User-Id", userId))
                                .build();
                        return chain.filter(mutatedExchange);
                    });
        }

        // Si pas de token, continue normalement
        return chain.filter(exchange);
    }
}
