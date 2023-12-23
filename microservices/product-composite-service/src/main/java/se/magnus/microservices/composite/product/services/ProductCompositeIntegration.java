package se.magnus.microservices.composite.product.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import se.magnus.api.core.product.Product;
import se.magnus.api.core.product.ProductService;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.api.core.recommendation.RecommendationService;
import se.magnus.api.core.review.Review;
import se.magnus.api.core.review.ReviewService;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Component
public class ProductCompositeIntegration implements ProductService, RecommendationService, ReviewService {

    public ProductCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,
            @Value("${app.product-service.host}")
            String productServiceHost,

            @Value("${app.product-service.port}")
            int productServicePort,
            @Value("${app.recommendation-service.host}")
            String recommendationServiceHost,
            @Value("${app.recommendation-service.port}")
            int recommendationServicePort,
            @Value("${app.review-service.host}")
            String reviewServiceHost,
            @Value("${app.review-service.port}")
            int reviewServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        productServiceUrl = "http://" + productServiceHost + ":" +
                productServicePort + "/product/";

        recommendationServiceUrl = "http://" + recommendationServiceHost + ":"
                + recommendationServicePort + "/recommendation?productId=";

        reviewServiceUrl = "http://" + reviewServiceHost + ":"
                + reviewServicePort + "/review?productId=";

    }

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    @Override
    public Product getProduct(int productId) {
        String url = productServiceUrl + productId;
        return restTemplate.getForObject(url, Product.class);
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {
        String url = recommendationServiceUrl + productId;
        return restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<List<Recommendation>>() {}).getBody();
    }

    @Override
    public List<Review> getReviews(int productId) {
        String url = reviewServiceUrl + productId;
        return restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<List<Review>>() {}).getBody();
    }
}
