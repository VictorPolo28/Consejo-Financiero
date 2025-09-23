package com.example.Consejo_financiero.services.impl;

import com.example.Consejo_financiero.dto.CuentaRequest;
import com.example.Consejo_financiero.dto.CuentaResponse;
import com.example.Consejo_financiero.entity.Cuenta;
import com.example.Consejo_financiero.entity.Users;
import com.example.Consejo_financiero.exception.ResourceNotFoundException;
import com.example.Consejo_financiero.repository.ICuentaRepository;
import com.example.Consejo_financiero.repository.IUserRepository;
import com.example.Consejo_financiero.services.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements ICuentaService {

    @Autowired
    private ICuentaRepository cuentaRepository;

    @Autowired
    private IUserRepository userRepository;

    private CuentaResponse mapToResponse(Cuenta c){
        CuentaResponse r = new CuentaResponse();
        r.setCuentaId(c.getCuentaId());
        r.setUsuarioId(c.getUsuario().getUserId());
        r.setTipoCuenta(c.getTipoCuenta().name());
        r.setSaldoInicial(c.getSaldoInicial());
        r.setSaldoActual(c.getSaldoActual());
        r.setMoneda(c.getMoneda().name());
        return r;
    }

    @Override
    @Transactional
    public CuentaResponse createCuenta(CuentaRequest request) {
        Users u = userRepository.findById(request.getUsuarioId())
                 .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id_user", request.getUsuarioId()));

        Cuenta c = new Cuenta();
        c.setUsuario(u);
        c.setTipoCuenta(Enum.valueOf(com.example.Consejo_financiero.entity.TipoCuenta.class, request.getTipoCuenta()));
        c.setSaldoInicial(request.getSaldoInicial());
        c.setSaldoActual(request.getSaldoActual());
        c.setMoneda(Enum.valueOf(com.example.Consejo_financiero.entity.Moneda.class, request.getMoneda()));

        Cuenta saved = cuentaRepository.save(c);
        return mapToResponse(saved);
    }

    @Override
    public CuentaResponse getCuentaById(Long id) {
        Cuenta c = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta", "id", id));
        return mapToResponse(c);
    }

    @Override
    public List<CuentaResponse> getAllCuentas() {
        return cuentaRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CuentaResponse> getCuentasByUsuarioId(Long usuarioId) {
        return cuentaRepository.findByUsuarioId(usuarioId).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CuentaResponse updateCuenta(Long id, CuentaRequest request) {
        Cuenta c = cuentaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cuenta", "id", id));

        if (!c.getUsuario().getUserId().equals(request.getUsuarioId())) {
            Users u = userRepository.findById(request.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id_user", request.getUsuarioId()));
            c.setUsuario(u);
        }
        c.setTipoCuenta(Enum.valueOf(com.example.Consejo_financiero.entity.TipoCuenta.class, request.getTipoCuenta()));
        c.setSaldoInicial(request.getSaldoInicial());
        c.setSaldoActual(request.getSaldoActual());
        c.setMoneda(Enum.valueOf(com.example.Consejo_financiero.entity.Moneda.class, request.getMoneda()));
        Cuenta updated = cuentaRepository.save(c);
        return mapToResponse(updated);
    }

    @Override
    public void deleteCuenta(Long id) {
        Cuenta c = cuentaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cuenta", "id", id));
        cuentaRepository.delete(c);
    }
}
