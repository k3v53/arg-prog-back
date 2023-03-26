
package com.kevinvicente.argprogback.argprogback.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevinvicente.argprogback.argprogback.dto.ExperienciaDto;
import com.kevinvicente.argprogback.argprogback.entity.Experiencia;
import com.kevinvicente.argprogback.argprogback.security.controller.Mensaje;
import com.kevinvicente.argprogback.argprogback.service.ExperienciaService;

@RestController
@RequestMapping("/experiencia")
@CrossOrigin(origins = "*")
public class ExperienciaController {
    @Autowired
    ExperienciaService experienciaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Experiencia>> list() {
        List<Experiencia> list = experienciaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Experiencia> getById(@PathVariable("id") int id) {
        if (!experienciaService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        }

        Experiencia experiencia = experienciaService.getOne(id).get();
        return new ResponseEntity(experiencia, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ExperienciaDto experienciaDto) {
        if (StringUtils.isBlank(experienciaDto.getTituloEx())) {
            return new ResponseEntity(new Mensaje("El titulo es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (experienciaService.existsByTituloEx(experienciaDto.getTituloEx())) {
            return new ResponseEntity(new Mensaje("Esa experiencia existe"), HttpStatus.BAD_REQUEST);
        }

        Experiencia experiencia = new Experiencia(experienciaDto.getTituloEx(), experienciaDto.getEmpleador(),
                experienciaDto.getFechaInicio(), experienciaDto.getFechaFin(), experienciaDto.getDescripcionEx());
        experienciaService.save(experiencia);

        return new ResponseEntity(new Mensaje("Experiencia agregada"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ExperienciaDto experienciaDto) {
        // Validamos si existe el ID
        if (!experienciaService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        }
        // Compara nombre de experiencias
        if (experienciaService.existsByTituloEx(experienciaDto.getTituloEx())
                && experienciaService.getByTituloEx(experienciaDto.getTituloEx()).get().getId() != id) {
            return new ResponseEntity(new Mensaje("Esa experiencia ya existe"), HttpStatus.BAD_REQUEST);
        }
        // No puede estar vacio
        if (StringUtils.isBlank(experienciaDto.getTituloEx())) {
            return new ResponseEntity(new Mensaje("El titulo es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        Experiencia experiencia = experienciaService.getOne(id).get();
        experiencia.setTituloEx(experienciaDto.getTituloEx());
        experiencia.setEmpleador(experienciaDto.getEmpleador());
        experiencia.setFechaInicio(experienciaDto.getFechaInicio());
        experiencia.setFechaFin(experienciaDto.getFechaFin());
        experiencia.setDescripcionEx(experienciaDto.getDescripcionEx());

        experienciaService.save(experiencia);
        return new ResponseEntity(new Mensaje("Experiencia actualizada"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        // Validamos si existe el ID
        if (!experienciaService.existsById(id)) {
            return new ResponseEntity(new Mensaje("El ID no existe"), HttpStatus.NOT_FOUND);
        }

        experienciaService.delete(id);
        return new ResponseEntity(new Mensaje("Experiencia eliminada"), HttpStatus.OK);
    }
}