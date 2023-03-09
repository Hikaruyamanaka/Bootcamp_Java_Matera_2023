package com.matera.bootcamp2023.controller;

import com.matera.bootcamp2023.domain.Conta;
import com.matera.bootcamp2023.dto.ContaDto;
import com.matera.bootcamp2023.dto.ContaRequestDto;
import com.matera.bootcamp2023.repository.ContaRepository;
import com.matera.bootcamp2023.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "v1/contas")
public class ContaController {

    private final ContaRepository contaRepository;
    private final ContaService contaService;

    @PostMapping
    public ContaDto criarConta(@RequestBody ContaRequestDto requestDto) throws InterruptedException {
        Conta conta = contaService.criarConta(requestDto);
        conta.setNumero(6543);
        return conta.toContaDto();
    }

    @GetMapping()
    public List<Conta> procuraContas() {
        return contaService.procuraContas();
    }

//    @GetMapping("/{id}")
////    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<ContaDto> procuraContaPorId(@PathVariable Long id) {
//
//        try {
//            Optional<Conta> contaOptional = contaService.procuraConta(id);
//            Conta conta = contaOptional.get();
//            //return ResponseEntity.status(HttpStatus.OK).body(conta.toContaDto());
//            //return ResponseEntity.ok().body(conta.toContaDto());
//            return ResponseEntity.ok(conta.toContaDto());
//        } catch (ContaInexistenteException exception) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDto> procuraContaPorIdSemTry(@PathVariable Long id) {
        Conta conta = contaService.procuraConta(id);
        return ResponseEntity.ok(conta.toContaDto());
    }

    @PostMapping("/{idConta}/credito/{valor}")
    public ResponseEntity<ContaDto> creditarConta(@PathVariable Long idConta, @PathVariable BigDecimal valor) {
        Conta conta = contaService.creditarConta(idConta, valor);
        return ResponseEntity.ok(conta.toContaDto());
    }

    @PostMapping("/{idConta}/debito/{valor}")
    public ResponseEntity<ContaDto> debitaConta(@PathVariable Long idConta, @PathVariable BigDecimal valor) {
        Conta conta = contaService.debitaConta(idConta, valor);
        return ResponseEntity.ok(conta.toContaDto());
    }


    @PostMapping("/{idContaDebitada}/{idContaCreditada}/{valor}")
    public ResponseEntity debitaConta(@PathVariable Long idContaDebitada,
                                      @PathVariable Long idContaCreditada,
                                      @PathVariable BigDecimal valor) {
        contaService.transferencia(idContaDebitada, idContaCreditada, valor);
        return ResponseEntity.ok("Transferencia realizada com sucesso");
    }
}