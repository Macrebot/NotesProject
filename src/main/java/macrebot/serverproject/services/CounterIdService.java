package macrebot.serverproject.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;;

@Service
public class CounterIdService {

    private final RestTemplate restTemplate;
    private final String idCounterServiceUrl;

    public CounterIdService(RestTemplate restTemplate, @Value("${id.counter.service.url}") String idCounterServiceUrl) {
        this.restTemplate = restTemplate;
        this.idCounterServiceUrl = idCounterServiceUrl;
    }

    public Long generateId(Long counterId) {
        try {
            String url = idCounterServiceUrl + counterId + "/nextId";
            return restTemplate.getForObject(url, Long.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error generating id: " + e.getMessage(), e);
        }

    }

}
