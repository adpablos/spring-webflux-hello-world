package com.alexdepablos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Controller {

    @GetMapping("/test")
    public Mono<String> test() {
        return ReactiveRequestContextHolder.getRequest()
                .map(request -> {
                    String headerValue = request.getHeaders().getFirst("Hello");
                    return headerValue != null ? headerValue : "Cabecera 'Hello' no encontrada";
                });
    }

    /**
     * En programación reactiva, especialmente con el uso de Mono o Flux en Spring WebFlux, las operaciones son
     * no bloqueantes y asincrónicas. Esto significa que, a diferencia de la programación imperativa, no puedes
     * simplemente asignar el resultado de una operación a una variable y luego usar esa variable en la línea
     * siguiente como lo harías normalmente, porque el valor aún no estará disponible en el momento en que se
     * asigne la variable.
     *
     * Sin embargo, lo que puedes hacer es encadenar operaciones de manera que el resultado de una operación se
     * pase a la siguiente. Para el escenario que describes, se puede lograr un patrón similar al uso de variables
     * a través de la manipulación del flujo de datos con operadores reactivos.
     */
    @GetMapping("/test2")
    public Mono<String> test2() {
        Mono<String> headerValueMono = ReactiveRequestContextHolder.getRequest()
                .map(request -> request.getHeaders().getFirst("Hello"))
                .defaultIfEmpty("Cabecera 'Hello' no encontrada");

        // Aquí podrías hacer más operaciones con headerValueMono si fuera necesario
        // Por ejemplo, transformarlo, aplicar lógica condicional, etc.

        // Finalmente, devolvemos el Mono que contiene el valor de la cabecera o el valor por defecto
        return headerValueMono;
    }

}
