package se.magnus.api.core.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductService {

  /**
   * Sample usage: "curl $HOST:$PORT/product/1".
   *
   * @param productId Id of the product
   * @return the product, if found, else null
   */
  @Operation(
          summary =
                  "${api.product-composite.get-composite-product.description}",
          description =
                  "${api.product-composite.get-composite-product.notes}")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description =
                  "${api.responseCodes.ok.description}"),
          @ApiResponse(responseCode = "400", description =
                  "${api.responseCodes.badRequest.description}"),
          @ApiResponse(responseCode = "404", description =
                  "${api.responseCodes.notFound.description}"),
          @ApiResponse(responseCode = "422", description =
                  "${api.responseCodes.unprocessableEntity.description}")
  })
  @GetMapping(
    value = "/product/{productId}",
    produces = "application/json")
  Product getProduct(@PathVariable int productId);
}
