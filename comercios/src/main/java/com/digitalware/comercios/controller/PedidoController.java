package com.digitalware.comercios.controller;


import com.digitalware.comercios.dto.PedidoDTO;
import com.digitalware.comercios.model.Pedido;
import com.digitalware.comercios.model.PedidoLinea;
import com.digitalware.comercios.model.Producto;
import com.digitalware.comercios.repository.PedidoLineaRepository;
import com.digitalware.comercios.repository.PedidoRepository;
import com.digitalware.comercios.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PedidoController {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    PedidoLineaRepository pedidoLineaRepository;

    @GetMapping("/pedidos")
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        try {
            List<PedidoDTO> pedidos = new ArrayList<PedidoDTO>();


            pedidoRepository.findAll().forEach((pedido -> {pedidos.add(new PedidoDTO(pedido));}));


            if (pedidos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable("id") long id) {
        Optional<Pedido> pedidoData = pedidoRepository.findById(id);

        if (pedidoData.isPresent()) {
            return new ResponseEntity<>(new PedidoDTO(pedidoData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/pedidos")
    public ResponseEntity<?> createPedido(@RequestBody Pedido pedido) {
        try {
            Pedido _pedido = new Pedido(pedido.getCodigo(), pedido.getFechaPedido(), pedido.getLineasPedido());

            pedido.getLineasPedido().forEach((linea -> {linea.setProducto(productoRepository.findById(linea.getProducto().getId()).get());}));
            if (!_pedido.validarPedido()){
                return new ResponseEntity<>("El pedido no es valido", HttpStatus.PRECONDITION_FAILED);
            }
            List<PedidoLinea> lineasPedido = _pedido.getLineasPedido();
            _pedido.setLineasPedido(null);
            Pedido final_pedido = pedidoRepository.save(_pedido);

            List<PedidoLinea> _lineasPedido = new ArrayList<>();
            lineasPedido.forEach(linea -> {
                linea.setPedido(final_pedido);
                _lineasPedido.add(pedidoLineaRepository.save(linea));
            });

            final_pedido.setLineasPedido(_lineasPedido);

            return new ResponseEntity<PedidoDTO>(new PedidoDTO(final_pedido), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Ha ocurrido un error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/pedidos/{id}")
    public ResponseEntity<?> writePedido(@PathVariable("id") long id, @RequestBody Pedido pedido) {
        try {
            Pedido _pedido = pedidoRepository.getById(id);
            _pedido.setCodigo(pedido.getCodigo());
            _pedido.setFechaPedido(pedido.getFechaPedido());
            _pedido.setLineasPedido(pedido.getLineasPedido());

            _pedido.getLineasPedido().forEach((linea -> {linea.setProducto(productoRepository.findById(linea.getProducto().getId()).get());}));
            if (!_pedido.validarPedido()){
                return new ResponseEntity<>("El pedido no es valido", HttpStatus.PRECONDITION_FAILED);
            }
            _pedido = pedidoRepository
                    .save(_pedido);
            return new ResponseEntity<>(new PedidoDTO(_pedido), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable("id") Long id) {
        try{
            Optional<Pedido> pedido = pedidoRepository.findById(id);

            if (pedido.isPresent()) {

                pedidoRepository.deleteById(id);
                return new ResponseEntity<>("{\"mensaje\":\"Registro eliminado\"}", HttpStatus.OK);


            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}