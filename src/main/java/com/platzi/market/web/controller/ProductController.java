package com.platzi.market.web.controller;

import com.platzi.market.domain.dto.Product;
import com.platzi.market.domain.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("Get all Super Market Products")
    @ApiResponse(code=200, message = "OK")
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @ApiOperation("Get a Super Market Product by ID")
    @ApiResponses({
            @ApiResponse(code=200, message = "OK"),
            @ApiResponse(code=404, message = "NOT FOUND")
    })
    @GetMapping("/id/{productId}")
    public ResponseEntity<Product>
    getProduct(@ApiParam(value = "id of product", required = true, example = "7") @PathVariable("productId") int productId) {
        return productService.getProduct(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation("Get the Super Market Products by Category")
    @ApiResponses({
            @ApiResponse(code=200, message = "OK"),
            @ApiResponse(code=404, message = "NOT FOUND")
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>>
    getByCategory(@ApiParam(value = "id of category", required = true, example = "3") @PathVariable("categoryId") int categoryId) {
        return productService.getByCategory(categoryId)
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation("Save a new Super Market Product")
    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @ApiOperation("Delete a Super Market Product by ID")
    @ApiResponses({
            @ApiResponse(code=200, message = "OK"),
            @ApiResponse(code=404, message = "NOT FOUND")
    })
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity
    delete(@ApiParam(value = "id of product", required = true, example = "7") @PathVariable("productId") int productId) {
        if(productService.delete(productId))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
