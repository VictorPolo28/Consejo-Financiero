package com.example.Consejo_financiero.services;

import com.example.Consejo_financiero.dto.CuentaRequest;
import com.example.Consejo_financiero.dto.CuentaResponse;

import java.util.List;

public interface ICuentaService {
    CuentaResponse createCuenta(CuentaRequest request);
    CuentaResponse getCuentaById(Long id);
    List<CuentaResponse> getAllCuentas();
    List<CuentaResponse> getCuentasByUsuarioId(Long usuarioId);
    CuentaResponse updateCuenta(Long id, CuentaRequest request);
    void deleteCuenta(Long id);
}
