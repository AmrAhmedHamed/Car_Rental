package com.example.project905.Config;

import com.example.project905.Controller.Vm.EcxepionMasseg;
import com.example.project905.Service.MassegeHandler.bandlMassegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExcptionConfg {
    private bandlMassegeService massegeService;
@Autowired
public ExcptionConfg(bandlMassegeService massegeService){
    this.massegeService=massegeService;
}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EcxepionMasseg> handelExcepion(Exception exception){
        EcxepionMasseg error = new EcxepionMasseg(
                massegeService.getMassegeEn(exception.getMessage()),
                massegeService.getMassegeAr(exception.getMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }



    @ExceptionHandler(CustomException.class)
    public ResponseEntity<EcxepionMasseg> handleCustomException(CustomException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new EcxepionMasseg(ex.getMessageEn(), ex.getMessageAr()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<EcxepionMasseg>> handleValidationException(MethodArgumentNotValidException exception) {
        List<EcxepionMasseg> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String key = fieldError.getDefaultMessage();
                    return new EcxepionMasseg(
                            massegeService.getMassegeEn(key),
                            massegeService.getMassegeAr(key)
                    );
                })
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }



}
