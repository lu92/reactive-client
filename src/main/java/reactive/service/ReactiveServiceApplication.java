package reactive.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactive.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class ReactiveServiceApplication {

    @GetMapping("/event/{id}")
    Mono<Event> eventById(@PathVariable long id) {
        return Mono.just(new Event(id, LocalDateTime.now()));
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/events")
    Flux<Event> events() {
      Flux<Event> eventFlux = Flux.fromStream(Stream.generate(() -> new Event(System.currentTimeMillis(), LocalDateTime.now())));
      Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
      return Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1);
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveServiceApplication.class, args);
    }
}
