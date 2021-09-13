package com.digitalware.comercios.controller;

import com.digitalware.comercios.dto.PedidoLineaDTO;
import com.digitalware.comercios.dto.ProductoDTO;
import com.digitalware.comercios.model.PedidoLinea;
import com.digitalware.comercios.model.Producto;
import com.digitalware.comercios.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    ProductoRepository productoRepository;

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        try {
            List<ProductoDTO> productos = new ArrayList<ProductoDTO>();


            productoRepository.findAll().forEach((producto -> {productos.add(new ProductoDTO(producto));}));


            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable("id") long id) {
        Optional<Producto> productoData = productoRepository.findById(id);

        if (productoData.isPresent()) {
            return new ResponseEntity<>(new ProductoDTO(productoData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/productos")
    public ResponseEntity<?> createProducto(@RequestBody Producto producto) {
        try {

            Producto _producto = productoRepository.save(new Producto(producto.getReferencia(), producto.getDescripcion()));
            return new ResponseEntity<>(new ProductoDTO(_producto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<?> writeProducto(@PathVariable("id") long id, @RequestBody Producto producto) {
        try {
            Producto _producto = productoRepository.getById(id);
            _producto.setReferencia(producto.getReferencia());
            _producto.setDescripcion(producto.getDescripcion());
            _producto = productoRepository
                    .save(_producto);
            return new ResponseEntity<>(new ProductoDTO(_producto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable("id") Long id) {
        try{
            Optional<Producto> producto = productoRepository.findById(id);

            if (producto.isPresent()) {
                productoRepository.deleteById(id);
                return new ResponseEntity<>("{\"mensaje\":\"Registro eliminado\"}", HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}