package com.digitalware.comercios.controller;

import com.digitalware.comercios.dto.PedidoLineaDTO;
import com.digitalware.comercios.model.PedidoLinea;
import com.digitalware.comercios.repository.PedidoLineaRepository;
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
public class PedidoLineaController {

    @Autowired
    PedidoLineaRepository pedidoLineaRepository;
    
    @GetMapping("/pedidoLineas")
    public ResponseEntity<?> getAllPedidoLineas() {
        try {
            List<PedidoLineaDTO> pedidoLineas = new ArrayList<>();


            pedidoLineaRepository.findAll().forEach((pedidoLinea -> {pedidoLineas.add(new PedidoLineaDTO(pedidoLinea));}));


            if (pedidoLineas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(pedidoLineas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pedidoLineas/{id}")
    public ResponseEntity<PedidoLineaDTO> getPedidoLineaById(@PathVariable("id") long id) {
        Optional<PedidoLinea> pedidoLineaData = pedidoLineaRepository.findById(id);

        return pedidoLineaData.map(pedidoLinea -> new ResponseEntity<>(new PedidoLineaDTO(pedidoLinea), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/pedidoLineas")
    public ResponseEntity<?> createPedidoLinea(@RequestBody PedidoLinea pedidoLinea) {
        try {
            PedidoLinea _pedidoLinea = new PedidoLinea(pedidoLinea.getPedido(), pedidoLinea.getProducto(), pedidoLinea.getCantidad());
            if (!_pedidoLinea.validarCantidad()){
                return new ResponseEntity<>("Valide la cantidad de la linea de pedido", HttpStatus.PRECONDITION_FAILED);
            }
            _pedidoLinea = pedidoLineaRepository.save(_pedidoLinea);
            return new ResponseEntity<>(new PedidoLineaDTO(_pedidoLinea), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/pedidoLineas/{id}")
    public ResponseEntity<?> writePedidoLinea(@PathVariable("id") long id, @RequestBody PedidoLinea pedidoLinea) {
        try {
            PedidoLinea _pedidoLinea = pedidoLineaRepository.getById(id);
            _pedidoLinea.setCantidad(pedidoLinea.getCantidad());
            _pedidoLinea.setPedido(pedidoLinea.getPedido());
            _pedidoLinea.setProducto(pedidoLinea.getProducto());

            if (!_pedidoLinea.validarCantidad()){
                return new ResponseEntity<>("Valide la cantidad de la linea de pedido", HttpStatus.PRECONDITION_FAILED);
            }
            _pedidoLinea = pedidoLineaRepository
                    .save(_pedidoLinea);
            return new ResponseEntity<>(new PedidoLineaDTO(_pedidoLinea), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/pedidoLineas/{id}")
    public ResponseEntity<String> deletePedidoLinea(@PathVariable("id") Long id) {
        try{
            Optional<PedidoLinea> pedidoLinea = pedidoLineaRepository.findById(id);

            if (pedidoLinea.isPresent()) {
                pedidoLineaRepository.deleteById(id);
                return new ResponseEntity<>("{\"mensaje\":\"Registro eliminado\"}", HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    

}