package com.talvan.calculator.controller;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/calculadora")
public class CalculadoraController {

    @GetMapping("/soma/{a}/{b}")
    public String soma(@PathVariable int a, @PathVariable int b) {
        int resultado = a + b;
        return "Resultado: \n " + resultado;
    }

    @GetMapping("/subtrair")
    public String subtrair(@RequestParam int numero1, @RequestParam int numero2) {
        int resultado = numero1 - numero2;
        return "Resultado: \n " + resultado;
    }

    @GetMapping("calcular/{operacao}")
    public String calcular(@PathVariable String operacao, @RequestParam int numero1, @RequestParam int numero2,  @RequestParam(defaultValue = "2") int casasDecimais) {
        double resultado;
        switch (operacao.toLowerCase()) {
            case "soma" -> resultado = numero1 + numero2;
            case "subtrair" -> resultado = numero1 - numero2;
            case "multiplicar" -> resultado = numero1 * numero2;
            case "dividir" -> {
                if (numero2 == 0) {
                    return "Erro: Divisão por zero não é permitida.";
                }
                resultado = (double) numero1 / numero2;
            }
            default -> {
                return "Erro: Operação inválida. Use 'soma', 'subtrair', 'multiplicar' ou 'dividir'.";
            }
        }

        resultado = BigDecimal.valueOf(resultado)
                .setScale(casasDecimais, RoundingMode.HALF_UP)
                .doubleValue();

        String nomeOperacao = switch (operacao.toLowerCase()) {
            case "soma" -> "Soma";
            case "subtrair" -> "Subtração";
            case "multiplicar" -> "Multiplicação";
            case "dividir" -> "Divisão";
            default -> "";
        };

        return "Operação: " + nomeOperacao +
                "\nNúmero 1:\n " + numero1 +
                "\nNúmero 2:\n " + numero2 +
                "\nResultado:\n " + resultado;
    }

    @GetMapping("/par-ou-impar/{numero}")
    public String parOuImpar(@PathVariable int numero) {
        String resultado = (numero % 2 == 0) ? "par" : "ímpar";
        return "O número " + numero + " é " + resultado + ".";
    }

    @GetMapping("/analisar/{numero}")
    public String analisar(@PathVariable int numero) {
        String parOuImpar = (numero % 2 == 0) ? "PAR" : "ÍMPAR";
        String positivoOuNegativo = (numero > 0) ? "POSITIVO" : (numero < 0) ? "NEGATIVO" : "ZERO";
        double dobro = numero * 2;
        double metade = numero / 2.0;
        double quadrado = Math.pow(numero, 2);

        return "O número: " + numero +
                "\nPar ou impar:" + parOuImpar +
                "\nPositivo ou negativo:" + positivoOuNegativo + "." +
                "\nDobro: " + dobro +
                "\nMetade: " + metade +
                "\nQuadrado: " + quadrado;
    }

    @GetMapping("/media")
    public String media(@RequestParam double numero1, @RequestParam double numero2, @RequestParam double numero3) {
        double media = (numero1 + numero2 + numero3) / 3;
        String situacao = media >= 7 ? "Aprovado" : "Reprovado";
        return "Média: " + media +
                "\nSituação: " + situacao;
    }
}