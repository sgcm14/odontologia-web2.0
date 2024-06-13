package clinica.sistemaReservaTurno.controller;
import clinica.sistemaReservaTurno.entity.Odontologo;
import clinica.sistemaReservaTurno.exception.ResourceNotFoundException;
import clinica.sistemaReservaTurno.service.OdontologoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@Controller
@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @PostMapping //nos permite crear o registrar un odontologo
    public ResponseEntity<Odontologo> registrarUnOdontologo(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo)  throws ResourceNotFoundException{
        //necesitamos primeramente validar si existe o  no
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(odontologo.getId());
        if(odontologoBuscado.isPresent()){
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("odontologo actualizado");
        }else{
            //return  ResponseEntity.badRequest().body("no se encontro odontologo");
            //aca lanzamos la exception
            throw new ResourceNotFoundException("No se encontró odontologo con id: "+odontologo.getId());
        }

    }

    //Buscar Odontologo por Id
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Long id)  throws ResourceNotFoundException{

        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(id);
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado.get());
        }else{
            //return ResponseEntity.notFound().build();
            throw new ResourceNotFoundException("No se encontró odontologo con id: "+id);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id)  throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(id);
        if(odontologoBuscado.isPresent()){
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("odontologo eliminado con exito");
        }else{
           // return ResponseEntity.badRequest().body("odontologo no encontrado");
            //aca lanzamos la exception
            throw new ResourceNotFoundException("No existe odontologo con id: "+id);
        }
    }
}
