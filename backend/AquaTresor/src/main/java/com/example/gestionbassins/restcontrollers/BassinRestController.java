package com.example.gestionbassins.restcontrollers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionbassins.dto.BassinDTO;
import com.example.gestionbassins.entities.Bassin;
import com.example.gestionbassins.service.BassinService;

import org.springframework.web.bind.annotation.PutMapping;

// Annotation des web services
@RestController
// Pour accéder au web services de cette classe on va taper l'URL
@RequestMapping("/api")
// Sécurité
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class BassinRestController {

    @Autowired
    BassinService bassinService; 

    // Get all events
   @RequestMapping(path="all", method=RequestMethod.GET)
    public List<Bassin> getAllBassins() {
        return bassinService.getAllBassins();
    }
    
    /*@RequestMapping(path = "all", method = RequestMethod.GET)
    public List<BassinDTO> getAllBassins() {
        return bassinService.getAllBassins().stream()
            .map(bassinService::toBassinDTO)
            .collect(Collectors.toList());
    }*/

    // Get event by ID
    @RequestMapping(value="getbyid/{idBassin}", method=RequestMethod.GET)
    //@GetMapping("getbyid/{idEvenement}")
    public Bassin getBassinById(@PathVariable("idBassin") Long id) {
        return bassinService.getBassin(id);
    }

    // Create new event
    //@RequestMapping(path="addbassin",method=RequestMethod.POST)
    //@PostMapping("/addbassin")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addbassin")
    public Bassin createBassin(@RequestBody Bassin bassin) {
   	 System.out.println(bassin);
       System.out.println("Received prixBassin: " + bassin.getPrix()); // Debugging
       return bassinService.saveBassin(bassin);
   }
    
    // Update existing event
    @PutMapping("updatebassin/{idBassin}")// Use PUT and specify the ID in the path
    public Bassin updateBassin(@PathVariable("idBassin") Long idBassin, @RequestBody Bassin bassin) {
    	bassin.setIdBassin(idBassin); // Set the ID to ensure the correct event is updated
        return bassinService.upadteBassin(bassin);
    }

    // Delete event by ID
    @DeleteMapping("deletebassin/{idBassin}") // Corrected path variable
    public void deleteEvenement(@PathVariable("idBassin") Long idBassin) {
    	bassinService.deleteBassinById(idBassin);
    }
    
    
    //ne fonctionne pas 
    @RequestMapping(value="/Categories/{idCategorie}", method=RequestMethod.GET)
    public List<Bassin> getBassinByCategorieId(@PathVariable("idCategorie") Long idCategorie) {
        return bassinService.findByCategorieIdCategorie(idCategorie);
    }
    
    @RequestMapping(value="/bassinByName/{nom}",method = RequestMethod.GET)
    public List<Bassin> findByNomEvenementContains(@PathVariable("nom") String nom) {
    	return bassinService.findByNomBassinContains(nom);
    }
    
   
    
}

