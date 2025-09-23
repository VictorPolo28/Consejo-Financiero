package com.example.Consejo_financiero.controller;

import com.example.Consejo_financiero.dto.CuentaRequest;
import com.example.Consejo_financiero.dto.CuentaResponse;
import com.example.Consejo_financiero.services.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentasController {

    @Autowired
    private ICuentaService cuentaService;

    @PostMapping
    public ResponseEntity<CuentaResponse> createCuenta(@RequestBody CuentaRequest req) {
        return ResponseEntity.ok(cuentaService.createCuenta(req));
    }

    @GetMapping
    public ResponseEntity<List<CuentaResponse>> getAll() {
        return ResponseEntity.ok(cuentaService.getAllCuentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.getCuentaById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CuentaResponse>> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(cuentaService.getCuentasByUsuarioId(usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponse> update(@PathVariable Long id, @RequestBody CuentaRequest req) {
        return ResponseEntity.ok(cuentaService.updateCuenta(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cuentaService.deleteCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
